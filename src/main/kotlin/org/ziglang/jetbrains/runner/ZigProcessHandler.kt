package org.ziglang.jetbrains.runner

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.AnsiEscapeDecoder
import com.intellij.execution.process.KillableProcessHandler
import com.intellij.openapi.util.Key

class ZigProcessHandler(cmdLine: GeneralCommandLine) : KillableProcessHandler(cmdLine),
    AnsiEscapeDecoder.ColoredTextAcceptor {
    override fun coloredTextAvailable(text: String, attributes: Key<*>) {
        super.notifyTextAvailable(text, attributes)
    }

}
