package org.ziglang.jetbrains.reference

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import org.ziglang.jetbrains.psi.ZigTypes

open class ContainerFieldMixin(node: ASTNode) : ASTWrapperPsiElement(node), PsiNameIdentifierOwner {
    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? = node.findChildByType(ZigTypes.ID)?.psi
}