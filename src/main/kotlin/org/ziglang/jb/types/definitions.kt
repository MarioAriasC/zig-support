package org.ziglang.jb.types

import com.intellij.psi.PsiElement

abstract class Type(private val owner: PsiElement?) {
    fun reference(): PsiElement? {
        return owner
    }
}

class FieldType(ref: PsiElement, val type: Type?) : Type(ref)

class StructType(owner: PsiElement, val fields: Map<String, FieldType?>) : Type(owner)

class FnType(owner: PsiElement, var returnType: Type?) : Type(owner)

class BuiltinType(val name: String) : Type(null)