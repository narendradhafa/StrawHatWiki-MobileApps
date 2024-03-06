package com.example.submissionakhir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionakhir.databinding.ItemRowStrawhatBinding

class ListStrawhatAdapter(private val listStrawhat: ArrayList<Strawhat>) : RecyclerView.Adapter<ListStrawhatAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowStrawhatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listStrawhat.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, bindPosition: Int) {
        val (name, position, description, moreDescription, bounty, photo) = listStrawhat[bindPosition]
        Glide.with(holder.itemView.context)
            .load(photo) // URL Gambar
            .into(holder.binding.imgItemPhoto) // imageView mana yang akan diterapkan
        holder.binding.tvItemName.text = name
        holder.binding.tvItemDescription.text = description

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listStrawhat[holder.adapterPosition]) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowStrawhatBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Strawhat)
    }
}