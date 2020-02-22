package com.kraktun.servilio.executors

import com.kraktun.kutils.log.KLogger
import com.kraktun.servilio.explorer.getSimpleFolder
import com.kraktun.servilio.explorer.listFolders
import com.kraktun.servilio.menu.Menu
import com.kraktun.servilio.utils.CliOptions
import com.kraktun.servilio.utils.OutWriter
import com.kraktun.servilio.utils.time

/**
 * List all subfolders in a folder
 */
class FolderLister {

    companion object {
        private val exe = {
            val targetDir = if (CliOptions.inputFolder != null) CliOptions.inputFolder!! else getSimpleFolder()
            KLogger.flush()
            time {
                println("COLLECTING")
                val fileSet = listFolders(targetDir)
                println("WRITING")
                OutWriter.writeSet(OutWriter.getOutputFile("List_els"), fileSet)
            }
        }

        val menu = Menu(name = "FolderLister", description = "List all subfolders in folder", interact = exe)
    }
}