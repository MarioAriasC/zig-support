package org.ziglang.jetbrains.runner

import com.intellij.execution.configurations.ConfigurationTypeBase
import org.ziglang.jetbrains.ZigIcons

class ZigRunnerConfigType :
    ConfigurationTypeBase("ZigRunnerConfiguration", "Zig", "Zig command run configuration", ZigIcons.zig) {
        init {
            addFactory(ZigConfigurationFactory(this))
        }
}