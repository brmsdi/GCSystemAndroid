package com.brmsdi.gcsystem.data.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brmsdi.gcsystem.data.adapter.viewHolder.DebtViewHolder
import com.brmsdi.gcsystem.data.model.Debt
import com.brmsdi.gcsystem.data.model.Lessee
import com.brmsdi.gcsystem.databinding.RowDebtBinding
import java.util.Locale

/**
 *
 * @author Wisley Bruno Marques França
 * @since 1
 */
class AdapterDebts(private val locale: Locale) : RecyclerView.Adapter<DebtViewHolder>() {
    private var list : MutableList<Debt> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowDebtBinding.inflate(layoutInflater, parent, false)
        return DebtViewHolder(binding, locale)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(newList: MutableList<Debt>) {
        list.clear()
        list = newList
        notifyDataSetChanged()
    }
}