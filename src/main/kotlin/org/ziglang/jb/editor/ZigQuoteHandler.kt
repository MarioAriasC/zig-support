package org.ziglang.jb.editor

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.intellij.psi.TokenType
import org.ziglang.jb.psi.ZigTypes

class ZigQuoteHandler : SimpleTokenSetQuoteHandler(ZigTypes.STRING_LITERAL_SINGLE, TokenType.BAD_CHARACTER)