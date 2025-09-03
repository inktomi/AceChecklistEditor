package com.inktomi.ace

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform