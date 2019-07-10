package com.lkand.bitcoin_tracker.util

import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

class NetworkUtilUnitTest {
    @Mock
    private lateinit var webSocketListener: WebSocketListener

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Test
    fun createResultTest() {
        assertThat(NetworkUtil.create("wss://ws-feed.gdax.com/", this.webSocketListener), instanceOf(WebSocket::class.java))
    }
}