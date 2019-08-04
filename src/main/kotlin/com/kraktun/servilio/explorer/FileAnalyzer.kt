package com.kraktun.servilio.explorer

import com.google.common.hash.Hashing
import java.io.BufferedInputStream
import java.io.File
import java.security.MessageDigest
import java.security.DigestInputStream
import java.io.FileInputStream
import java.nio.file.Files


fun getMD5(f: File, md : MessageDigest): String {
    val inputStream = BufferedInputStream(FileInputStream(f))
    val digestInputStream = DigestInputStream(inputStream, md)
    //md.update(Files.readAllBytes(f.toPath()))
    val buffer = ByteArray(2048)
    while (digestInputStream.read(buffer) > -1) {}
    val resultB = md.digest()
    digestInputStream.close()
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

fun getMD5Unorthodox(f: File, md : MessageDigest): String {
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

fun getMD52(f: File) : String {
    println("Calculating hash of: ${f.name}")
    return com.google.common.io.Files.asByteSource(f).hash(Hashing.md5()).toString()
}