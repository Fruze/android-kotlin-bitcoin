package com.lkand.bitcoin_tracker.feature.dashboard.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.TextView
import com.lkand.bitcoin_tracker.BR
import com.lkand.bitcoin_tracker.R
import com.lkand.bitcoin_tracker.databinding.ActivityDashboardBinding
import com.lkand.bitcoin_tracker.feature.dashboard.viewmodel.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initBinding()
        this.initViewModel()
    }

    private fun initBinding() {
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        this.setBinding()
    }

    private fun setBinding() {
        this.binding.setVariable(BR.viewmodel, this.viewModel)
        this.binding.executePendingBindings()
    }

    private fun initViewModel() {
        this.viewModel.transform().observe(this, Observer { _ ->
            this.checkStatus()
            this.setBinding()
        })
    }

    private fun checkStatus() {
        if (dashboardBuyPrice.text.toString() > this.viewModel.getResponseBuyModel().price.toString()) {
            this.animateStatus(dashboardBuyPrice, Color.RED)
        }
        else if (dashboardBuyPrice.text.toString() < this.viewModel.getResponseBuyModel().price.toString()) {
            this.animateStatus(dashboardBuyPrice, Color.GREEN)
        }

        if (dashboardSellPrice.text.toString() > this.viewModel.getResponseSellModel().price.toString()) {
            this.animateStatus(dashboardSellPrice, Color.RED)
        }
        else if (dashboardSellPrice.text.toString() < this.viewModel.getResponseSellModel().price.toString()) {
            this.animateStatus(dashboardSellPrice, Color.GREEN)
        }
    }

    private fun animateStatus(textView: TextView, color: Int) {
        ObjectAnimator.ofObject(
            textView,
            "textColor",
            ArgbEvaluator(),
            color,
            Color.WHITE)
            .setDuration(500)
            .start()
    }

}