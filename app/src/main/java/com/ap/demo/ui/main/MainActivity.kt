package com.ap.demo.ui.main

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ap.demo.core.BaseActivity
import com.ap.demo.databinding.ActivityMainBinding
import com.ap.demo.ui.adapter.ImagePagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    lateinit var adapter: ImagePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.activity = this
        binding.lifecycleOwner = this

        adapter = ImagePagingAdapter()
        binding.rvCart.adapter = adapter
        callGetImagesAPI()
    }

    private fun callGetImagesAPI() {
        lifecycleScope.launch {
            viewModel.getImagesList().collectLatest {
            launch(Dispatchers.Main) {
                adapter.loadStateFlow.collectLatest { loadStates -> }
            }
                adapter.submitData(it)
            }
        }

    }

}