package com.kraktun.servilio.explorer

import com.kraktun.servilio.utils.printlnK
import java.io.File
import java.util.stream.Collectors

private const val TAG = "FILEEXPLORER"

fun getSimpleFiles(path : String) : Set<String>{
    return File(path).walk().onFail{
        a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}

fun getSimpleDirectories(path : String) : Set<String>{
    return File(path).walk().onFail{
        a, _ -> printlnK(TAG, a)
    }.filter {
        it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}

fun Set<String>.toString(advanced : Boolean) : String {
    return this.stream().collect(Collectors.joining("\n"))
}



