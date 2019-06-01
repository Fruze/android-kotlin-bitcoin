package com.lkand.bitcoin_tracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.lkand.bitcoin_tracker.util.NetworkUtil
import okhttp3.*
import okio.ByteString

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val websocket = NetworkUtil.create("wss://ws-feed.pro.coinbase.com", bitcoinRateListener())

        val timer = object: CountDownTimer(5000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("DebugUtil, tick", millisUntilFinished.toString())
            }
            override fun onFinish() {
                websocket.close(1000, "5s up")
            }
        }
        timer.start()

    }

    private fun bitcoinRateListener(): WebSocketListener {
        return object: WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)

                webSocket.send("""
                    {
                        "type": "subscribe",
                        "product_ids": [
                            "BTC-USD"
                        ],
                        "channels": [
                            "matches"
                        ]
                    }
                """.trimIndent())
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)

                Log.d("DebugUtil, byte", bytes.toString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                Log.d("DebugUtil, string", text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)

                webSocket.close(1000, null)
                runOnUiThread(object: Runnable {
                    override fun run() {
                        Log.d("DebugUtil, run", "close")
                    }
                })
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                t.printStackTrace()
            }

        }
    }
}
