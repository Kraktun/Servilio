package com.kraktun.servilio.explorer

import com.kraktun.servilio.utils.getMD5
import com.kraktun.servilio.utils.printlnK
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import java.io.File

private const val TAG = "FILEEXPLORER"

fun listFiles(path: String): Set<String> {
    return File(path).walk().onFail {
        a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}

@UseExperimental(ExperimentalCoroutinesApi::class)
fun listMD5Files(path : String) : Map<String, String> {
    val list = File(path).walk().onFail {
            a, _ -> printlnK(TAG, a)
    }.filter {
        !it.isDirectory
    }.toList()
    val newMap = mutableMapOf<String, String>()
    runBlocking {
        val listChannel = Channel<File>(capacity = 10)
        launch {
            for (file in list) {
                listChannel.send(file)
            }
            listChannel.close()
        }
        coroutineScope {
            launch (CoroutineName("Core1")){
                for (f in listChannel)
                    newMap[f.absolutePath] = f.getMD5()
            }
            launch (CoroutineName("Core2")){
                for (f in listChannel)
                    newMap[f.absolutePath] = f.getMD5()
            }
            launch (CoroutineName("Core3")){
                for (f in listChannel)
                    newMap[f.absolutePath] = f.getMD5()
            }
            launch (CoroutineName("Core4")){
                for (f in listChannel)
                    newMap[f.absolutePath] = f.getMD5()
            }
        }
    }
    return newMap.toSortedMap()
}

fun listFolders(path: String): Set<String> {
    return File(path).walk().onFail {
        a, _ -> printlnK(TAG, a)
    }.filter {
        it.isDirectory
    }.map {
        it.absolutePath
    }.toSet()
}
