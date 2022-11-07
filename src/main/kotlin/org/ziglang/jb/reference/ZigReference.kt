package org.ziglang.jb.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import org.ziglang.jb.psi.ZigFnDecl
import org.ziglang.jb.psi.ZigTopVarDecl
import org.ziglang.jb.psi.psiTreeWalkUpInsideBlock

class ZigReference(element: PsiElement, private val id: PsiElement) : PsiPolyVariantReferenceBase<PsiElement>(element) {
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return (topLevelRefs() + localVarResolve()).toTypedArray()
    }

    private fun localVarResolve(): List<ResolveResult> {
        val refs = mutableListOf<ResolveResult>()
        psiTreeWalkUpInsideBlock(element) { named ->
            if (named.nameIdentifier?.text == id.text) {
                refs.add(PsiElementResolveResult(named, true))
            }
            false
        }
        return refs
    }

    private inline fun <reified T : PsiNameIdentifierOwner> collectByName(): List<T> {
        return collectByType<T>()
            .filter { named -> named.nameIdentifier?.text == id.text }
    }

    private inline fun <reified T : PsiNameIdentifierOwner> collectByType(): MutableCollection<T> =
        PsiTreeUtil.collectElementsOfType(element.containingFile, T::class.java)

    private fun topLevelRefs(): List<ResolveResult> {
        return (collectByName<ZigFnDecl>() + collectByName<ZigTopVarDecl>()).map { named ->
            PsiElementResolveResult(
                named,
                true
            )
        }
    }

    override fun calculateDefaultRangeInElement(): TextRange = TextRange(id.startOffsetInParent, id.textLength)

    override fun getVariants(): Array<Any> {
        val fns = collectByType<ZigFnDecl>()
        val vars = collectByType<ZigTopVarDecl>()
        val localVars = mutableListOf<PsiNameIdentifierOwner>()
        psiTreeWalkUpInsideBlock(element) { named ->
            localVars.add(named)
            false
        }
        return (fns + vars + localVars).map { named -> createLookup(named.nameIdentifier?.text!!) }.toTypedArray()
    }
}