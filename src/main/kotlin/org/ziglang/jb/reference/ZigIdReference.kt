package org.ziglang.jb.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReferenceBase

abstract class ZigIdReference(element: PsiElement, val id: PsiElement) :
    PsiPolyVariantReferenceBase<PsiElement>(element) {
    override fun calculateDefaultRangeInElement(): TextRange {
        return TextRange.from(id.startOffsetInParent, id.textLength)
    }

}