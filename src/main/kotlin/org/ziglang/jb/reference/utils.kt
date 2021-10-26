package org.ziglang.jb.reference

import com.intellij.codeInsight.lookup.AutoCompletionPolicy
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import org.ziglang.jb.psi.ZigTypes

fun ASTNode.findIdNode(childType: IElementType): PsiElement? =
    findChildByType(childType)?.findChildByType(ZigTypes.ID)?.psi

fun createLookup(name: String): LookupElement {
    return LookupElementBuilder.create(name).withPresentableText(name)
        .withAutoCompletionPolicy(AutoCompletionPolicy.NEVER_AUTOCOMPLETE)
}