package org.ziglang.jb.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.ziglang.jb.ZigFileType
import org.ziglang.jb.ZigLang

class ZigFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, ZigLang) {
    override fun getFileType(): FileType = ZigFileType

}