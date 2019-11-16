package com.kraktun.servilio.executors

import com.kraktun.kutils.log.KLogger
import com.kraktun.servilio.explorer.listFiles
import com.kraktun.servilio.explorer.getSimpleFolder
import com.kraktun.servilio.menu.Menu
import com.kraktun.servilio.utils.CliOptions
import com.kraktun.servilio.utils.OutWriter
import com.kraktun.servilio.utils.time

/**
 * List all files in a folder
 */
class FileLister {

    companion object {
        private val exe = {
            val targetDir = if (CliOptions.inputFolder != null) CliOptions.inputFolder!! else getSimpleFolder()
            KLogger.flush()
            time {
                println("COLLECTING")
                val fileSet = listFiles(targetDir)
                println("WRITING")
                OutWriter.writeSet(OutWriter.getOutputFile("List_els"), fileSet)
            }
        }

        val menu = Menu(name = "FileLister", description = "List all files in folder", interact = exe)
    }
}