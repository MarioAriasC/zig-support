package org.ziglang.jb.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.AutoCompletionPolicy
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.ObjectPattern
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.tree.IElementType
import com.intellij.util.ProcessingContext
import org.ziglang.jb.ZigIcons
import org.ziglang.jb.ZigLang
import org.ziglang.jb.psi.ZigFile
import org.ziglang.jb.psi.ZigTypes
import org.ziglang.jb.psi.leftSiblings

class ZigCompletionContributor : CompletionContributor() {

    private val topKeyWords = listOf(
        ZigTypes.CONST,
        ZigTypes.VAR,
        ZigTypes.FN,
        ZigTypes.PUB,
        ZigTypes.EXPORT,
        ZigTypes.EXTERN,
        ZigTypes.INLINE,
        ZigTypes.NOINLINE,
        ZigTypes.THREAD_LOCAL,
        ZigTypes.USING_NAME_SPACE
    ).map { keyword ->
        LookupElementBuilder
            .create("$keyword ")
            .withPresentableText(keyword.toString())
            .withAutoCompletionPolicy(AutoCompletionPolicy.NEVER_AUTOCOMPLETE)
    }

    private val lookupLowerCase = { element: IElementType ->
        val lowerCased = element.toString().toLowerCase()
        LookupElementBuilder
            .create(lowerCased)
            .withPresentableText(lowerCased)
            .withAutoCompletionPolicy(AutoCompletionPolicy.NEVER_AUTOCOMPLETE)
    }

    private val containerDecl = listOf(
        ZigTypes.STRUCT,
        ZigTypes.ENUM,
        ZigTypes.OPAQUE,
        ZigTypes.UNION,
        ZigTypes.EXTERN,
        ZigTypes.PACKED
    ).map(lookupLowerCase)

    private val containerAutoDecl = listOf(
        ZigTypes.STRUCT,
        ZigTypes.ENUM,
        ZigTypes.OPAQUE,
        ZigTypes.UNION,
    ).map(lookupLowerCase)

    private val builtinFunctions = ZigLang.builtInFunctions.map { fn ->
        LookupElementBuilder.create("$fn(")
            .withPresentableText(fn)
            .withIcon(ZigIcons.function)
            .withAutoCompletionPolicy(AutoCompletionPolicy.NEVER_AUTOCOMPLETE)

    }

    init {
        extend(
            CompletionType.BASIC,
            psiElement().withParents(PsiErrorElement::class.java, ZigFile::class.java),
            ZigCompletionProvider(topKeyWords)
        )

        extend(
            CompletionType.BASIC,
            psiElement(ZigTypes.ID).withAncestor(2, psiElement(ZigTypes.PRIMARY_TYPE_EXPR)),
            ZigCompletionProvider(containerDecl)
        )

        extend(
            CompletionType.BASIC,
            psiElement(ZigTypes.ID).withParent(
                psiElement(ZigTypes.CONTAINER_FIELD).withPreviousSiblingsSkipping(
                    psiElement().whitespace(),
                    psiElement(PsiErrorElement::class.java)
                )
            ),
            ZigCompletionProvider(containerAutoDecl)
        )

        extend(
            CompletionType.BASIC,
            psiElement(ZigTypes.BUILTIN_IDENTIFIER),
            ZigCompletionProvider(builtinFunctions)
        )
    }

    private fun <T, Self : ObjectPattern<T, Self>> ObjectPattern<T, Self>.with(
        name: String,
        predicate: (T) -> Boolean
    ): Self =
        with(object : PatternCondition<T>(name) {
            override fun accepts(t: T, context: ProcessingContext?): Boolean = predicate(t)
        })

    private fun <T : PsiElement, Self : PsiElementPattern<T, Self>> PsiElementPattern<T, Self>.withPreviousSiblingsSkipping(
        skip: ElementPattern<out T>,
        pattern: ElementPattern<out T>
    ): Self = with("withPrevSiblingSkipping") { element: PsiElement ->
        val sibling = element.leftSiblings().dropWhile(skip::accepts).firstOrNull() ?: return@with false
        pattern.accepts(sibling)
    }
}