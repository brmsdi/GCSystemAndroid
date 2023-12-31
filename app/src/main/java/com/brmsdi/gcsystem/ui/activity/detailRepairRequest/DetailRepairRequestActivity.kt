package com.brmsdi.gcsystem.ui.activity.detailRepairRequest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.brmsdi.gcsystem.data.constants.Constant.REPAIR.REPAIR_REQUEST_DATA
import com.brmsdi.gcsystem.data.model.Employee
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.databinding.ActivityDetailRepairRequestBinding
import com.brmsdi.gcsystem.ui.utils.LoadData
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp

class DetailRepairRequestActivity : AppCompatActivity(), LoadData, ProgressBarOnApp {
    private lateinit var binding: ActivityDetailRepairRequestBinding
    private var repairRequest: RepairRequest? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRepairRequestBinding.inflate(layoutInflater)
        repairRequest = load(intent.getBundleExtra(REPAIR_REQUEST_DATA), REPAIR_REQUEST_DATA, RepairRequest::class.java)
        loadData(repairRequest)
        addAction()
        setContentView(binding.root)
    }

    private fun addAction() {
        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun loadData(repairRequest: RepairRequest?) {
        repairRequest?.let {
            binding.editId.setText(it.id.toString())
            binding.editCondominium.setText(it.condominium.name)
            binding.editApartment.setText(it.apartmentNumber)
            binding.editDescriptionProblem.setText(it.problemDescription)
            binding.editTypeProblem.setText(it.typeProblem.name)
            it.orderService?.let { orderService ->
                binding.editOrderService.setText(orderService.id.toString())
                loadRecycler(orderService.employees)
                showOrHideView(binding.textCollaboratorsNone, false)
                showOrHideView(binding.spinnerCollaborators, true)
            }
        }
    }

    private fun loadRecycler(employees: Set<Employee>) {
        val list = mutableListOf<Employee>()
        employees.forEach {
            list.add(it)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.spinnerCollaborators.adapter = adapter
    }
}