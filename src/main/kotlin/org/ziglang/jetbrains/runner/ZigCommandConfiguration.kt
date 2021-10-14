package org.ziglang.jetbrains.runner

import com.intellij.execution.Executor
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class ZigCommandConfiguration(project: Project, factory: ConfigurationFactory, name: String) :
    LocatableConfigurationBase<RunProfileState>(project, factory, name) {

    var command = ""
    var args = ""

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState =
        ZigRunState(environment, this)

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> = ZigRunningConfigurationEditor()
}