package com.brmsdi.gcsystem.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.brmsdi.gcsystem.data.adapter.viewHolder.OrderServiceViewHolder
import com.brmsdi.gcsystem.data.dto.PagingOrderServiceModel
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.RowOrderServiceBinding

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PagingDataOrderService(private val listener: ItemRecyclerClickListener<OrderService>) :
    PagingDataAdapter<PagingOrderServiceModel, OrderServiceViewHolder>(OS_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderServiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowOrderServiceBinding.inflate(layoutInflater, parent, false)
        return OrderServiceViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: OrderServiceViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bindData(tile.orderService)
        }
    }

    companion object {
        private val OS_DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingOrderServiceModel>() {
            override fun areItemsTheSame(
                oldItem: PagingOrderServiceModel,
                newItem: PagingOrderServiceModel
            ): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(
                oldItem: PagingOrderServiceModel,
                newItem: PagingOrderServiceModel
            ): Boolean =
                oldItem == newItem
        }
    }
}