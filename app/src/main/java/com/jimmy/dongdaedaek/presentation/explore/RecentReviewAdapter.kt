package com.jimmy.dongdaedaek.presentation.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.jimmy.dongdaedaek.databinding.ItemRecentStoreWPhotoBinding
import com.jimmy.dongdaedaek.databinding.ItemRecentStoreWoPhotoBinding
import com.jimmy.dongdaedaek.domain.model.Review

class RecentReviewAdapter(
    val data: List<Review>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onClickListener: ((Review) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            REVIEW_WITH_PHOTO -> {
                RecentStorePhotoViewHolder(
                    ItemRecentStoreWPhotoBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            else -> {
                RecentStoreNoPhotoViewHolder(
                    ItemRecentStoreWoPhotoBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentStorePhotoViewHolder -> {
                holder.bind(data[position % data.size])
            }
            is RecentStoreNoPhotoViewHolder -> {
                holder.bind(data[position % data.size])
            }
        }
    }

    override fun getItemCount(): Int =
        if (data.isEmpty()) {
            0
        } else {
            Int.MAX_VALUE
        }

    override fun getItemViewType(position: Int): Int =
        if (data[position % data.size].photos?.isNotEmpty() == true) {
            REVIEW_WITH_PHOTO
        } else {
            REVIEW_WITHOUT_PHOTO
        }


    inner class RecentStorePhotoViewHolder(val binding: ItemRecentStoreWPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            binding.recentStoreNameTextView.text = item.reviewText
            binding.ratingTextView.text = item.rating.toString()
            if (item.photos?.isNotEmpty() == true) {
                Glide.with(binding.root.context)
                    .load(item.photos?.first())
                    .transform(CenterCrop())
                    .thumbnail(0.1F)
                    .into(binding.recentReviewPhotoImageView)
            }
            onClickListener?.let {
                binding.root.setOnClickListener {
                    data[adapterPosition % data.size].let {
                        onClickListener!!.invoke(it)
                    }
                }
            }
        }
    }

    inner class RecentStoreNoPhotoViewHolder(val binding: ItemRecentStoreWoPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            binding.recentStoreNameTextView.text = item.reviewText
            binding.ratingTextView.text = item.rating.toString()
            binding.ratingBar.rating = item.rating!!
            onClickListener?.let {
                binding.root.setOnClickListener {
                    data[adapterPosition % data.size].let {
                        onClickListener!!.invoke(it)
                    }
                }
            }
        }
    }

    companion object {
        const val REVIEW_WITH_PHOTO = 0
        const val REVIEW_WITHOUT_PHOTO = 1
    }
}