package org.ziglang.jb

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.ziglang.jb.lexer.ZigLexerAdapter
import org.ziglang.jb.psi.ZigTypes


object ZigSyntaxHighLighter : SyntaxHighlighterBase() {

    private val KEYWORDS = listOf(
        ZigTypes.PUB,
        ZigTypes.CONST,
        ZigTypes.FN,
        ZigTypes.TEST,
        ZigTypes.RETURN,
        ZigTypes.ALIGN,
        ZigTypes.ALLOWZERO,
        ZigTypes.AND,
        ZigTypes.ANYFRAME,
        ZigTypes.ANY_TYPE,
        ZigTypes.ASM,
        ZigTypes.ASYNC,
        ZigTypes.AWAIT,
        ZigTypes.BREAK,
        ZigTypes.CALLCONV,
        ZigTypes.CATCH,
        ZigTypes.COMPTIME,
        ZigTypes.CONTINUE,
        ZigTypes.DEFER,
        ZigTypes.ELSE,
        ZigTypes.ENUM,
        ZigTypes.ERRDEFER,
        ZigTypes.ERROR,
        ZigTypes.EXPORT,
        ZigTypes.EXTERN,
        ZigTypes.FALSE,
        ZigTypes.FOR,
        ZigTypes.IF,
        ZigTypes.INLINE,
        ZigTypes.NOALIAS,
        ZigTypes.NOSUSPEND,
        ZigTypes.NOINLINE,
        ZigTypes.NULL,
        ZigTypes.OPAQUE,
        ZigTypes.OR,
        ZigTypes.ORELSE,
        ZigTypes.PACKED,
        ZigTypes.RESUME,
        ZigTypes.LINKSECTION,
        ZigTypes.STRUCT,
        ZigTypes.SUSPEND,
        ZigTypes.SWITCH,
        ZigTypes.THREAD_LOCAL,
        ZigTypes.TRUE,
        ZigTypes.TRY,
        ZigTypes.UNDEFINED,
        ZigTypes.UNION,
        ZigTypes.UNREACHABLE,
        ZigTypes.USING_NAME_SPACE,
        ZigTypes.VAR,
        ZigTypes.VOLATILE,
        ZigTypes.WHILE
    )

    private val STRINGS = listOf(ZigTypes.STRING_LITERAL_SINGLE, ZigTypes.LINE_STRING, ZigTypes.CHAR_LITERAL)

    private val COMMENTS = listOf(ZigTypes.CONTAINER_DOC, ZigTypes.COMMENT)

    private val NUMBERS = listOf(ZigTypes.INTEGER, ZigTypes.FLOAT)


    private val keyword =
        arrayOf(createTextAttributesKey("ZIG_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD))

    private val builtinFunctions = arrayOf(
        createTextAttributesKey(
            "ZIG_BUILTIN_FUNCTIONS",
            DefaultLanguageHighlighterColors.STATIC_METHOD
        )
    )

    private val strings =
        arrayOf(createTextAttributesKey("ZIG_STRING"), DefaultLanguageHighlighterColors.STRING)

    private val comments =
        arrayOf(createTextAttributesKey("ZIG_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT))

    private val numbers = arrayOf(createTextAttributesKey("ZIG_NUMBER", DefaultLanguageHighlighterColors.NUMBER))

    private val semicolon =
        arrayOf(createTextAttributesKey("ZIG_SEMICOLON"), DefaultLanguageHighlighterColors.SEMICOLON)

    private val comma = arrayOf(createTextAttributesKey("ZIG_COMMA"), DefaultLanguageHighlighterColors.COMMA);

    override fun getHighlightingLexer(): Lexer = ZigLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
//        println("tokenType = ${tokenType}")
        return when (tokenType) {
            ZigTypes.BUILTIN_IDENTIFIER -> builtinFunctions
            in KEYWORDS -> keyword
            in STRINGS -> strings
            in COMMENTS -> comments
            in NUMBERS -> numbers
            ZigTypes.SEMICOLON -> semicolon
            ZigTypes.COMMA -> comma
            else -> emptyArray()
        }
    }
}