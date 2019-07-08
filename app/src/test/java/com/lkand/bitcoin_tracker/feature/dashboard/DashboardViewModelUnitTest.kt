package com.lkand.bitcoin_tracker.feature.dashboard

import com.lkand.bitcoin_tracker.feature.dashboard.model.DashboardResponseModel
import com.lkand.bitcoin_tracker.feature.dashboard.viewmodel.DashboardViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.Mock
import org.powermock.reflect.Whitebox
import org.mockito.Mockito.`when` as whenever

class DashboardViewModelUnitTest {
    private val viewModel = DashboardViewModel()

    @Mock
    private lateinit var dashboardResponseModel: DashboardResponseModel

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Test
    fun getResponseBuyModelValidTest() {
        Whitebox.setInternalState(this.viewModel, "emptyDashboardResponseModel", this.dashboardResponseModel)
        whenever(this.dashboardResponseModel.side).thenReturn("buy")
        whenever(this.dashboardResponseModel.price).thenReturn(1000.00)

        val responseBuyModel = this.viewModel.getResponseBuyModel()
        assertEquals("buy", responseBuyModel.side)
        assertEquals(1000.00, responseBuyModel.price)
    }

    @Test
    fun getResponseBuyModelEmptyTest() {
        val responseBuyModel = this.viewModel.getResponseBuyModel()
        assertEquals("Loading ...", responseBuyModel.side)
        assertEquals(0.0, responseBuyModel.price)
    }

    @Test
    fun getResponseSellModelValidTest() {
        Whitebox.setInternalState(this.viewModel, "emptyDashboardResponseModel", this.dashboardResponseModel)
        whenever(this.dashboardResponseModel.side).thenReturn("sell")
        whenever(this.dashboardResponseModel.price).thenReturn(2000.00)

        val responseBuyModel = this.viewModel.getResponseBuyModel()
        assertEquals("sell", responseBuyModel.side)
        assertEquals(2000.00, responseBuyModel.price)
    }

    @Test
    fun getResponseSellModelEmptyTest() {
        val responseBuyModel = this.viewModel.getResponseBuyModel()
        assertEquals("Loading ...", responseBuyModel.side)
        assertEquals(0.0, responseBuyModel.price)
    }

}