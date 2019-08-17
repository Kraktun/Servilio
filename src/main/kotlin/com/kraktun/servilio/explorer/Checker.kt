package com.kraktun.servilio.explorer

import java.io.File

/**
 * Ask to input a path and check if it's a valid folder
 */
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