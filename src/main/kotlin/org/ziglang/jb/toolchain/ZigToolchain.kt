package org.ziglang.jb.toolchain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.processTools.getResultStdoutStr
import com.intellij.openapi.vfs.VirtualFile
import kotlinx.coroutines.runBlocking
import org.ziglang.jb.ZigLang
import java.nio.charset.StandardCharsets

interface ZigToolchain {
    fun version(): String
    fun executable(): VirtualFile?
    fun libDir(): VirtualFile?
    fun stdLibDir(): VirtualFile?

    fun isValid(): Boolean


    companion object {
        private val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
        private const val ENV = "env"

        

        fun defaultZigEnv(): ZigEnv? = runBlocking {
            val zigPath = ZigLang.zigPath ?: return@runBlocking null
            val commandLine = GeneralCommandLine()
                .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
                .withExePath(zigPath)
                .withParameters(ENV)
            val handler = OSProcessHandler(commandLine.withCharset(StandardCharsets.UTF_8))

            val envOutput = handler.process.getResultStdoutStr().getOrNull() ?: return@runBlocking null
            objectMapper.readValue(envOutput, ZigEnv::class.java)
        }

        val NULL = object : ZigToolchain {
            override fun version(): String = ""

            override fun executable(): VirtualFile? = null

            override fun libDir(): VirtualFile? = null

            override fun stdLibDir(): VirtualFile? = null

            override fun isValid(): Boolean = false

        }
    }

}