package com.kraktun.servilio.explorer

import java.io.File

fun getMD5(f: File): String {
    println("Executing ${f.name}")
    return f.name
}
