package com.lkand.bitcoin_tracker.feature.dashboard.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.CountDownTimer
import android.util.Log
import com.lkand.bitcoin_tracker.feature.dashboard.model.DashboardResponseModel
import com.lkand.bitcoin_tracker.util.NetworkUtil
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class DashboardViewModel: ViewModel() {

    private val responseModel = MutableLiveData<DashboardResponseModel>()
    private val responseBuyModel = MutableLiveData<DashboardResponseModel>()
    private val responseSellModel = MutableLiveData<DashboardResponseModel>()

    fun transform(): MutableLiveData<DashboardResponseModel> {
        val socket = NetworkUtil.create("wss://ws-feed.pro.coinbase.com", bitcoinRateListener())

        val timer = object: CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                 Log.d("DebugUtil, tick", millisUntilFinished.toString())
            }
            override fun onFinish() {
                socket.close(1000, "10s up")
            }
        }
        timer.start()

        return responseModel
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

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                val responseModel = DashboardResponseModel(text)
                this@DashboardViewModel.responseModel.postValue(responseModel)
                Log.d("DebugUtil, ", text)

                if (responseModel.side == "sell") {
                    this@DashboardViewModel.responseSellModel.postValue(responseModel)
                }
                else if (responseModel.side == "buy") {
                    this@DashboardViewModel.responseBuyModel.postValue(responseModel)
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)

                webSocket.close(1000, null)
                Log.d("DebugUtil", "Closed")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)

                t.printStackTrace()
            }

        }
    }

    fun getResponseBuyModel(): DashboardResponseModel {
        return if(this.responseBuyModel.value != null) this.responseBuyModel.value!! else DashboardResponseModel("""
            {
                type: "Loading ...",
                price: 0,
                side: "Loading ..."
            }
        """.trimIndent())
    }

    fun getResponseSellModel(): DashboardResponseModel {
        return if(this.responseSellModel.value != null) this.responseSellModel.value!! else DashboardResponseModel("""
            {
                type: "Loading ...",
                price: 0,
                side: "Loading ..."
            }
        """.trimIndent())
    }
}