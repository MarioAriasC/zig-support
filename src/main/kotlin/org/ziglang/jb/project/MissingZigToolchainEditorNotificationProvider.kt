package org.ziglang.jb.project

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationProvider
import java.util.function.Function
import javax.swing.JComponent

class MissingZigToolchainEditorNotificationProvider(private val project: Project) : EditorNotificationProvider,
    DumbAware {
    override fun collectNotificationData(
        project: Project,
        file: VirtualFile
    ): Function<in FileEditor, out JComponent?> = Function { editor ->
        null
    }

    companion object {
        const val NOTIFICATION_STATUS_KEY = "org.ziglang.hideToolchainNotifications"
    }
}