package org.ziglang.jb.psi

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import org.ziglang.jb.ZigLang

class ZigTokenType(debugName: String) : IElementType(debugName, ZigLang) {
    companion object TokenHolder {
        @JvmField
        val COMMENTS = TokenSet.create(ZigTypes.COMMENT)

        @JvmField
        val STRINGS = TokenSet.create(ZigTypes.STRING_LITERAL)
    }
}