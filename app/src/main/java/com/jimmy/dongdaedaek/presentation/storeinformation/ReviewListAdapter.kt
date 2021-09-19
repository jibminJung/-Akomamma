package com.jimmy.dongdaedaek.presentation.storeinformation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.dongdaedaek.databinding.ItemReviewBinding
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.extension.toDecimalFormatString
import java.text.SimpleDateFormat

class ReviewListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = ArrayList<DataItem>()
    var imageClickListener: ((List<String>,Int) -> Unit)? = null
    inner class ReviewItemViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            item.photos?.let{
                binding.photoRecyclerView.adapter = ReviewPhotoAdapter().apply {
                    data.addAll(item.photos)
                    clickListener = imageClickListener
                }
            }
            binding.userIdTextView.text = "${item.userId?.take(2)}***"
            binding.contentTextView.text = item.reviewText
            binding.dateTextView.text = DATE_FORMAT.format(item.createdAt!!)
            binding.ratingTextView.text = item.rating?.toDecimalFormatString("0.0")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReviewItemViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReviewItemViewHolder -> {
                holder.bind(data[position].value as Review)
            }
        }
    }

    override fun getItemCount(): Int = data.size



    fun addReviewData(reviews: List<Review>) {
        reviews.forEach {
            data.add(DataItem(it))
        }
    }

    fun clearReviewData() {
        data.clear()
    }

    data class DataItem(val value: Any)
    companion object{
        val DATE_FORMAT = SimpleDateFormat("yy:MM:dd hh:mm")
    }


}