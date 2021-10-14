package org.ziglang.jetbrains.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.ziglang.jetbrains.ZigFileType
import org.ziglang.jetbrains.ZigLang

class ZigFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, ZigLang) {
    override fun getFileType(): FileType = ZigFileType

}