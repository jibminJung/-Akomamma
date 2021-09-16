package com.jimmy.dongdaedaek.presentation.storeinformation

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimmy.dongdaedaek.databinding.ItemImageBinding

class ImageRecyclerAdapter: RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>() {
    val data: MutableList<Uri> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder =
        ImageHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int =
        data.size


    inner class ImageHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(binding.root)
                .load(uri)
                .into(binding.imageViewHolder)
        }
    }
    fun addData(uri: Uri){
        data.add(uri)
        notifyDataSetChanged()
    }


}