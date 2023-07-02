package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.RepairRequestViewHolder
import com.brmsdi.gcsystem.data.listeners.RepairRequestListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowRepairRequestBinding


/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterRepairRequest : RecyclerView.Adapter<RepairRequestViewHolder>() {

    private var list : List<RepairRequest> = arrayListOf()
    private lateinit var listener: RepairRequestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowRepairRequestBinding.inflate(inflater, parent, false)
        return RepairRequestViewHolder(itemBinding, listener)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: RepairRequestViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRepairRequestAll(newList: List<RepairRequest>) {
        list = newList
        notifyDataSetChanged()
    }

    fun addListener(listener: RepairRequestListener) {
        this.listener = listener
    }
}