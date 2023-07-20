package com.brmsdi.gcsystem.data.adapter.viewHolder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.AdapterItem
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.RowOrderServiceRepairRequestBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class OrderServiceDetailRepairRequestsViewHolder(
    private val context: Context,
    private val locale: Locale,
    private val row: RowOrderServiceRepairRequestBinding
) :
    RecyclerView.ViewHolder(row.root) {

    fun bindData(repairRequest: RepairRequest) {
        row.textId.text = repairRequest.id.toString()
        row.textDescription.text = repairRequest.problemDescription
        row.textTypeProblem.text = repairRequest.typeProblem.name
        row.textCondominium.text = repairRequest.condominium.name
        row.textApartment.text = repairRequest.apartmentNumber
        row.textLesseeName.text = repairRequest.lessee.name

        repairRequest.items?.let { items ->
            val adapter = AdapterItem(locale)
            row.recyclerItemsId.layoutManager = LinearLayoutManager(context)
            row.recyclerItemsId.adapter = adapter
            adapter.updateAll(items.toMutableList())
            row.textItemNone.visibility = View.GONE
            row.recyclerItemsId.visibility= View.VISIBLE
            row.buttonRemoveItem.visibility = View.VISIBLE
        }

    }
}