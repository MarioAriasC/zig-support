package org.ziglang.jetbrains.reference

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.TokenType
import com.intellij.psi.util.PsiTreeUtil
import org.ziglang.jetbrains.psi.*
import org.ziglang.jetbrains.types.Type
import org.ziglang.jetbrains.types.getTypeFromChain
import org.ziglang.jetbrains.types.inference

abstract class SymbolMixin(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getReference(): PsiReference? {
        var containerType: Type? = null
        if (prevSibling?.text == ZigTypes.DOT.toString()) {
            if (parent is ZigSuffixOp) {
                val typeExpr = PsiTreeUtil.findSiblingBackward(parent, ZigTypes.PRIMARY_TYPE_EXPR, null)
                containerType = (typeExpr as ZigPrimaryTypeExpr).getTypeFromChain(parent)
            } else if (parent is ZigFieldInit) {
                val typeExpr = PsiTreeUtil.findSiblingBackward(parent.parent, ZigTypes.PRIMARY_TYPE_EXPR, null)
                containerType = (typeExpr as ZigPrimaryTypeExpr).inference()
            }
        } else if (parent is ZigPrimaryTypeExpr) {
            if (parent.prevSiblingTypeIgnoring(ZigTypes.COLON, TokenType.WHITE_SPACE) != null) {
                return ZigTypeReference(this, node.firstChildNode?.psi!!)
            } else if (parent.parent is ZigFnProto || parent.parent is ZigParamType) {
                return ZigTypeReference(this, node.firstChildNode?.psi!!)
            }
        }
        return if(containerType != null) {
            ZigContainerTypeReference(this, node.firstChildNode?.psi!!, containerType)
        } else {
            ZigReference(this, node.firstChildNode?.psi!!)
        }
    }
}