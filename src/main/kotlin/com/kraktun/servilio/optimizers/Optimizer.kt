package com.kraktun.servilio.optimizers

interface Optimizer {

    /**
     * The result must always be a pair, with the input value as the first element
     */
    fun executeNext() : Any
}