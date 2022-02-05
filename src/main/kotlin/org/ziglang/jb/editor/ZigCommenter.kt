package org.ziglang.jb.editor

import com.intellij.codeInsight.generation.IndentedCommenter
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.psi.PsiComment
import com.intellij.psi.tree.IElementType
import org.ziglang.jb.psi.ZigTypes

class ZigCommenter : CodeDocumentationAwareCommenter, IndentedCommenter {

    companion object {
        const val comment = "// "
        const val docComment = "/// "
    }

    override fun getLineCommentPrefix(): String = comment

    override fun getBlockCommentPrefix(): String? = null

    override fun getBlockCommentSuffix(): String? = null

    override fun getCommentedBlockCommentPrefix(): String? = null

    override fun getCommentedBlockCommentSuffix(): String? = null

    override fun getLineCommentTokenType(): IElementType? = ZigTypes.COMMENT

    override fun getBlockCommentTokenType(): IElementType? = null

    override fun getDocumentationCommentTokenType(): IElementType? = null

    override fun getDocumentationCommentPrefix(): String? = null

    override fun getDocumentationCommentLinePrefix(): String = docComment

    override fun getDocumentationCommentSuffix(): String? = null

//    TODO implement Doc comments
    override fun isDocumentationComment(element: PsiComment?): Boolean = false

    override fun forceIndentedLineComment(): Boolean = true
}