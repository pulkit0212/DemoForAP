package com.ap.demo.ui.splash

import com.ap.demo.core.BaseActivity
import com.ap.demo.databinding.ActivitySplashBinding
import com.ap.demo.ui.main.MainActivity
import com.ap.demo.utils.extensions.openActivity

class Splash : BaseActivity<SplashVM, ActivitySplashBinding>() {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(viewModel: SplashVM, binding: ActivitySplashBinding) {
        super.onCreate(viewModel, binding)
        binding.viewModel = viewModel
        binding.activity = this
        binding.lifecycleOwner = this

    }
    fun callStart(){
        openActivity<MainActivity>()
    }
}