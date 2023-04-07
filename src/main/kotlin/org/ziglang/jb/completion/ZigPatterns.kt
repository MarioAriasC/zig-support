package org.ziglang.jb.completion

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import org.ziglang.jb.psi.ZigContainerDecl
import org.ziglang.jb.psi.ZigContainerField
import org.ziglang.jb.psi.ZigFile
import org.ziglang.jb.psi.ZigFnProto
import org.ziglang.jb.psi.ZigParamDeclList
import org.ziglang.jb.reference.ContainerFieldMixin

object ZigPatterns {

    fun basicDeclarationPattern(): PsiElementPattern.Capture<PsiElement> = psiElement().withParents(ZigFile::class.java)

    fun topDeclarationPattern(): PsiElementPattern.Capture<PsiElement> =
        psiElement().withParents(ZigContainerField::class.java, ZigFile::class.java)


    fun testPattern(): PsiElementPattern.Capture<PsiElement> =
        psiElement().withParent(ZigFile::class.java)

    fun afterParameterDeclarationListPattern(): PsiElementPattern.Capture<PsiElement> =
        psiElement().withParent(ZigFnProto::class.java)
            .afterSibling(psiElement().whitespace())


}

private inline fun <reified T : PsiElement> psiElement(): PsiElementPattern.Capture<T> {
    return psiElement(T::class.java)
}