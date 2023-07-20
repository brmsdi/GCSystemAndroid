package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.ItemViewHolder
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.databinding.RowItemBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterItem(private val locale: Locale) : RecyclerView.Adapter<ItemViewHolder>() {
    private var list : MutableList<Item> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RowItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(locale, itemBinding)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<Item>) {
        list = newList
        notifyDataSetChanged()
    }
}