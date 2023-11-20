package com.brmsdi.gcsystem.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.brmsdi.gcsystem.data.adapter.viewHolder.RepairRequestViewHolder
import com.brmsdi.gcsystem.data.dto.PagingRepairRequestModel
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerListenerListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowRepairRequestBinding

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PagingDataRepairRequest(private val listener: ItemRecyclerListenerListener<RepairRequest>) :
    PagingDataAdapter<PagingRepairRequestModel, RepairRequestViewHolder>(RR_DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowRepairRequestBinding.inflate(inflater, parent, false)
        return RepairRequestViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: RepairRequestViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bindData(tile.repairRequest)
        }
    }

    companion object {
        private val RR_DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingRepairRequestModel>() {
            override fun areItemsTheSame(oldItem: PagingRepairRequestModel, newItem: PagingRepairRequestModel): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: PagingRepairRequestModel, newItem: PagingRepairRequestModel): Boolean =
                oldItem == newItem
        }
    }
}