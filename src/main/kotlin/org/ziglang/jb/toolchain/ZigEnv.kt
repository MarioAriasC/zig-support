package org.ziglang.jb.toolchain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Created by IntelliJ IDEA.
 * @author Mario Arias
 * Date: 23/4/23
 * Time: 5:22 pm
 */
@JsonIgnoreProperties("global_cache_dir")
data class ZigEnv(
    @field:JsonProperty("zig_exe")
    val zigExe: String,

    @field:JsonProperty("lib_dir")
    val libDir: String,

    @field:JsonProperty("std_dir")
    val stdDir: String,
    
    val version: String,

    val target:String
)
