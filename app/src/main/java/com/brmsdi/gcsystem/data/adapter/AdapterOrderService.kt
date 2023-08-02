package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.OrderServiceViewHolder
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.RowOrderServiceBinding

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterOrderService(private val listener: ItemRecyclerClickListener<OrderService>) :
    RecyclerView.Adapter<OrderServiceViewHolder>() {
    private var list: MutableList<OrderService> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderServiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowOrderServiceBinding.inflate(inflater, parent, false)
        return OrderServiceViewHolder(itemBinding, listener)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: OrderServiceViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<OrderService>) {
        list = newList
        notifyDataSetChanged()
    }
}