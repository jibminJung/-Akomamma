package com.jimmy.dongdaedaek.presentation.explore

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.dongdaedaek.databinding.ItemStoreBinding
import com.jimmy.dongdaedaek.domain.model.Store

class ExploreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data:List<Store> = listOf()
    var onClickListener:((Store)->Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("debug","holder create...")
        return StoreViewHolder(ItemStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("debug","binding holder...$position")
        (holder as StoreViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun addItem(stores:List<Store>){

        data = stores
    }

    inner class StoreViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                data[adapterPosition].let{
                    onClickListener?.invoke(it)
                }
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(item: Store) {
            binding.storeNameTextView.text=item.title?:"이름 없음"
            binding.ratingTextView.text = item.rating?:"?"
        }
    }

}