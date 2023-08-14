package com.brmsdi.gcsystem.ui.activity.newRepairRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.CondominiumSpinnerDTO
import com.brmsdi.gcsystem.data.dto.SpinnerDTO
import com.brmsdi.gcsystem.data.dto.TypeProblemSpinnerDTO
import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.model.TypeProblem
import com.brmsdi.gcsystem.databinding.ActivityNewRepairRequestBinding
import com.brmsdi.gcsystem.ui.utils.Mock
import com.brmsdi.gcsystem.ui.utils.Mock.Companion.lesseeList
import com.brmsdi.gcsystem.ui.utils.Mock.Companion.statusList
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import java.util.Date

class NewRepairRequestActivity : AppCompatActivity(), OnClickListener {
    private lateinit var viewModel: NewRepairRequestViewModel
    private lateinit var binding: ActivityNewRepairRequestBinding
    private lateinit var repairRequest: RepairRequest
    private var condominiumList = mutableListOf<Condominium>()
    private var typeProblemList = mutableListOf<TypeProblem>()
    private lateinit var spinnerAdapterCondominium: ArrayAdapter<SpinnerDTO<Condominium>>
    private lateinit var spinnerAdapterTypeProblem: ArrayAdapter<SpinnerDTO<TypeProblem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewRepairRequestBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NewRepairRequestViewModel::class.java]
        spinnerAdapterCondominium = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<SpinnerDTO<Condominium>>()
        )
        spinnerAdapterTypeProblem = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<SpinnerDTO<TypeProblem>>()
        )
        binding.spinnerCondominium.adapter = spinnerAdapterCondominium
        binding.spinnerTypeProblem.adapter = spinnerAdapterTypeProblem
        loadData()
        loadSpinner()
        addAction()
        setContentView(binding.root)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonCancel.id -> back()
            binding.buttonSave.id -> {
                repairRequest = getFields()
                if (!fieldsIsNotEmpty(
                        repairRequest.condominium.name,
                        repairRequest.apartmentNumber,
                        repairRequest.problemDescription,
                        repairRequest.typeProblem.name
                    )
                ) {
                    displayMessage(this, getString(R.string.fields_empty))
                    return
                }
                save(repairRequest)
            }
        }
    }

    private fun save(repairRequest: RepairRequest) {
        //viewModel.save(repairRequest)
        displayMessage(this, getString(R.string.new_repair_request_success))
    }

    private fun addAction() {
        binding.buttonCancel.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    private fun back() {
        finish()
    }

    private fun getFields(): RepairRequest {
        val typeProblemSpinnerDTO = binding.spinnerTypeProblem.selectedItem as TypeProblemSpinnerDTO
        val condominiumSpinnerDTO = binding.spinnerCondominium.selectedItem as CondominiumSpinnerDTO
        return RepairRequest(
            problemDescription = binding.editDescriptionProblem.text.toString(),
            date = Date(),
            typeProblem = typeProblemSpinnerDTO.getModel(),
            lessee = lesseeList()[0],
            condominium = condominiumSpinnerDTO.getModel(),
            status = statusList()[0],
            items = null,
            apartmentNumber = binding.editApartment.text.toString(),
            orderService = null
        )
    }

    private fun loadData() {
        condominiumList = Mock.condominiumList()
        typeProblemList = Mock.typeProblemList()
    }

    private fun loadSpinner() {
        spinnerAdapterCondominium.addAll(condominiumList.map { CondominiumSpinnerDTO(it) } )
        spinnerAdapterTypeProblem.addAll(typeProblemList.map { TypeProblemSpinnerDTO(it) })
    }
}