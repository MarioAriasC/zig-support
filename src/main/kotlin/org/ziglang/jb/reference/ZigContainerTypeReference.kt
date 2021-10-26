package org.ziglang.jb.reference

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.ResolveResult
import org.ziglang.jb.types.FieldType
import org.ziglang.jb.types.StructType
import org.ziglang.jb.types.Type

class ZigContainerTypeReference(element: PsiElement, id: PsiElement, private val containerType: Type?) :
    ZigIdReference(element, id) {
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val result = when (containerType) {
            is StructType -> containerType.fields[id.text]?.reference()
            is FieldType -> {
                when (val type = containerType.type) {
                    is StructType -> type.fields[id.text]?.reference()
                    else -> null
                }
            }
            else -> null
        }

        return if (result != null) {
            arrayOf(PsiElementResolveResult(result, true))
        } else {
            emptyArray()
        }
    }

    override fun getVariants(): Array<Any> {
        return when (containerType) {
            is StructType -> containerType.fields.keys.map(::createLookup).toTypedArray()
            is FieldType -> {
                when (val type = containerType.type) {
                    is StructType -> type.fields.keys.map(::createLookup).toTypedArray()
                    else -> emptyArray()
                }
            }
            else -> emptyArray()
        }
    }
}