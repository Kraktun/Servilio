package com.kraktun.servilio.executors

import com.kraktun.servilio.explorer.getSimpleFiles
import com.kraktun.servilio.menu.Menu
import com.kraktun.servilio.utils.LoggerK
import com.kraktun.servilio.utils.time

class FileLister {

    companion object {
        private fun showMenu1(): String {
            println("Insert target folder and press enter.\n")
            val targetDir = readLine() ?: ""
            return if (targetDir.isEmpty()) {
                println("Invalid folder")
                showMenu1()
            } else {
                targetDir
            }
        }

        private val exe = {
            val targetDir = showMenu1()
            LoggerK.flush()
            time {
                println("COLLECTING")
                val fileSet = getSimpleFiles(targetDir)
                println("WRITING")
                LoggerK.writeSet(LoggerK.getOutputFile("List_els"), fileSet)
            }
        }

        val menu = Menu(name = "FileLister", description = "List all files in folder", interact = exe)
    }
}