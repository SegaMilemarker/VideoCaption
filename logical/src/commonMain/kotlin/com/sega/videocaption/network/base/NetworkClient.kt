package com.sega.videocaption.network.base

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

class NetworkClient(val client: HttpClient) {

    suspend inline fun <reified T> request(target: NetworkTarget, body: Any? = null): Result<T> {
        return try {
            val response: HttpResponse = client.request {
                url("${target.baseURL}${target.path}")
                method = target.method
                target.headers?.forEach { header(it.key, it.value) }
                target.params?.forEach { parameter(it.key, it.value) }

                if (body != null) {
                    setBody(body)
                }
            }

            val responseBody: T = response.body()
            Result.success(responseBody)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}