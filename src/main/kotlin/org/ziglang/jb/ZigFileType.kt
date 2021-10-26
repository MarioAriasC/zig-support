package org.ziglang.jb

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon


object ZigFileType : LanguageFileType(ZigLang) {
    override fun getName(): String = "Zig"

    override fun getDescription(): String = "Zig files"

    override fun getDefaultExtension(): String = "zig"

    override fun getIcon(): Icon = ZigIcons.file
}