package org.ziglang.jb.toolchain

import com.intellij.openapi.vfs.VirtualFile

class ZigLocalToolchain(private val env: ZigEnv):ZigToolchain {
    override fun version(): String = env.version

    override fun executable(): VirtualFile? {
        TODO("Not yet implemented")
    }

    override fun libDir(): VirtualFile? {
        TODO("Not yet implemented")
    }

    override fun stdLibDir(): VirtualFile? {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean = true
}