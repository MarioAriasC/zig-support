package org.ziglang.jb.editor

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import org.ziglang.jb.ZigFileType

class ZigCodeContextType : TemplateContextType("Zig") {
    override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
        return templateActionContext.file.fileType == ZigFileType
    }
}