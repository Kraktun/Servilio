package com.kraktun.servilio.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*

object LoggerK {

    private lateinit var fileHolder: File
    private lateinit var outPath: String
    @Volatile private var textHolder = StringBuilder()

    fun log(s: String) {
        synchronized(this) {
            textHolder.append(s + "\n")
        }
    }

    fun initialize(mainFolder: String) {
        fileHolder = File("$mainFolder$logFolder/log_${getCurrentDateTimeStamp()}.log")
        outPath = "$mainFolder$outFolder"
    }

    fun flush() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                synchronized(this) {
                    if (textHolder.isNotEmpty()) {
                        FileOutputStream(fileHolder, true).bufferedWriter().use {
                            it.write(textHolder.toString())
                            it.close()
                            textHolder = StringBuilder()
                        }
                    }
                }
            }
        }
    }

    fun getOutputFile(basicName: String): File {
        return File("$outPath/$basicName".plus("_${getCurrentDateTimeStamp()}.txt"))
    }

    fun writeSet(file: File, set: Set<String>) {
        synchronized(this) {
            FileOutputStream(file, true).bufferedWriter().use { out ->
                set.forEach { out.writeLn(it) }
                out.close()
            }
        }
    }

    private fun BufferedWriter.writeLn(line: String) {
        this.write(line)
        this.newLine()
    }
}