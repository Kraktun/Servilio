package com.kraktun.servilio.explorer

import com.kraktun.kutils.coroutines.optimizators.BipartiteOptimizer
import com.kraktun.kutils.coroutines.optimizators.Orchestrator
import com.kraktun.servilio.utils.CliOptions
import com.kraktun.servilio.utils.printlnK
import java.io.File

private const val TAG = "FILEEXPLORER"

/**
 * List all files in a folder.
 * @param path path of the folder
 * @return set containing the paths (absolute) of files as strings
 */
fun listFiles(path: String): Set<String> {
    return File(path).walk().onFail {
        a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}

/**
 * List all files in a folder and calculate md5 of files.
 * @param path path of the folder
 * @return map with path (absolute) of the file as key and md5 as value
 */
fun listMD5Files(path: String): Map<String, String> {
    val list = File(path).walk().onFail {
            a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.toList().sortedByDescending {
        it.length() // sorting by length allows to use getMD5Chunked on small files and getMD5Unorthodox on biggers
    }
    // val newMap = executeToMap<File, String, String>(objects = list, functionK = { it.absolutePath }, functionV = { file -> getMD5(file) })
    val newMap = Orchestrator().run<File, String, String>(
        optimizer = BipartiteOptimizer(files = list, highThreads = CliOptions.highThreads, highFunction = { file -> getMD5Unorthodox(file) }, lowFunction = { file -> getMD5Chunked(file, -1) }),
        functionK = { it.absolutePath },
        channelCapacity = CliOptions.threads*3,
        threads = CliOptions.threads,
        enableLog = true)
    return newMap.toSortedMap()
}

/**
 * List all folders in a folder (aka subdirectories).
 * @param path path of the folder
 * @return set containing the paths (absolute) of folders as strings
 */
fun listFolders(path: String): Set<String> {
    return File(path).walk().onFail {
        a, _ -> printlnK(TAG, a)
    }.filter {
        it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}
