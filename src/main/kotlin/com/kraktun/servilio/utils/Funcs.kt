package com.kraktun.servilio.utils

import java.io.File
import java.net.URLDecoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

const val TAG = "FUNCS"

/**
 * Execute function for a collection, defaults to passed value if list is empty or null, or an exception is thrown.
 */
inline fun <E : Any, T : Collection<E>> T?.ifNotEmpty(func: T.() -> Any?, default: Any?): Any? {
    return if (this != null && this.isNotEmpty()) {
        try {
            func(this)
        } catch (e: Exception) {
            default
        }
    } else default
}

/**
 * Print & log functions
 */
fun printK(tag: String, s: Any = "") {
    val d = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    print("$d $tag: $s")
    LoggerK.log("$d $tag: $s")
}

fun printlnK(tag: String, s: Any = "") {
    val d = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    println("$d $tag: $s")
    LoggerK.log("$d $tag: $s")
}

fun logK(tag: String, s: Any = "") {
    val d = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    LoggerK.log("$d $tag: $s")
}

/**
 * Execute script with passed arguments in custom amount of time
 */
fun File.executeScript(
    timeoutAmount: Long,
    timeoutUnit: TimeUnit,
    vararg arguments: String
): String {
    val process = ProcessBuilder(*arguments)
        .directory(this)
        .start()
        .apply { waitFor(timeoutAmount, timeoutUnit) }

    if (process.exitValue() != 0) {
        return process.errorStream.bufferedReader().readText().substringBeforeLast("\n")
    }
    return process.inputStream.bufferedReader().readText().substringBeforeLast("\n")
}

/**
 * Execute script with passed arguments in fixed amount of time
 */
fun File.executeScript(vararg arguments: String): String {
    return this.executeScript(10, TimeUnit.SECONDS, *arguments)
}

/**
 * Get current date and time formatted as yyyy-MM-dd_HH-mm-ss
 */
fun getCurrentDateTimeStamp(): String {
    return LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
}

fun time(func : () -> Any) {
    val start = System.nanoTime()
    func()
    val end = System.nanoTime()
    printlnK(TAG, "FUNC TOOK : ${end-start} nanoseconds")
}

/**
 * Get parent folder of java\jar file specified.
 * From: https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
 * Edited.
 * @return path to current folder
 * @throws Exception
 */
@Throws(Exception::class)
fun getMainFolder(): String {
    val kClass = com.kraktun.servilio.Main::class.java
    val codeSource = kClass.protectionDomain.codeSource
    val jarFile: File
    if (codeSource.location != null) {
        jarFile = File(codeSource.location.toURI())
    } else {
        val path = kClass.getResource(kClass.simpleName + ".class").path
        var jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"))
        jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8")
        jarFile = File(jarFilePath)
    }
    return jarFile.parentFile.absolutePath
}