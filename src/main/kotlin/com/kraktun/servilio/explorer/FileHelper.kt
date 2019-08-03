package com.kraktun.servilio.explorer

import com.kraktun.servilio.utils.CliOptions
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.ConcurrentHashMap

/**
 * Execute functions on list with coroutines.
 * @param objects list of objects on which execute the function
 * @param functionK function executed on a object that will get the key of that object for the map
 * @param functionV function executed on a object that will be the value of that object for the map
 * @param functionPre function to execute once per coroutine, its result will be passed to functionV
 */
fun <T, P, K, Z> executeToMap(objects: List<T>, functionK: (T) -> P, functionV: (T, Z) -> K, functionPre: () -> Z): Map<P, K> {
    val newMap = ConcurrentHashMap<P, K>()
    runBlocking {
        val listChannel = Channel<T>(capacity = CliOptions.threads*3)
        launch {
            objects.forEach {
                listChannel.send(it)
            }
            listChannel.close()
        }
        val waitingFor = mutableSetOf<Deferred<Unit>>()
        for (t in 1..CliOptions.threads) {
            waitingFor.add(GlobalScope.async(CoroutineName("Core$t")) {
                val z = functionPre()
                for (f in listChannel) {
                    newMap[functionK(f)] = functionV(f, z)
                }
            })
        }
        waitingFor.map { it.await()}
        delay(100)
    }
    return newMap
}