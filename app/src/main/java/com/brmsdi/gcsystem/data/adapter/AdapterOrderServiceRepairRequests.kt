package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.OrderServiceDetailRepairRequestsViewHolder
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowOrderServiceRepairRequestBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterOrderServiceRepairRequests(private val locale: Locale, private val context: Context) : RecyclerView.Adapter<OrderServiceDetailRepairRequestsViewHolder>() {
    private var list : MutableList<RepairRequest> = mutableListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderServiceDetailRepairRequestsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RowOrderServiceRepairRequestBinding.inflate(layoutInflater, parent, false)
        return OrderServiceDetailRepairRequestsViewHolder(context, locale, itemBinding)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(
        holder: OrderServiceDetailRepairRequestsViewHolder,
        position: Int
    ) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<RepairRequest>) {
        list = newList
        notifyDataSetChanged()
    }
}