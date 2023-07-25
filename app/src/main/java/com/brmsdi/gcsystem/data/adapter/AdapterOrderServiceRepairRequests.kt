package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.OrderServiceDetailRepairRequestsViewHolder
import com.brmsdi.gcsystem.data.listeners.AddItemListener
import com.brmsdi.gcsystem.data.listeners.RemoveItemListener
import com.brmsdi.gcsystem.data.model.Item
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowOrderServiceRepairRequestBinding
import com.brmsdi.gcsystem.ui.utils.NumberUtils

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterOrderServiceRepairRequests(
    private val context: Context,
    private val orderService: OrderService,
    private val addItemListener: AddItemListener,
    private val removeItemListener: RemoveItemListener
) : RecyclerView.Adapter<OrderServiceDetailRepairRequestsViewHolder>() {
    private var list: MutableList<RepairRequest> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderServiceDetailRepairRequestsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RowOrderServiceRepairRequestBinding.inflate(layoutInflater, parent, false)
        return OrderServiceDetailRepairRequestsViewHolder(
            context,
            itemBinding,
            addItemListener
        )
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(
        holder: OrderServiceDetailRepairRequestsViewHolder,
        position: Int
    ) {
        val adapter = AdapterItem(context, NumberUtils.getSystemLocale(context), orderService, removeItemListener)
        holder.bindData(list[position], adapter)
    }

    fun getList() : MutableList<RepairRequest> = list

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<RepairRequest>) {
        list = newList
        notifyDataSetChanged()
    }

    fun addItem(repairRequest: RepairRequest, item: Item, notify: Boolean = true) : Boolean {
        list.forEach {
            if (it.id == repairRequest.id) {
                if (it.items == null) it.items = mutableListOf()
                it.items!!.add(item)
                if (notify) notifyItemChanged(list.indexOf(repairRequest))
                return true
            }
        }
        return false
    }

    fun removeItem(repairRequest: RepairRequest, item: Item, notify: Boolean = true) : Boolean {
        for (index in list.count() - 1  downTo 0) {
            if (list[index].id == repairRequest.id) {
                val position = list[index].items?.indexOf(item)
                if (position != null) {
                    list[index].items!!.removeAt(position)
                    if (notify) notifyItemChanged(index)
                    return true
                }
            }
        }
        return false
    }
}