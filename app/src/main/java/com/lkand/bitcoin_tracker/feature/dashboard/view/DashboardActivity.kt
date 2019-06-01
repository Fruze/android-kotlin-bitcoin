package com.lkand.bitcoin_tracker.feature.dashboard.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.lkand.bitcoin_tracker.BR
import com.lkand.bitcoin_tracker.R
import com.lkand.bitcoin_tracker.databinding.ActivityDashboardBinding
import com.lkand.bitcoin_tracker.feature.dashboard.viewmodel.DashboardViewModel

class DashboardActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this).get(DashboardViewModel::class.java)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setBinding()
        this.setViewModel()

    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.setVariable(BR.viewmodel, this.viewModel)
        binding.executePendingBindings()
    }

    private fun setViewModel() {
        this.viewModel.transform().observe(this, Observer { model ->
            Log.d("DebugUtil", "Changed" + model.toString())
            binding.setVariable(BR.viewmodel, this.viewModel)
            binding.executePendingBindings()
        })
    }

}