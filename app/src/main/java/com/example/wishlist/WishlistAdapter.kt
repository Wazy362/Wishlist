package com.example.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat

class WishlistAdapter(
    private val items: MutableList<WishlistItem>,
    private val onUrlClick: (String) -> Unit
) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvItemName)
        val tvPrice: TextView = view.findViewById(R.id.tvItemPrice)
        val tvUrl: TextView = view.findViewById(R.id.tvItemUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_entry, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = if (item.store.isNotBlank()) "${item.name} — ${item.store}" else item.name
        holder.tvPrice.text = NumberFormat.getCurrencyInstance().format(item.price)
        holder.tvUrl.text = item.url

        holder.tvUrl.setOnClickListener {
            if (item.url.isNotBlank()) onUrlClick(item.url)
        }
    }

    fun addItem(item: WishlistItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun getAllItems(): List<WishlistItem> = items.toList()
}
