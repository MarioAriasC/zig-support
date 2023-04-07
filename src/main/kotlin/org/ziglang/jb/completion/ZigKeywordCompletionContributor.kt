package org.ziglang.jb.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import org.ziglang.jb.completion.ZigPatterns.afterParameterDeclarationListPattern
import org.ziglang.jb.completion.ZigPatterns.topDeclarationPattern

class ZigKeywordCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            topDeclarationPattern(),
            ZigKeywordCompletionProvider(
                "test",
                "comptime",
                "pub",
                "export",
                "extern",
                "inline",
                "noinline",
                "threadlocal",
                "usingnamespace",
                "fn",
                "const",
                "var"
            )
        )

        extend(CompletionType.BASIC, afterParameterDeclarationListPattern(), ZigKeywordCompletionProvider("align"))
    }


}