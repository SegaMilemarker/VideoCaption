package com.sega.videocaption.network.base

import io.ktor.http.HttpMethod

interface NetworkTarget {
    val baseURL: String
    val path: String
    val method: HttpMethod
    val params: Map<String, String>?
    val headers: Map<String, String>?
}