package com.kraktun.servilio

import com.kraktun.kutils.file.BuildEnv
import com.kraktun.kutils.file.getLocalFolder
import com.kraktun.kutils.log.LOG_OUTPUT_FOLDER
import com.kraktun.kutils.log.LogFolder
import com.kraktun.kutils.time.TimeFormat
import com.kraktun.servilio.executors.FileLister
import com.kraktun.servilio.executors.FileMD5Lister
import com.kraktun.kutils.log.KLogger
import com.kraktun.servilio.executors.FolderLister
import com.kraktun.servilio.menu.MainMenu
import com.kraktun.servilio.utils.*
import java.io.File
import kotlin.system.exitProcess

private const val TAG = "MAIN"

class Main

fun main(args: Array<String>) {
    Runtime.getRuntime().addShutdownHook(Thread {
        onShutdown()
    })
    val currentDir = getLocalFolder(Main::class.java).parentFile.absolutePath
    println("Current dir is $currentDir")
    onStart(currentDir)
    MainMenu.register(FileLister.menu)
    MainMenu.register(FileMD5Lister.menu)
    MainMenu.register(FolderLister.menu)
    CliOptions.main(args)
    if (CliOptions.menu >= 0) {
        MainMenu.linkTo(CliOptions.menu)
    } else {
        MainMenu.show()
    }
    exitProcess(0)
}

/**
 * What to execute on start
 */
fun onStart(folder: String) {
    KLogger.initialize(
        c = Main::class.java,
        type = LogFolder.PARENT,
        pattern = TimeFormat.YMD,
        logFolder = LOG_OUTPUT_FOLDER,
        buildEnv = BuildEnv.INTELLIJ)
    // printlnK(TAG, "Log file is: ${KLogger.getOutputFile().absolutePath}")
    logK(TAG, "Starting system")
    logK(TAG, "Script version is: ${com.kraktun.servilio.Main::class.java.getPackage().implementationVersion}")
    println("Script version is: ${com.kraktun.servilio.Main::class.java.getPackage().implementationVersion}")
    logK(TAG, "Checking folders")
    val outs = File("$folder$outFolder")
    if (!outs.exists() || !outs.isDirectory) {
        logK(TAG, "Creating output folder")
        outs.mkdir()
    }
    logK(TAG, "Folders checked")
}

/**
 * What to execute when closing
 */
fun onShutdown() {
    println("Closing system")
    KLogger.close()
    Thread.sleep(2000)
    println("DONE")
}