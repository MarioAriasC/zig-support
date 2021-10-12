package org.ziglang.jetbrains

import com.intellij.lang.Language
import org.ziglang.jetbrains.reference.createLookup

object ZigLang : Language("Zig") {
    val primitiveTypes = listOf(
        "i8",
        "u8",
        "i16",
        "u16",
        "i32",
        "u32",
        "i64",
        "u64",
        "i128",
        "u128",
        "isize",
        "usize",
        "c_short",
        "c_ushort",
        "c_int",
        "c_uint",
        "c_long",
        "c_ulong",
        "c_longlong",
        "c_ulonglong",
        "c_longdouble",
        "c_void",
        "f16",
        "f32",
        "f64",
        "f128",
        "bool",
        "void",
        "noreturn",
        "type",
        "anyerror",
        "comptime_int",
        "comptime_float"
    )

    val primitiveTypesLookup = primitiveTypes.map(::createLookup)
}