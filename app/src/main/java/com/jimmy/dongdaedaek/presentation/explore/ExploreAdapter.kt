package com.jimmy.dongdaedaek.presentation.explore

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip
import com.jimmy.dongdaedaek.R
import com.jimmy.dongdaedaek.databinding.ItemStoreBinding
import com.jimmy.dongdaedaek.domain.model.Store
import com.jimmy.dongdaedaek.extension.toGone
import com.jimmy.dongdaedaek.extension.toVisible

class ExploreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<Store> = listOf()
    var onClickListener: ((Store) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "holder create...")
        return StoreViewHolder(
            ItemStoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "binding holder...$position")
        (holder as StoreViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(stores: List<Store>) {
        data = stores
    }

    inner class StoreViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                data[adapterPosition].let {
                    onClickListener?.invoke(it)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Store) {
            binding.storeNameTextView.text = item.title ?: "이름 없음"
            binding.ratingTextView.text = item.rating?.take(4) ?: "?"
            item.recentPhoto?.let {
                binding.storeImageView.toVisible()
                Glide.with(binding.root)
                    .load(item.recentPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.storeImageView)
            } ?: run {
                binding.storeImageView.toGone()
            }
            binding.storeCategoryChipGroup.removeAllViews()
            item.category?.forEach {
                binding.storeCategoryChipGroup.addView(Chip(binding.root.context).apply {
                    text = it
                    isClickable = false
                    rippleColor = null
                    setTextAppearanceResource(R.style.ChipStyleNew)
                })
            }
        }
    }

    companion object {
        private const val TAG = "ExploreAdapter"
    }
}