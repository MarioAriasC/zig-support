package org.ziglang.jetbrains

import com.intellij.openapi.util.IconLoader

object ZigIcons {
    val zig = IconLoader.getIcon("/icons/zig.png", this::class.java)
    val file = IconLoader.getIcon("/icons/zig_file.png", this::class.java)
    val function = IconLoader.getIcon("/icons/zig_function.png", this::class.java)
    val icon = IconLoader.getIcon("/icons/zig_icon.png", this::class.java)
    val sdk = IconLoader.getIcon("/icons/zig_sdk.png", this::class.java)
    val variable = IconLoader.getIcon("/icons/zig_variable.png", this::class.java)
}