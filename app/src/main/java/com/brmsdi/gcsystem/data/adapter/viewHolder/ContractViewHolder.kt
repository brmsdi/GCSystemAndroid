package com.brmsdi.gcsystem.data.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.RowContractBinding
import com.brmsdi.gcsystem.ui.utils.DateUtils.Companion.dateFormattedToView
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.formatCoin
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class ContractViewHolder(
    private val row: RowContractBinding,
    private val locale: Locale,
    private val listener: ItemRecyclerClickListener<Contract>
) :
    RecyclerView.ViewHolder(row.root) {

    fun bindData(contract: Contract) {
        row.textId.text = contract.id.toString()
        row.textDateContract.text = dateFormattedToView(contract.contractDate)
        row.textDateExpirationContract.text = dateFormattedToView(contract.contractExpirationDate)
        row.textValue.text = formatCoin(locale, contract.contractValue)
        row.textStatus.text = contract.status.name
        row.textContractor.text = contract.lessee.name
        row.root.setOnClickListener { listener.onClick(contract) }
    }
}