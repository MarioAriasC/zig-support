package org.ziglang.jb

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.ziglang.jb.lexer.ZigLexerAdapter
import org.ziglang.jb.psi.ZigFile
import org.ziglang.jb.psi.ZigTokenType
import org.ziglang.jb.psi.ZigTypes
import org.ziglang.jb.psi.parser.ZigParser

class ZigParserDefinition : ParserDefinition {

    companion object {
        private val FILE = IFileElementType(ZigLang)
    }

    override fun createLexer(project: Project?): Lexer = ZigLexerAdapter()

    override fun createParser(project: Project?): PsiParser = ZigParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = ZigTokenType.COMMENTS

    override fun getStringLiteralElements(): TokenSet = ZigTokenType.STRINGS

    override fun createElement(node: ASTNode?): PsiElement = ZigTypes.Factory.createElement(node)
    override fun createFile(viewProvider: FileViewProvider): PsiFile = ZigFile(viewProvider)

    override fun getWhitespaceTokens(): TokenSet = TokenSet.WHITE_SPACE

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }
}