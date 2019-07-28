package com.kraktun.servilio.menu

data class Menu(val name: String, val description: String, val interact: () -> Unit)