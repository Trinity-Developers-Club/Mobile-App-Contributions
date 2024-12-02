package com.example.pagerv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.pagerv.paging.DataItem
import com.example.pagerv.databinding.ItemDataBinding

class PagerDataAdapter :
    PagingDataAdapter<DataItem, PagerDataAdapter.CatViewHolder>(diffCallback = differCallback) {

    inner class CatViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: DataItem) {
            Glide.with(binding.root.context)
                .load(cat.imageUrl)
                .placeholder(R.drawable.ic_baseline_pets_24)
                .error(R.drawable.ic_baseline_pets_24)
                .centerCrop()
                .into(binding.catpic)

            binding.apply {
                breedName.text = cat.title
                countryName.text = cat.subTitle

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(cat)
                    }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            ItemDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    private var onItemClickListener: ((DataItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (DataItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
