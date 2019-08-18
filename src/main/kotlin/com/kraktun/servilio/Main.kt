package com.kraktun.servilio

import com.kraktun.servilio.executors.FileLister
import com.kraktun.servilio.executors.FileMD5Lister
import com.kraktun.servilio.menu.MainMenu
import com.kraktun.servilio.utils.*
import java.io.File

private const val TAG = "MAIN"

class Main

fun main(args: Array<String>) {
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
    MainMenu.register(FileLister.menu)
    MainMenu.register(FileMD5Lister.menu)
    CliOptions.main(args)
    if (CliOptions.menu > 0) {
        MainMenu.linkTo(CliOptions.menu)
    } else {
        MainMenu.show()
    }
}

/**
 * What to execute on start
 */
fun onStart(folder: String) {
    logK(TAG, "Starting system")
    logK(TAG, "Script version is: ${com.kraktun.servilio.Main::class.java.getPackage().implementationVersion}")
    println("Script version is: ${com.kraktun.servilio.Main::class.java.getPackage().implementationVersion}")
    logK(TAG, "Checking folders")
    val logs = File("$folder$logFolder")
    if (!logs.exists() || !logs.isDirectory) {
        logK(TAG, "Creating logs folder")
        logs.mkdir()
    }
    val outs = File("$folder$outFolder")
    if (!outs.exists() || !outs.isDirectory) {
        logK(TAG, "Creating output folder")
        outs.mkdir()
    }
    logK(TAG, "Folders checked")
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