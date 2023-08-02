package com.brmsdi.gcsystem.data.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerListenerListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowRepairRequestBinding

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class RepairRequestViewHolder(
    private val rowRepairRequestBinding: RowRepairRequestBinding,
    private val listener: ItemRecyclerListenerListener<RepairRequest>
) :
    RecyclerView.ViewHolder(rowRepairRequestBinding.root) {
    fun bindData(repairRequest: RepairRequest) {
        rowRepairRequestBinding.apply {
            textId.text = repairRequest.id.toString()
            textCondominiumName.text = repairRequest.condominium.name
            textTypeProblem.text = repairRequest.typeProblem.name
            textStatus.text = repairRequest.status?.name
        }

        rowRepairRequestBinding.root.setOnClickListener {
            listener.onClick(repairRequest)
        }

        rowRepairRequestBinding.root.setOnLongClickListener {
            listener.onLongClick(repairRequest)
        }
    }
}