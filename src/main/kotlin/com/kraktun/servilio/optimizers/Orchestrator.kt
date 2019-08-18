package com.kraktun.servilio.optimizers

import com.kraktun.servilio.utils.CliOptions
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.indexOf
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * Instantiate the coroutines and assign functions according to the chosen optimizer.
 */
class Orchestrator {

    /**
     * @param elementsCount size of the list passed to the optimizer
     * @param optimizer optimizer
     * @param functionK function to execute on each element of the list passed to the optimizer, to get the key for the map
     * @return map with functionK as key and function passed to the optimizer as value
     */
    @Suppress("UNCHECKED_CAST")
    fun<T, P, K> run(elementsCount : Int, optimizer: Optimizer, functionK: (T) -> P): Map<P, K> {
        val newMap = ConcurrentHashMap<P, K>()
        runBlocking {
            val counter = IntArray(elementsCount) {it + 1}
            val listChannel = Channel<Int>(capacity = CliOptions.threads*3)
            launch {
                counter.forEach {
                    listChannel.send(it)
                }
                listChannel.close()
            }
            val waitingFor = mutableSetOf<Deferred<Unit>>()
            for (t in 1..CliOptions.threads) {
                waitingFor.add(GlobalScope.async(CoroutineName("Core$t")) {
                    for (f in listChannel) {
                        println("Processing element $f/$elementsCount")
                        val result = optimizer.executeNext() as Pair<T, K>
                        newMap[functionK(result.first)] = result.second
                    }
                })
            }
            waitingFor.map { it.await()}
            delay(100)
        }
        return newMap
    }
}