package com.kraktun.servilio

import com.kraktun.servilio.explorer.getSimpleFiles
import com.kraktun.servilio.utils.*
import java.io.File
import kotlin.system.exitProcess

private const val TAG = "MAIN"

class Main

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
    val currentDir = getMainFolder()
    println("Current dir is $currentDir")
    onStart(currentDir)
    val targetDir = if (args.isNotEmpty()) args[0] else {
        println("Insert target folder and press enter.\n")
        readLine() ?: ""
    }
    if (targetDir.isEmpty()) {
        println("Invalid folder")
        exitProcess(1)
    }
    LoggerK.flush()
    time {
        println("COLLECTING")
        val fileSet = getSimpleFiles(targetDir)
        println("WRITING")
        LoggerK.writeSet(fileSet)
    }
}

/**
 * What to execute on start
 */
fun onStart(folder : String) {
    printlnK(TAG, "Starting system")
    printlnK(TAG, "Current version is: ${com.kraktun.servilio.Main::class.java.getPackage().implementationVersion}")
    printlnK(TAG, "Checking folders")
    val logs = File("$folder$logFolder")
    if (!logs.exists()) {
        printlnK(TAG, "Creating logs folder")
        logs.mkdir()
    }
    printlnK(TAG, "Folders checked")
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