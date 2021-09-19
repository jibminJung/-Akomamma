package com.jimmy.dongdaedaek.presentation.wishstorelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.dongdaedaek.databinding.ItemWishStoreBinding
import com.jimmy.dongdaedaek.domain.model.Store

class WishStoreListAdapter: RecyclerView.Adapter<WishStoreListAdapter.WishStoreViewHolder>() {
    var data : List<Store> = listOf()
    var clickListener : ((Store) -> Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishStoreViewHolder
    = WishStoreViewHolder(ItemWishStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: WishStoreViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class WishStoreViewHolder(val binding:ItemWishStoreBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                clickListener?.invoke(data[adapterPosition])
            }
        }

        fun bind(item : Store){
            binding.storeTitleTextView.text = item.title
            var cate : String = ""
            item.category?.forEach {
                cate += it + ", "
            }
            cate.trimEnd(',')
            binding.storeCategoryTextView.text = cate
        }
    }

    fun addData(data:List<Store>){
        this.data = data
        notifyDataSetChanged()
    }
}