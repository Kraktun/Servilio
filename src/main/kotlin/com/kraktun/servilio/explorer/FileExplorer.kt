package com.kraktun.servilio.explorer

import com.kraktun.servilio.utils.printlnK
import java.io.File
import java.security.MessageDigest

private const val TAG = "FILEEXPLORER"

fun listFiles(path: String): Set<String> {
    return File(path).walk().onFail {
        a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}

fun listMD5Files(path: String): Map<String, String> {
    val list = File(path).walk().onFail {
            a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.toList()
    val newMap = executeToMap<File, String, String, MessageDigest>(objects = list, functionK = { it.absolutePath }, functionV = { file, md -> getMD5Unorthodox(file, md) }, functionPre = {MessageDigest.getInstance("MD5")})
    return newMap.toSortedMap()
}

fun listFolders(path: String): Set<String> {
    return File(path).walk().onFail {
        a, _ -> printlnK(TAG, a)
    }.filter {
        it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}
