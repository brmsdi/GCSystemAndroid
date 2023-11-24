package com.brmsdi.gcsystem.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.brmsdi.gcsystem.data.adapter.viewHolder.ContractViewHolder
import com.brmsdi.gcsystem.data.dto.PagingContractModel
import com.brmsdi.gcsystem.data.listeners.ItemRecyclerClickListener
import com.brmsdi.gcsystem.data.model.Contract
import com.brmsdi.gcsystem.databinding.RowContractBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PagingDataContractsAdapter(
    private val locale: Locale,
    private val listener: ItemRecyclerClickListener<Contract>
) : PagingDataAdapter<PagingContractModel, ContractViewHolder>(CONTRACT_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowContractBinding.inflate(layoutInflater, parent, false)
        return ContractViewHolder(binding, locale, listener)
    }

    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bindData(tile.contract)
        }
    }

    companion object {
        private val CONTRACT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingContractModel>() {
            override fun areItemsTheSame(
                oldItem: PagingContractModel,
                newItem: PagingContractModel
            ): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(
                oldItem: PagingContractModel,
                newItem: PagingContractModel
            ): Boolean =
                oldItem == newItem
        }
    }
}