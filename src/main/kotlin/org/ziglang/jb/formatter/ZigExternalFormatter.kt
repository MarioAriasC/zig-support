package org.ziglang.jb.formatter

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessAdapter
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.psi.PsiFile
import org.ziglang.jb.ZigLang
import org.ziglang.jb.psi.ZigFile
import java.nio.charset.StandardCharsets

private const val FMT = "fmt"

class ZigExternalFormatter : AsyncDocumentFormattingService() {
    override fun getFeatures(): MutableSet<FormattingService.Feature> = mutableSetOf(
        FormattingService.Feature.FORMAT_FRAGMENTS,
        FormattingService.Feature.AD_HOC_FORMATTING,
        FormattingService.Feature.OPTIMIZE_IMPORTS
    )

    override fun canFormat(psiFile: PsiFile): Boolean = psiFile is ZigFile

    override fun createFormattingTask(request: AsyncFormattingRequest): FormattingTask? {
        val zigPath = ZigLang.zigPath ?: return null
//        val context = request.context
//        val project = context.project
//        val file = context.virtualFile ?: return null;
//        val ioFile = request.ioFile
        val params = listOf(FMT, "--stdin")
        val commandLine =
            GeneralCommandLine().withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                .withExePath(zigPath).withParameters(params)
        val handler = OSProcessHandler(commandLine.withCharset(StandardCharsets.UTF_8))
        return object : FormattingTask {
            override fun run() {
                handler.processInput.use { out -> out.write(request.documentText.toByteArray()) }
                handler.addProcessListener(object : CapturingProcessAdapter() {
                    override fun processTerminated(event: ProcessEvent) {
                        val exitCode = event.exitCode
                        if(exitCode == 0) {
                            request.onTextReady(output.stdout)
                        } else {
                            request.onError(ZigLang.NOTIFICATION_GROUP_ID, output.stderr)
                        }
                    }
                })
                handler.startNotify()
            }

            override fun cancel(): Boolean {
                handler.destroyProcess()
                return true
            }

        }

    }

    override fun getNotificationGroupId(): String = ZigLang.NOTIFICATION_GROUP_ID

    override fun getName(): String = ZigLang.NOTIFICATION_GROUP_ID
}