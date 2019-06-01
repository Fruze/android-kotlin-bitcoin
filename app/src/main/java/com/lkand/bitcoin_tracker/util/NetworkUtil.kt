package com.lkand.bitcoin_tracker.util

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

abstract class NetworkUtil {

    companion object {
        fun create(url: String, listener: WebSocketListener): WebSocket {
            val client = OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build()

            val request = Request.Builder()
                .url(url)
                .build()

            return client.newWebSocket(request, listener)
        }
    }

}