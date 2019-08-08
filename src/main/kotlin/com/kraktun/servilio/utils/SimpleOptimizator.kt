package com.kraktun.servilio.utils

import java.io.File

/**
 * Don't expect for this one to be of some help...
 * The best solution is to use a single thread.
 */
object SimpleOptimizator {

    private const val TAG = "ALLOCATOR"
    private val maxMemory : Long = Runtime.getRuntime().maxMemory() - 307200000 // margin 300MB
    private const val INCREASER = 0.1 //10% margin on file size
    @Volatile
    var allocatedMemory = 0L

    fun allocate(f : File) : Boolean {
        synchronized(this) {
            println("Checking file ${f.name}")
            //println("Allocated memory is: $allocatedMemory")
            //println("Free memory is: ${maxMemory - allocatedMemory}")
            //println("Max memory is: $maxMemory")
            //println("File size is: ${f.length()}")
            val result = maxMemory - allocatedMemory > (f.length() + f.length() * INCREASER).toLong()
            println("Result is: $result")
            if (result) allocatedMemory += (f.length() + f.length() * INCREASER).toLong()
            return result
        }
    }

    fun release(f : File) {
        synchronized(this) {
            allocatedMemory -= (f.length() + f.length() * INCREASER).toLong()
        }
    }
}