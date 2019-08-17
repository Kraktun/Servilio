package com.kraktun.servilio.utils

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int

/**
 * Options in CLI
 */
object CliOptions : CliktCommand() {
    val threads: Int by option("-t", "--threads", help = "Number of threads").int().default(1).validate {
        require(it > 0) { "Value must be > 0" }
    }

    override fun run() {
    }
}