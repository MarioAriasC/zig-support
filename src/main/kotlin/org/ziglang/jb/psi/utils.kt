package org.ziglang.jb.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil

fun ASTNode.isContainerType() = PsiTreeUtil.findChildOfType(this.psi, ZigContainerDecl::class.java) != null

fun PsiElement.prevSiblingTypeIgnoring(type: IElementType, vararg ignoredTypes: IElementType): PsiElement? {
    var next = prevSibling
    while (true) {
        val localNext = next ?: return null
        next = localNext.prevSibling
        return if (ignoredTypes.any { ignored -> localNext.node.elementType == ignored }) {
            continue
        } else {
            localNext.takeIf { it.node.elementType == type }
        }
    }
}

fun findPsiElementByNameInsideFile(name: String, start: PsiElement): PsiElement? {
    val found = findPsiElementByNameInsideBlock(name, start)
    if (found != null) return found
    return PsiTreeUtil.collectElementsOfType(start.containingFile, ZigTopVarDecl::class.java)
        .firstOrNull { varDecl -> varDecl.nameIdentifier?.text == name }
}

fun findPsiElementByNameInsideBlock(name: String, start: PsiElement): PsiElement? {
    var element: PsiElement? = null
    psiTreeWalkUpInsideBlock(start) { named ->
        if (named.nameIdentifier?.text == name) {
            element = named
            true
        } else {
            false
        }
    }
    return element
}

fun psiTreeWalkUpInsideBlock(element: PsiElement, consumer: (PsiNameIdentifierOwner) -> Boolean) {
    var statement = PsiTreeUtil.findFirstParent(element) { parent -> parent is ZigStatement }
    while (statement != null) {
        var sibling = statement.prevSibling
        while (sibling != null) {
            if (ZigTypes.STATEMENT == sibling.node.elementType) {
                val localVarDecl = sibling.firstChild
                if (localVarDecl is ZigLocalVarDecl) {
                    if (consumer(localVarDecl)) return
                }
            }
            sibling = sibling.prevSibling
        }
        val block = PsiTreeUtil.findFirstParent(statement) { parent -> parent is ZigBlock }
        statement = PsiTreeUtil.findFirstParent(block) { parent -> parent is ZigStatement }
    }

}

fun findTypesInsideFile(start: PsiElement): List<PsiNameIdentifierOwner> {
    return findTypesInsideBlock(start) + findTopLevelTypes(start)
}

fun findTopLevelTypes(element: PsiElement): List<PsiNameIdentifierOwner> {
    val topVars = PsiTreeUtil.collectElementsOfType(element.containingFile, ZigTopVarDecl::class.java)
        .filter { varDecl -> isElementAType(varDecl) }
    val fns = PsiTreeUtil.collectElementsOfType(element.containingFile, ZigFnDecl::class.java)
        .filter { fn -> isElementAType(fn) }
    return topVars + fns
}

fun findTypesInsideBlock(start: PsiElement): List<PsiNameIdentifierOwner> {
    val types = mutableListOf<PsiNameIdentifierOwner>()
    psiTreeWalkUpInsideBlock(start) { named ->
        if (isElementAType(named)) {
            types.add(named)
        }
        false
    }
    return types
}

fun isElementAType(element: PsiElement): Boolean =
    (((element is ZigTopVarDecl) || (element is ZigLocalVarDecl)) && (PsiTreeUtil.findChildOfType(
        element,
        ZigContainerDecl::class.java
    ) != null)) || (element is ZigFnDecl)

fun PsiElement.leftSiblings(): Sequence<PsiElement> =
    generateSequence(this.prevSibling) { element -> element.prevSibling }