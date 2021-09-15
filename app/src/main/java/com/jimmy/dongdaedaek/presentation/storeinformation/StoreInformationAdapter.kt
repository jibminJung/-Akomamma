package com.jimmy.dongdaedaek.presentation.storeinformation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.dongdaedaek.databinding.ItemReviewBinding
import com.jimmy.dongdaedaek.databinding.ItemReviewFormBinding
import com.jimmy.dongdaedaek.domain.model.Review
import com.jimmy.dongdaedaek.extension.toDecimalFormatString

class StoreInformationAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val data = ArrayList<DataItem>()

    var onSubmitButtonClickListener:((String,Float)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            ITEM_VIEW_TYPE_REVIEW->{
                ReviewItemViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else->{
                ReviewFormViewHolder(ItemReviewFormBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ReviewItemViewHolder ->{
                holder.bind(data[position].value as Review)
            }
        }
    }

    override fun getItemCount(): Int =data.size

    override fun getItemViewType(position: Int): Int =
        when(data[position].value){
            is Review ->{
                ITEM_VIEW_TYPE_REVIEW
            }
            else-> ITEM_VIEW_TYPE_FORM
        }

    inner class ReviewItemViewHolder(val binding:ItemReviewBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:Review){
            binding.userIdTextView.text = "${item.userId?.take(2)}***"
            binding.contentTextView.text = item.reviewText
            binding.dateTextView.text = item.createdAt.toString()
            binding.ratingTextView.text = item.rating?.toDecimalFormatString("0.0")
        }
    }
    inner class ReviewFormViewHolder(val binding:ItemReviewFormBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.submitButton.setOnClickListener {
                onSubmitButtonClickListener?.invoke(
                    binding.reviewTextEditText.text.toString(),
                    binding.ratingBar.rating
                )
                binding.reviewTextEditText.text.clear()

            }

            binding.reviewTextEditText.addTextChangedListener{ editable->
                binding.submitButton.isEnabled = (editable?.length?:0>5)
            }
            binding.ratingBar.setOnRatingBarChangeListener{_, rating, _ ->
                binding.ratingScoreTextView.text = rating.toString()
            }
        }
    }
    fun addReviewData(reviews:List<Review>){
        data.add(DataItem(1))
        reviews.forEach {
            data.add(DataItem(it))
        }
    }
    fun clearReviewData(){
        data.clear()
    }

    data class DataItem(val value:Any)

    companion object{
        const val ITEM_VIEW_TYPE_FORM = 0
        const val ITEM_VIEW_TYPE_REVIEW = 1
    }
}