package org.ziglang.jetbrains.types

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import org.ziglang.jetbrains.ZigLang.primitiveTypes
import org.ziglang.jetbrains.psi.*

fun ZigPrimaryTypeExpr.getTypeFromChain(end: PsiElement): Type? {
    var type = this.inference()
    var sibling = nextSibling
    while (sibling != end) {
        when (sibling) {
            is ZigSuffixOp -> {
                val fieldName = sibling.symbol?.firstChild?.text ?: return null
                when (type) {
                    is StructType -> {
                        type = type.fields[fieldName]
                        sibling = sibling.nextSibling
                    }
                    is FieldType -> {
                        when (val ft = type.type) {
                            is StructType -> {
                                type = ft.fields[fieldName]
                                sibling = sibling.nextSibling
                            }
                            else -> return null
                        }
                    }
                    else -> return null
                }
            }
            else -> return null
        }
    }
    return type
}

fun PsiElement.inference(): Type? {
    val context = Context(mutableMapOf(), mutableSetOf())
    val type = this.type(context)
    while (context.unknownTypes.isNotEmpty()) {
        val unknownType = context.unknownTypes.first()
        unknownType.handler(unknownType.type)
        context.unknownTypes.remove(unknownType)
    }
    return type
}

fun PsiElement.type(context: Context): Type? {
    return when (this) {
        is ZigTopVarDecl -> type(context)
        is ZigLocalVarDecl -> type(context)
        is ZigPrimaryTypeExpr -> type(context)
        is ZigSymbol -> type(context)
        is ZigFnDecl -> type(context)
        else -> null
    }
}

fun ZigTopVarDecl.type(context: Context): Type? =
    PsiTreeUtil.findChildOfType(this, ZigPrimaryTypeExpr::class.java)?.type(context)

fun ZigLocalVarDecl.type(context: Context): Type? =
    PsiTreeUtil.findChildOfType(this, ZigPrimaryTypeExpr::class.java)?.type(context)

fun ZigPrimaryTypeExpr.type(context: Context): Type? {
    return when {
        firstChild is ZigContainerDecl -> firstChild.type(context)
        firstChild is ZigSymbol -> firstChild.type(context)
        firstChild.text?.toString() == "@import" -> context.getTypeFromImport(this)
        else -> null
    }
}

fun ZigContainerDecl.type(context: Context): Type? {
    return context.cacheType(this) {
        when (firstChild?.firstChild?.text) {
            ZigTypes.STRUCT.toString().toLowerCase() -> context.getStructType(this)
            ZigTypes.ENUM.toString().toLowerCase() -> context.getEnumType(this)
            else -> null
        }
    }
}

fun ZigFnDecl.type(context: Context): Type? {
    return context.cacheType(this) {
        val type = FnType(this, null)
        val unknownType = UnknownType(this, type) {
            val fnType = it as FnType
            val fnProto = PsiTreeUtil.getChildOfType(this, ZigFnProto::class.java) ?: return@UnknownType
            val returnType = PsiTreeUtil.getChildOfType(fnProto, ZigPrimaryTypeExpr::class.java)?.type(context)
            fnType.returnType = returnType
        }
        context.unknownTypes.add(unknownType)
        type
    }
}

fun ZigSymbol.type(context: Context): Type? {
    val id = firstChild?.text ?: return null
    return context.cacheType(this) {
        if (id in primitiveTypes) {
            BuiltinType(id)
        } else {
            findPsiElementByNameInsideFile(id, this)?.type(context)
        }
    }
}

data class UnknownType(val element: PsiElement, val type: Type, val handler: (Type) -> Unit)

class Context(private val visitedTypes: MutableMap<PsiElement, Type?>, val unknownTypes: MutableSet<UnknownType>) {
    fun cacheType(element: PsiElement, provider: () -> Type?): Type? {
        val cachedType = visitedTypes[element]
        if (cachedType != null) {
            return cachedType
        }
        val type = provider() ?: return null
        visitedTypes[element] = type
        return type
    }

    private fun structType(
        element: PsiElement,
        containerFieldTypeProvider: (child: PsiElement) -> FieldType
    ): StructType {
        val fields = element.children.map { child ->
            when (child) {
                is ZigContainerField -> child.firstChild?.text!! to containerFieldTypeProvider(child)
                is ZigFnDecl -> child.nameIdentifier?.text!! to FieldType(child, child.type(this))
                is ZigTopVarDecl -> child.nameIdentifier?.text!! to FieldType(child, child.type(this))
                else -> null
            }
        }
        return StructType(element, fields.filterNotNull().toMap())
    }

    fun getStructType(element: PsiElement): StructType {
        return structType(element) { child ->
            FieldType(
                child,
                PsiTreeUtil.getChildOfType(child, ZigPrimaryTypeExpr::class.java)?.type(this)
            )
        }
    }

    fun getEnumType(element: PsiElement): StructType {
        return structType(element) { child ->
            FieldType(child, BuiltinType("u2"))
        }
    }

    fun getTypeFromImport(element: PsiElement): StructType? {
        val literal = PsiTreeUtil.findChildOfType(element, ZigStringLiteral::class.java)
        val path = literal?.firstChild?.text?.removeSurrounding("\"") ?: return null
        val psiFile =
            FilenameIndex.getFilesByName(element.project, "$path.zig", GlobalSearchScope.projectScope(element.project))
                .first()
        return getStructType(psiFile)
    }
}