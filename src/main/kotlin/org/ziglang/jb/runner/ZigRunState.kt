package org.ziglang.jb.runner

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment

class ZigRunState(environment: ExecutionEnvironment, private val config: ZigCommandConfiguration) :
    CommandLineState(environment) {

    init {
        addConsoleFilters(ZigCompilerOutputFilter(environment))
    }

    override fun startProcess(): ProcessHandler {
        val cmds = mutableListOf(config.command)
        cmds.addAll(config.args.split(" "))
        val cmdLine = GeneralCommandLine(cmds).withWorkDirectory(environment.project.basePath)

        val handler = ZigProcessHandler(cmdLine)
        ProcessTerminatedListener.attach(handler)
        return handler
    }
}