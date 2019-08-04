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
 */
fun <T, P, K> executeToMap(objects: List<T>, functionK: (T) -> P, functionV: (T) -> K): Map<P, K> {
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
                for (f in listChannel) {
                    //println("Executing coroutine $t")
                    newMap[functionK(f)] = functionV(f)
                }
            })
        }
        waitingFor.map { it.await()}
        delay(100)
    }
    return newMap
}