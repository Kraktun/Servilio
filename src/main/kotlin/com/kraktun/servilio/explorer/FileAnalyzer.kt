package com.kraktun.servilio.explorer

import com.google.common.hash.Hashing
import com.kraktun.servilio.utils.Optimizator
import java.io.BufferedInputStream
import java.io.File
import java.security.MessageDigest
import java.security.DigestInputStream
import java.io.FileInputStream
import java.nio.file.Files


fun getMD5(f: File): String {
    val md = MessageDigest.getInstance("MD5")
    println("Calculating hash of: ${f.name}")
    if (Optimizator.allocate(f)) {
        md.update(Files.readAllBytes(f.toPath()))
    } else {
        val inputStream = BufferedInputStream(FileInputStream(f))
        val digestInputStream = DigestInputStream(inputStream, md)
        val buffer = ByteArray(2048)
        while (digestInputStream.read(buffer) > -1) {
        }
        digestInputStream.close()
    }
    val resultB = md.digest()
    println("Converting hash of: ${f.name}")
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
    println("Calculating hash of: ${f.name}")
    md.update(Files.readAllBytes(f.toPath()))
    val resultB = md.digest()
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

fun getGuavaMD5(f: File) : String {
    println("Calculating hash of: ${f.name}")
    return com.google.common.io.Files.asByteSource(f).hash(Hashing.md5()).toString()
}