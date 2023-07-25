package com.brmsdi.gcsystem.data.adapter.viewHolder

import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.adapter.AdapterItem
import com.brmsdi.gcsystem.data.listeners.AddItemListener
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowOrderServiceRepairRequestBinding

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class OrderServiceDetailRepairRequestsViewHolder(
    private val context: Context,
    private val row: RowOrderServiceRepairRequestBinding,
    private val addItemListener: AddItemListener
) :
    RecyclerView.ViewHolder(row.root) {

    fun bindData(repairRequest: RepairRequest, adapterItem: AdapterItem) {
        row.textId.text = repairRequest.id.toString()
        row.textDescription.text = repairRequest.problemDescription
        row.textTypeProblem.text = repairRequest.typeProblem.name
        row.textCondominium.text = repairRequest.condominium.name
        row.textApartment.text = repairRequest.apartmentNumber
        row.textLesseeName.text = repairRequest.lessee.name

        repairRequest.items?.let { items ->
            row.recyclerItemsId.layoutManager = LinearLayoutManager(context)
            row.recyclerItemsId.adapter = adapterItem
            adapterItem.updateAll(items.toMutableList())
            row.textItemNone.visibility = GONE
            row.recyclerItemsId.visibility= VISIBLE
        }

        if (repairRequest.status.name == context.getString(R.string.status_concluded)) {
            row.buttonAddItem.visibility = GONE
        }

        row.buttonAddItem.setOnClickListener { addItemListener.addItem(repairRequest) }
    }
}