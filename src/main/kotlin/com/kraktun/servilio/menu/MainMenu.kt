package com.kraktun.servilio.menu

object MainMenu {

    private val holder = mutableListOf<Menu>()

    fun register(newMenu: Menu) {
        synchronized(this) {
            holder.add(newMenu)
        }
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
                holder[number.toInt()].interact()
            } else {
                println("Invalid value.")
                println()
                show()
            }
        }
    }
}