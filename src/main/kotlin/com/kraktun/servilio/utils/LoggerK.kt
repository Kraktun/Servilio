package com.kraktun.servilio.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*

object LoggerK {

    private lateinit var fileHolder : File
    @Volatile private var textHolder = StringBuilder()

    fun log(s: String) {
        synchronized(this) {
            textHolder.append(s + "\n")
        }
    }

    fun initialize(mainFolder : String) {
        fileHolder = File("$mainFolder$logFolder/log_${getCurrentDateTimeStamp()}.log")
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

    fun writeSet(set : Set<String>) {
        synchronized(this) {
            FileOutputStream(fileHolder, true).bufferedWriter().use { out ->
                set.forEach { out.writeLn(it) }
                out.close()
            }
        }
    }

    fun BufferedWriter.writeLn(line: String) {
        this.write(line)
        this.newLine()
    }
}