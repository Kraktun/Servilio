package com.kraktun.servilio.explorer

import java.io.File
import java.nio.file.Files
import java.security.MessageDigest



fun getMD5(f: File, md : MessageDigest): String {
    md.update(Files.readAllBytes(f.toPath()))
    val resultB = md.digest()
    println("Calculating hash of: ${f.name}")
    var result = ""
    for (i in 0 until resultB.size) {
        val hex = Integer.toHexString(0xFF and resultB[i].toInt())
        if (hex.length == 1) {
            result += '0'
        }
        result += hex
    }
    return result
}