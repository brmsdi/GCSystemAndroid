package com.brmsdi.gcsystem.ui.activity.detailOrderService

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.brmsdi.gcsystem.data.adapter.AdapterOrderServiceRepairRequests
import com.brmsdi.gcsystem.data.dto.EmployeeSpinnerDTO
import com.brmsdi.gcsystem.data.model.OrderService
import com.brmsdi.gcsystem.databinding.ActivityDetailOrderServiceBinding
import com.brmsdi.gcsystem.ui.utils.DateUtils.Companion.dateFormattedToView
import com.brmsdi.gcsystem.ui.utils.NumberUtils.Companion.getSystemLocale
import com.brmsdi.gcsystem.ui.utils.OrderServiceData

class DetailOrderServiceActivity : AppCompatActivity(), OrderServiceData {
    private lateinit var binding: ActivityDetailOrderServiceBinding
    private var orderService: OrderService? = null
    private lateinit var spinnerAdapterCollaborators : ArrayAdapter<EmployeeSpinnerDTO>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderServiceBinding.inflate(layoutInflater)
        orderService = loadIOrderServiceData(intent.extras)
        orderService?.let {
            binding.editId.setText(it.id.toString())
            binding.editCreatedDate.setText(dateFormattedToView(it.generationDate))
            it.reservedDate?.let { reservedDate ->
                binding.editReservedDate.setText(dateFormattedToView(reservedDate))
            }
            binding.editStatus.setText(it.status.name)

            if (it.employees.isNotEmpty()) {
                val employees = it.employees.map { employee -> EmployeeSpinnerDTO(employee) }.toMutableList()
                spinnerAdapterCollaborators = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    mutableListOf<EmployeeSpinnerDTO>()
                )
                binding.spinnerCollaborators.adapter = spinnerAdapterCollaborators
                spinnerAdapterCollaborators.addAll(employees)
                binding.spinnerCollaborators.visibility = View.VISIBLE
                binding.textCollaboratorsNone.visibility = View.GONE
            }
            val repair = it.repairRequests
            val adapterRepair = AdapterOrderServiceRepairRequests(getSystemLocale(this),this)
            binding.recyclerOrderServiceRepairRequests.layoutManager =
                LinearLayoutManager(this)

            adapterRepair.updateAll(repair.toMutableList())
            binding.recyclerOrderServiceRepairRequests.adapter = adapterRepair
        }

        setContentView(binding.root)
    }
}