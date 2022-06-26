package org.ziglang.jb.editor

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.ziglang.jb.psi.ZigTypes

class ZigBraceMatcher : PairedBraceMatcher {

    companion object {
        private val PAIRS = arrayOf(BracePair(ZigTypes.LBRACE, ZigTypes.RBRACE, true),
            BracePair(ZigTypes.LPAREN, ZigTypes.RPAREN, true),
            BracePair(ZigTypes.LBRACKET, ZigTypes.RBRACKET, true)
        )
    }

    override fun getPairs(): Array<BracePair> = PAIRS

    override fun isPairedBracesAllowedBeforeType(p0: IElementType, p1: IElementType?): Boolean = true

    override fun getCodeConstructStart(p0: PsiFile?, p1: Int): Int = p1
}