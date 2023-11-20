package com.brmsdi.gcsystem.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.brmsdi.gcsystem.data.adapter.viewHolder.DebtViewHolder
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.brmsdi.gcsystem.data.dto.PagingDebtModel
import com.brmsdi.gcsystem.databinding.RowDebtBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class PagingDataDebtsAdapter(private val locale: Locale) :
    PagingDataAdapter<PagingDebtModel, DebtViewHolder>(DEBT_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowDebtBinding.inflate(layoutInflater, parent, false)
        return DebtViewHolder(binding, locale)
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bindData(tile.debt)
        }
    }

    companion object {
        private val DEBT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<PagingDebtModel>() {
            override fun areItemsTheSame(
                oldItem: PagingDebtModel,
                newItem: PagingDebtModel
            ): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(
                oldItem: PagingDebtModel,
                newItem: PagingDebtModel
            ): Boolean =
                oldItem == newItem
        }
    }
}

