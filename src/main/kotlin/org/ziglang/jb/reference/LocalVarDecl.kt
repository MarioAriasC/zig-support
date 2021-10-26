package org.ziglang.jb.reference

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import org.ziglang.jb.psi.VarDeclaration
import org.ziglang.jb.psi.ZigTypes
import org.ziglang.jb.psi.isContainerType

open class LocalVarDecl(node: ASTNode) : ASTWrapperPsiElement(node), PsiNameIdentifierOwner, VarDeclaration {
    override fun setName(name: String): PsiElement {
        TODO("Not yet implemented")
    }

    override fun getNameIdentifier(): PsiElement? = node.findIdNode(ZigTypes.VAR_DECL)

    override fun isContainerType(): Boolean = node.isContainerType()

}