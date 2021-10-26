package org.ziglang.jb.runner

import com.intellij.execution.ExecutionManager
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.runners.executeState
import org.jetbrains.concurrency.resolvedPromise

open class ZigRunner : ProgramRunner<RunnerSettings> {

    companion object {
        const val RUNNER_ID = "Zig"
    }

    override fun getRunnerId(): String = RUNNER_ID

    override fun canRun(executorId: String, profile: RunProfile): Boolean = true

    @Suppress("UnstableApiUsage")
    override fun execute(environment: ExecutionEnvironment) {
        val state = environment.state ?: return
        ExecutionManager.getInstance(environment.project).startRunProfile(environment) {
            resolvedPromise(executeState(state, environment, this))
        }
    }
}