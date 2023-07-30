package org.ziglang.jb

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon


object ZigFileType : LanguageFileType(ZigLang) {
    override fun getName(): String = "Zig"

    override fun getDescription(): String = "Zig files"

    override fun getDefaultExtension(): String = "zig"

    override fun getIcon(): Icon = ZigIcons.file

    override fun getCharset(file: VirtualFile, content: ByteArray): String = CHARSET
    
    private const val CHARSET = "UTF-8"
}