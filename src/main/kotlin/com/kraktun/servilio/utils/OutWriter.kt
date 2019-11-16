package com.kraktun.servilio.utils

import com.kraktun.kutils.file.getLocalFolder
import com.kraktun.kutils.time.TimeFormat
import com.kraktun.kutils.time.getCurrentDateTimeStamp
import com.kraktun.servilio.Main
import java.io.*

object OutWriter {

    private val outPath: String = "${getLocalFolder(Main::class.java).parentFile.absolutePath}$outFolder"

    fun getOutputFile(basicName: String): File {
        return File("$outPath/$basicName".plus("_${getCurrentDateTimeStamp(pattern = TimeFormat.YMD)}.txt"))
    }

    fun writeSet(file: File, set: Set<String>) {
        FileOutputStream(file, true).bufferedWriter().use { out ->
            set.forEach { out.writeLn(it) }
            out.close()
        }
    }

    private fun BufferedWriter.writeLn(line: String) {
        this.write(line)
        this.newLine()
    }
}