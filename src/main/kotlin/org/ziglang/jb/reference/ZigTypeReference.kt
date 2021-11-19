package org.ziglang.jb.reference

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.ResolveResult
import org.ziglang.jb.ZigLang
import org.ziglang.jb.psi.findTypesInsideFile

class ZigTypeReference(element: PsiElement, id: PsiElement) : ZigIdReference(element, id) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val named = findTypesInsideFile(element).find { type -> type.nameIdentifier?.text == id.text }
        return if (named != null) {
            arrayOf(PsiElementResolveResult(named, true))
        } else {
            emptyArray()
        }
    }

    override fun getVariants(): Array<Any> {
        return (findTypesInsideFile(element).map { named -> createLookup(named.nameIdentifier?.text!!) } + ZigLang.primitiveTypesLookup).toTypedArray()
    }
}