package com.example.galery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.galery.R
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.databinding.ItemPhotoBinding

class PhotoAdapter(private val callback: (OnePhotoItem)->Unit, private val clickLike: (OnePhotoItem)-> Unit) : PagingDataAdapter<OnePhotoItem, PhotoAdapter.PhotoHolder>(
    PhotoCallback()
) {

    inner class PhotoHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnePhotoItem) {
            binding.onePhoto = item
            binding.root.setOnClickListener{
                callback(item)
            }
            binding.grayLike.setOnClickListener{
                item.isLiked = !item.isLiked
                binding.onePhoto = item
                clickLike(item)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding: ItemPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_photo,
            parent, false
        )
        return PhotoHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bind(item)
    }
}

class PhotoCallback : DiffUtil.ItemCallback<OnePhotoItem>() {
    override fun areItemsTheSame(oldItem: OnePhotoItem, newItem: OnePhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OnePhotoItem, newItem: OnePhotoItem): Boolean {
        return oldItem == newItem
    }
}