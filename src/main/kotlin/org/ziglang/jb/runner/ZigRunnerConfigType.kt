package org.ziglang.jb.runner

import com.intellij.execution.configurations.ConfigurationTypeBase
import org.ziglang.jb.ZigIcons

class ZigRunnerConfigType :
    ConfigurationTypeBase("ZigRunnerConfiguration", "Zig", "Zig command run configuration", ZigIcons.zig) {
        init {
            addFactory(ZigConfigurationFactory(this))
        }
}