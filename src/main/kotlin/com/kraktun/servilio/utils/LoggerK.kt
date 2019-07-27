package com.kraktun.servilio.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

object LoggerK {

    private val fileHolder = File(getMainFolder().plus("/logs/log_").plus(getCurrentDateTimeStamp()).plus(".log"))
    @Volatile private var textHolder = StringBuilder()

    fun log(s: String) {
        synchronized(this) {
            textHolder.append(s + "\n")
        }
    }

    fun flush() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                synchronized(this) {
                    if (textHolder.isNotEmpty()) {
                        val outStream = FileOutputStream(fileHolder, true)
                        val buffW = OutputStreamWriter(outStream, "UTF-8")
                        try {
                            buffW.write(textHolder.toString())
                            textHolder = StringBuilder()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } finally {
                            buffW.close()
                            outStream.close()
                        }
                    }
                }
            }
        }
    }
}