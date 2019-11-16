package com.kraktun.servilio.utils

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import com.kraktun.servilio.explorer.validateFolder
import com.kraktun.servilio.menu.MainMenu

/**
 * Options in CLI
 */
object CliOptions : CliktCommand() {
    val threads: Int by option("-t", "--threads", help = "Number of threads").int().default(1).validate {
        require(it > 0) { "Value must be > 0" }
    }

    val highThreads: Int by option("-ht", "--highthreads", help = "Number of threads for intensive functions. Must be <= threads").int().default(1).validate {
        require(it in 0..threads) { "Value must be between 0 and threads" }
    }

    val menu: Int by option("--option", help = "Execute an option from the menu without interaction").int().default(-1).validate {
        require( it < MainMenu.size()) {"Value must the one shown in the main menu"}
    }

    val inputFolder: String? by option("-if", "--inputFolder", help = "Input folder").validate {
        require(validateFolder(it)){"Invalid directory"}
    }

    override fun run() {
    }
}