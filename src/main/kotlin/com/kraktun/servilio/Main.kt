package com.kraktun.servilio

import com.kraktun.servilio.explorer.getSimpleFiles
import com.kraktun.servilio.explorer.toString
import com.kraktun.servilio.utils.*
import java.io.File

private const val TAG = "MAIN"

fun main(args : Array<String>) {
    val mainThread = Thread.currentThread()
    Runtime.getRuntime().addShutdownHook(Thread {
        onShutdown()
        try {
            mainThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    })
    val currentDir = if (args.isNotEmpty()) args[0] else {
        println("Could not detect current folder, insert current folder and press enter.\n")
        readLine() ?: ""
    }
    if (currentDir.isEmpty()) {
        println("Invalid folder")
        System.exit(1)
    }
    println("Passed dir is $currentDir")
    onStart(currentDir)
    val start = System.nanoTime()
    val fileSet = getSimpleFiles(currentDir)
    println(fileSet.toString(true))
    val end = System.nanoTime()
    printlnK(TAG, "TIME: ${end-start}")
}

/**
 * What to execute on start
 */
fun onStart(folder : String) {
    printlnK(TAG, "Starting system")
    printlnK(TAG, "Checking folders")
    val logs = File("$folder/logs")
    if (!logs.exists()) {
        printlnK(TAG, "Creating logs folder")
        logs.mkdir()
    }
    LoggerK.initialize(folder)
}

/**
 * What to execute when closing
 */
fun onShutdown() {
    printlnK(TAG, "Closing system")
    LoggerK.flush()
    Thread.sleep(2000)
}