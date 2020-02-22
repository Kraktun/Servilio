package com.kraktun.servilio.explorer

import java.io.BufferedInputStream
import java.io.File
import java.security.MessageDigest
import java.security.DigestInputStream
import java.io.FileInputStream
import java.nio.file.Files

/**
 * Get MD5 of file and stop to calculate hash at target size.
 * @param file file. Must be a valid file.
 * @param chunk size of data to digest. -1 to digest all file.
 */
fun getMD5Chunked(file: File, chunk: Long = 102400L): String {
    val md = MessageDigest.getInstance("MD5")
    // println("Using MD5 Chunked. Chunk is $chunk")
    println("Calculating hash of: ${file.name}")
    // println("Size is: ${file.length()}")
    val inputStream = BufferedInputStream(FileInputStream(file))
    val digestInputStream = DigestInputStream(inputStream, md)
    val buffer = ByteArray(2048)
    if (chunk > 0) {
        var counter = 0
        while (digestInputStream.read(buffer) > -1 && counter <= chunk) {
            counter += 2048
        }
    } else {
        while (digestInputStream.read(buffer) > -1) {}
    }
    digestInputStream.close()
    val resultB = md.digest()
    return bytesToString(resultB)
}

/**
 * Get MD5 of file reading all bytes.
 * Faster than getMD5Chunked, but requires more memory.
 * @param file file
 */
fun getMD5Unorthodox(file: File): String {
    val md = MessageDigest.getInstance("MD5")
    // println("Using MD5 Unorthodox")
    println("Calculating hash of: ${file.name}")
    // println("Size is: ${file.length()}")
    md.update(Files.readAllBytes(file.toPath()))
    val resultB = md.digest()
    return bytesToString(resultB)
}

/**
 * Converts byteArray to string.
 * @param bytes array of bytes
 */
fun bytesToString(bytes: ByteArray): String {
    var result = ""
    for (element in bytes) {
        val hex = Integer.toHexString(0xFF and element.toInt())
        if (hex.length == 1) {
            result += '0'
        }
        result += hex
    }
    return result
}