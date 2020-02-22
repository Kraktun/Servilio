package com.kraktun.servilio.utils

import com.kraktun.kutils.log.KLogger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.measureTimeMillis

const val TAG = "FUNCS"

/**
 * Print & log functions
 */
fun printK(tag: String, s: Any = "") {
    val d = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    print("$d $tag: $s")
    KLogger.logTagged(tag, "$s", true)
}

fun printlnK(tag: String, s: Any = "") {
    val d = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    println("$d $tag: $s")
    KLogger.logTagged(tag, "$s", true)
}

fun logK(tag: String, s: Any = "") {
    KLogger.logTagged(tag, "$s", true)
}

/**
 * Time passed function to check execution time
 */
fun time(func: () -> Any) {
    val time = measureTimeMillis { func() }
    printlnK(TAG, "FUNC TOOK : $time millis")
}
