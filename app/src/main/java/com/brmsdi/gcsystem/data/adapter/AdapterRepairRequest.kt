package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.RepairRequestViewHolder
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowRepairRequestBinding

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterRepairRequest(private val listener : ItemRecyclerListener<RepairRequest>) : RecyclerView.Adapter<RepairRequestViewHolder>() {
    private var list : MutableList<RepairRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowRepairRequestBinding.inflate(inflater, parent, false)
        return RepairRequestViewHolder(itemBinding, listener)
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: RepairRequestViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<RepairRequest>) {
        list = newList
        notifyDataSetChanged()
    }
}