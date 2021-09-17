package com.jimmy.dongdaedaek.presentation.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.dongdaedaek.databinding.ItemRecentStoreBinding
import com.jimmy.dongdaedaek.domain.model.Store

class RecentStoreAdapter:RecyclerView.Adapter<RecentStoreAdapter.RecentStoreViewHolder>() {

    var data:List<Store> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentStoreViewHolder {
        return RecentStoreViewHolder(ItemRecentStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecentStoreViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(stores:List<Store>){
        data = stores
    }
    inner class RecentStoreViewHolder(val binding:ItemRecentStoreBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Store){
            binding.recentStoreNameTextView.text=item.title
            binding.ratingTextView.text = item.rating

        }
    }
}