package org.ziglang.jetbrains.runner

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class ZigConfigurationFactory(type: ZigRunnerConfigType) : ConfigurationFactory(type) {

    companion object {
        const val CONFIGURATION_ID = "Zig"
    }

    override fun getId(): String = CONFIGURATION_ID

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return ZigCommandConfiguration(project, this, "Zig")
    }
}