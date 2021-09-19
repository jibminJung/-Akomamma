package com.jimmy.dongdaedaek.presentation.storeinformation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jimmy.dongdaedaek.databinding.ItemReviewImageBinding

class ReviewPhotoAdapter : RecyclerView.Adapter<ReviewPhotoAdapter.ReviewPhotoViewHolder>() {
    val data: MutableList<String> = mutableListOf()
    var clickListener : ((List<String>,Int) -> Unit)? = null

    inner class ReviewPhotoViewHolder(val binding: ItemReviewImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                clickListener?.invoke(data,adapterPosition)
            }
        }
        fun bind(url: String) {
            Glide.with(binding.root.context)
                .load(url)
                .thumbnail(0.1f)
                .transform(CenterCrop(),RoundedCorners(16))
                .into(binding.photoImageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewPhotoViewHolder =
        ReviewPhotoViewHolder(
            ItemReviewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ReviewPhotoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}