package com.kraktun.servilio.explorer

import com.google.common.hash.Hashing
import com.kraktun.servilio.utils.SimpleOptimizator
import java.io.BufferedInputStream
import java.io.File
import java.security.MessageDigest
import java.security.DigestInputStream
import java.io.FileInputStream
import java.nio.file.Files


fun getMD5(f: File): String {
    val md = MessageDigest.getInstance("MD5")
    println("Calculating hash of: ${f.name}")
    val allocate = SimpleOptimizator.allocate(f)
    if (allocate) {
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
    if (allocate)
        SimpleOptimizator.release(f)
    return result
}

fun getMD5Chunked(f: File, chunk: Long = 102400L): String {
    val md = MessageDigest.getInstance("MD5")
    println("Calculating hash of: ${f.name}")
    val inputStream = BufferedInputStream(FileInputStream(f))
    val digestInputStream = DigestInputStream(inputStream, md)
    val buffer = ByteArray(2048)
    var counter = 0
    while (digestInputStream.read(buffer) > -1 && counter <= chunk) {
        counter += 2048
    }
    digestInputStream.close()
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

fun getMD5Unorthodox(f: File): String {
    val md = MessageDigest.getInstance("MD5")
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