package com.kraktun.servilio.executors

import com.kraktun.kutils.log.KLogger
import com.kraktun.servilio.explorer.getSimpleFolder
import com.kraktun.servilio.explorer.listMD5Files
import com.kraktun.servilio.menu.Menu
import com.kraktun.servilio.utils.CliOptions
import com.kraktun.servilio.utils.OutWriter
import com.kraktun.servilio.utils.time

/**
 * List all files in a folder and calculate md5
 */
class FileMD5Lister {

    companion object {
        private val exe = {
            val targetDir = if (CliOptions.inputFolder != null) CliOptions.inputFolder!! else getSimpleFolder()
            KLogger.flush()
            time {
                println("COLLECTING")
                val fileSet = listMD5Files(targetDir).map { "${it.value}  ${it.key}" }.toSet()
                println("WRITING")
                OutWriter.writeSet(OutWriter.getOutputFile("List_els"), fileSet)
            }
        }

        val menu = Menu(name = "FileMD5Lister", description = "List all files in folder and calculate MD5", interact = exe)
    }
}