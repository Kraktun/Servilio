package com.kraktun.servilio.explorer

import java.io.File

/**
 * Ask to input a path and check if it's a valid folder
 */
fun getSimpleFolder(): String {
    println("Insert target folder and press enter.\n")
    val targetDir = readLine() ?: ""
    return if (validateFolder(targetDir)) {
        targetDir
    } else {
        println("Invalid folder")
        getSimpleFolder()
    }
}

fun validateFolder(path: String) : Boolean {
    return path.isNotEmpty() && File(path).exists() && File(path).isDirectory
}