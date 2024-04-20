package com.ap.demo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ap.demo.databinding.ImageItemLayoutBinding
import com.ap.demo.network.beans.ImageModelResponse

class ImagePagingAdapter () : PagingDataAdapter<ImageModelResponse.ImageModelResponseItem, ImagePagingAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ImageItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)!!
        holder.binding.apply {
            val imagePath = result.thumbnail.domain + "/" + result.thumbnail.basePath + "/0/" + result.thumbnail.key
            imageUrl = imagePath
            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ImageItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private class DiffCallback : DiffUtil.ItemCallback<ImageModelResponse.ImageModelResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ImageModelResponse.ImageModelResponseItem,
            newItem: ImageModelResponse.ImageModelResponseItem
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ImageModelResponse.ImageModelResponseItem,
            newItem: ImageModelResponse.ImageModelResponseItem
        ): Boolean = oldItem == newItem
    }

}