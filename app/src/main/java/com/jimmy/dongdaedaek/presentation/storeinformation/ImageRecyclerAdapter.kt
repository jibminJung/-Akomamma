package com.jimmy.dongdaedaek.presentation.storeinformation

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimmy.dongdaedaek.R
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
                .override(50,75)
                .placeholder(R.drawable.ic_baseline_timelapse_24)
                .into(binding.imageViewHolder)
        }
    }
    fun addData(list: List<Uri>){
        data.addAll(list)
        notifyDataSetChanged()
    }


}