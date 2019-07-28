package com.kraktun.servilio.explorer

import java.io.File

fun getSimpleFolder(): String {
    println("Insert target folder and press enter.\n")
    val targetDir = readLine() ?: ""
    return if (targetDir.isEmpty() || !File(targetDir).exists() || !File(targetDir).isDirectory) {
        println("Invalid folder")
        getSimpleFolder()
    } else {
        targetDir
    }
}