package com.lkand.bitcoin_tracker.feature.dashboard.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lkand.bitcoin_tracker.BR
import com.lkand.bitcoin_tracker.R
import com.lkand.bitcoin_tracker.databinding.ActivityDashboardBinding
import com.lkand.bitcoin_tracker.feature.dashboard.viewmodel.DashboardViewModel

class DashboardActivity: AppCompatActivity() {

    private val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.setVariable(BR.viewmodel, this.viewModel)
        binding.executePendingBindings()

        this.viewModel.transform()
        binding.setVariable(BR.viewmodel, this.viewModel)
        binding.executePendingBindings()
    }

}