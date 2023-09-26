package com.brmsdi.gcsystem.ui.activity.newRepairRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.dto.CondominiumSpinnerDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.dto.SpinnerDTO
import com.brmsdi.gcsystem.data.dto.TypeProblemSpinnerDTO
import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.TypeProblem
import com.brmsdi.gcsystem.databinding.ActivityNewRepairRequestBinding
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewRepairRequestActivity : AppCompatActivity(), OnClickListener, ProgressBarOnApp {
    private val viewModel by viewModel<NewRepairRequestViewModel>()
    private lateinit var binding: ActivityNewRepairRequestBinding
    private lateinit var repairRequest: RepairRequestRegisterDTO
    private lateinit var spinnerAdapterCondominium: ArrayAdapter<SpinnerDTO<Condominium>>
    private lateinit var spinnerAdapterTypeProblem: ArrayAdapter<SpinnerDTO<TypeProblem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewRepairRequestBinding.inflate(layoutInflater)
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
        observe()
        loadData()
        addAction()
        setContentView(binding.root)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonCancel.id -> finish()
            binding.buttonSave.id -> {
                repairRequest = getFields()
                if (!fieldsIsNotEmpty(
                        repairRequest.problemDescription,
                        repairRequest.apartmentNumber,
                    )
                ) {
                    displayMessage(this, getString(R.string.fields_empty))
                    return
                }
                save(repairRequest)
            }
        }
    }

    private fun observe() {
        viewModel.saved.observe(this) {
            if (it.status()) {
                displayMessage(this, getString(R.string.new_repair_request_success))
                clearScreen()
                showOrHideView(binding.progressSave, false)
                showOrHideView(binding.buttonSave, true)
            }
        }

        viewModel.screenData.observe(this) {
            loadSpinner(it)
            showOrHideView(binding.progressSave, false)
            showOrHideView(binding.buttonSave, true)
        }

        viewModel.error.observe(this) {
            displayMessage(this, it.message())
            showOrHideView(binding.progressSave, false)
            showOrHideView(binding.buttonSave, true)
        }
    }

    private fun save(repairRequest: RepairRequestRegisterDTO) {
        viewModel.save(repairRequest)
        showOrHideView(binding.progressSave, true)
        showOrHideView(binding.buttonSave, false)
    }

    private fun addAction() {
        binding.buttonCancel.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    private fun getFields(): RepairRequestRegisterDTO {
        val typeProblemSpinnerDTO = binding.spinnerTypeProblem.selectedItem as TypeProblemSpinnerDTO
        val condominiumSpinnerDTO = binding.spinnerCondominium.selectedItem as CondominiumSpinnerDTO
        return RepairRequestRegisterDTO(
            problemDescription = binding.editDescriptionProblem.text.toString(),
            condominiumID = condominiumSpinnerDTO.getModel().id,
            apartmentNumber = binding.editApartment.text.toString(),
            typeProblemID = typeProblemSpinnerDTO.getModel().id
        )
    }

    private fun loadData() {
        viewModel.loadDataNewRepairRequest()
    }

    private fun loadSpinner(data: RepairRequestRegisterDataDTO) {
        spinnerAdapterCondominium.addAll(data.condominiums.map { CondominiumSpinnerDTO(it) })
        spinnerAdapterTypeProblem.addAll(data.typeProblems.map { TypeProblemSpinnerDTO(it) })
    }

    private fun clearScreen() {
        binding.editApartment.setText("")
        binding.editDescriptionProblem.setText("")
    }
}