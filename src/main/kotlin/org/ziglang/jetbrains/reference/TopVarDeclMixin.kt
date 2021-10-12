package org.ziglang.jetbrains.reference

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import org.ziglang.jetbrains.psi.VarDeclaration
import org.ziglang.jetbrains.psi.ZigTypes
import org.ziglang.jetbrains.psi.isContainerType

open class TopVarDeclMixin(node: ASTNode) : ASTWrapperPsiElement(node), PsiNameIdentifierOwner, VarDeclaration {
    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? = node.findIdNode(ZigTypes.VAR_DECL)

    override fun isContainerType(): Boolean = node.isContainerType()

    override fun getTextOffset(): Int = nameIdentifier?.textOffset ?: super.getTextOffset()
}