package com.ap.demo.core

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ap.demo.network.base.BaseDataSource
import com.ap.demo.utils.Loader
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<VM : ViewModel, VB : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding: VB
    lateinit var loader: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContent(savedInstanceState)
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        binding = getViewBinding()
        binding.lifecycleOwner = this
        loader = Loader.getLoader(this)
        setContentView(binding.root)
        onCreate(viewModel, binding)
    }

    fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<VM>
    }

    abstract fun getViewBinding(): VB

    open fun beforeSetContent(savedInstanceState: Bundle?) {}

    open fun onCreate(viewModel: VM, binding: VB) {}

    open fun <T> wsWithLoader(dataResource: BaseDataSource.Resource<T>, block: () -> Unit) {
        when (dataResource.status) {
            BaseDataSource.Resource.Status.SUCCESS -> {
                loader.dismiss()
                block.invoke()
            }
            BaseDataSource.Resource.Status.LOADING -> {
                if (!loader.isShowing) {
                    loader.show()
                }
            }
            BaseDataSource.Resource.Status.ERROR -> {
                Toast.makeText(this,dataResource.message.toString(),Toast.LENGTH_SHORT).show()
                loader.dismiss()
            }
        }
    }

}