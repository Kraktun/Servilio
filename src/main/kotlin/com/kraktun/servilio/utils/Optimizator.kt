package com.kraktun.servilio.utils

import java.io.File

/**
 * Don't expect for this one to be of some help...
 * The best solution is to use a single thread.
 */
object Optimizator {

    const val TAG = "ALLOCATOR"
    private val maxMemory : Long = Runtime.getRuntime().maxMemory()
    @Volatile
    var allocatedMemory = Runtime.getRuntime().totalMemory()
    @Volatile
    var freeMemory = Runtime.getRuntime().freeMemory()

    fun allocate(f : File) : Boolean {
        refresh()
        synchronized(this) {
            printlnK(TAG, "Checking file ${f.name}")
            printlnK(TAG, "Allocated memory is: $allocatedMemory")
            printlnK(TAG, "Free memory is: $freeMemory")
            printlnK(TAG, "Max memory is: $maxMemory")
            printlnK(TAG, "File size is: ${f.length()}")
            printlnK(TAG, "Result is: ${(maxMemory - allocatedMemory > f.length()) || (f.length() < freeMemory)}")
            return (maxMemory - allocatedMemory > f.length()) || (f.length() < freeMemory)
        }
    }

    private fun refresh() {
        synchronized(this) {
            allocatedMemory = Runtime.getRuntime().totalMemory()
            freeMemory = Runtime.getRuntime().freeMemory()
        }
    }
}