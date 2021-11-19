package org.ziglang.jb.runner

import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.OpenFileHyperlinkInfo
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.project.DumbAware
import kotlin.math.max

class ZigCompilerOutputFilter(private val environment: ExecutionEnvironment) : Filter, DumbAware {
    companion object {
        private val lineRegex = "^(.*.zig):(\\d*):(\\d*):.*\n".toRegex()
    }

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        val matchResult = lineRegex.matchEntire(line) ?: return null
        val filePath = matchResult.groups[1]!!.value
        val lineNumber = max(0, matchResult.groups[2]!!.value.toInt() - 1)
        val columnNumber = max(0, matchResult.groups[3]!!.value.toInt() - 1)

        val file = environment.project.baseDir?.findFileByRelativePath(filePath) ?: return null
        val link = OpenFileHyperlinkInfo(environment.project, file, lineNumber, columnNumber)
        val lineStart = entireLength - line.length
        val end = matchResult.groups[1]!!.range.last
        return Filter.Result(
            lineStart + matchResult.groups[1]!!.range.first,
            lineStart + end + 1,
            link,
            false
        )
    }

}
