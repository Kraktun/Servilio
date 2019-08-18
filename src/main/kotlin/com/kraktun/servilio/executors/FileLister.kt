package com.kraktun.servilio.executors

import com.kraktun.servilio.explorer.listFiles
import com.kraktun.servilio.explorer.getSimpleFolder
import com.kraktun.servilio.menu.Menu
import com.kraktun.servilio.utils.CliOptions
import com.kraktun.servilio.utils.LoggerK
import com.kraktun.servilio.utils.time

/**
 * List all files in a folder
 */
class FileLister {

    companion object {
        private val exe = {
            val targetDir = if (CliOptions.inputFolder != null) CliOptions.inputFolder!! else getSimpleFolder()
            LoggerK.flush()
            time {
                println("COLLECTING")
                val fileSet = listFiles(targetDir)
                println("WRITING")
                LoggerK.writeSet(LoggerK.getOutputFile("List_els"), fileSet)
            }
        }

        val menu = Menu(name = "FileLister", description = "List all files in folder", interact = exe)
    }
}