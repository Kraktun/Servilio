package com.kraktun.servilio.menu

import com.kraktun.servilio.utils.printlnK

/**
 * Menu that display available options
 */
object MainMenu {

    private const val TAG = "MAINMENU"
    private val holder = mutableListOf<Menu>()

    fun register(newMenu: Menu) {
        synchronized(this) {
            holder.add(newMenu)
        }
    }

    fun size(): Int {
        return holder.size
    }

    fun linkTo(index: Int) {
        printlnK(TAG, "Executing ${holder[index].name}")
        holder[index].interact()
    }

    fun show() {
        synchronized(this) {
            println()
            println("OPTIONS")
            println()
            holder.forEach {
                println("${holder.indexOf(it)} - ${it.name}: [${it.description}]")
            }
            println()
            println("Choose a number")
            val number = readLine() ?: "-1"
            val parsedNumber = number.toIntOrNull()
            if (parsedNumber != null && parsedNumber >= 0 && parsedNumber < holder.size) {
                linkTo(parsedNumber)
            } else {
                println("Invalid value.")
                println()
                show()
            }
        }
    }
}