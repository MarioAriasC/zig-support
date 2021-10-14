package org.ziglang.jetbrains.runner

import com.intellij.openapi.Disposable
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.EditorTextField
import com.intellij.ui.components.Label
import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.panel
import javax.swing.JComponent
import javax.swing.event.DocumentEvent

class ZigRunningConfigurationEditor : SettingsEditor<ZigCommandConfiguration>() {

    private val command =
        pathTextField(FileChooserDescriptorFactory.createSingleFileDescriptor(), this, "Path to Zig compiler")
            .apply { isEnabled = true }

    private val args = EditorTextField("")

    override fun resetEditorFrom(s: ZigCommandConfiguration) {
        command.text = s.command
        args.text = s.args
    }

    override fun applyEditorTo(s: ZigCommandConfiguration) {
        s.command = command.text
        s.args = args.text
    }

    override fun createEditor(): JComponent = panel {
        val label = Label("&Zig:").apply { labelFor = command }
        row(label) {
            command(CCFlags.pushX, CCFlags.growX)
        }

        val argLabel = Label("&Args:").apply { labelFor = args }
        row(argLabel) {
            args(CCFlags.pushX, CCFlags.growX)
        }
    }


    private fun pathTextField(
        descriptor: FileChooserDescriptor,
        disposable: Disposable,
        title: String,
        onTextChanged: () -> Unit = {}
    ): TextFieldWithBrowseButton = TextFieldWithBrowseButton(null, disposable).apply {
        addBrowseFolderListener(title, null, null, descriptor, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT)
        childComponent.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent) {
                onTextChanged()
            }
        })
    }
}


