package com.sega.videocaption

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform