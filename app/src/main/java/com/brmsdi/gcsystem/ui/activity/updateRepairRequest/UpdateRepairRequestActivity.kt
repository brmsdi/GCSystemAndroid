package com.brmsdi.gcsystem.ui.activity.updateRepairRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.constants.Constant
import com.brmsdi.gcsystem.data.dto.CondominiumSpinnerDTO
import com.brmsdi.gcsystem.data.dto.RepairRequestRegisterDataDTO
import com.brmsdi.gcsystem.data.dto.SpinnerDTO
import com.brmsdi.gcsystem.data.dto.TypeProblemSpinnerDTO
import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.model.TypeProblem
import com.brmsdi.gcsystem.databinding.ActivityUpdateRepairRequestBinding
import com.brmsdi.gcsystem.ui.utils.LoadData
import com.brmsdi.gcsystem.ui.utils.ProgressBarOnApp
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateRepairRequestActivity : AppCompatActivity(), LoadData, OnClickListener,
    ProgressBarOnApp {
    private lateinit var binding: ActivityUpdateRepairRequestBinding
    private val viewModel by viewModel<UpdateRepairRequestViewModel>()
    private var repairRequest: RepairRequest? = null
    private var id = 0
    private var condominiumList = mutableListOf<Condominium>()
    private var typeProblemList = mutableListOf<TypeProblem>()
    private lateinit var spinnerAdapterCondominium: ArrayAdapter<SpinnerDTO<Condominium>>
    private lateinit var spinnerAdapterTypeProblem: ArrayAdapter<SpinnerDTO<TypeProblem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRepairRequestBinding.inflate(layoutInflater)
        id = intent.getIntExtra(Constant.REPAIR.REPAIR_REQUEST_DATA_ID, 0)
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
        loadData(id)
        addAction()
        setContentView(binding.root)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonCancel.id -> back()
            binding.buttonSave.id -> {
                getFields(repairRequest!!)
                if (!fieldsIsNotEmpty(
                        repairRequest!!.id.toString(),
                        repairRequest!!.condominium.id.toString(),
                        repairRequest!!.apartmentNumber,
                        repairRequest!!.problemDescription,
                        repairRequest!!.typeProblem.id.toString()
                    )
                ) {
                    displayMessage(this, getString(R.string.fields_empty))
                    return
                }
                save(repairRequest!!)
            }
        }
    }

    private fun observe() {
        viewModel.repairRequest.observe(this) {
            this.repairRequest = it
            fillInFields(it)
            if (!isLoading()) {
                showInputLayout()
            }
        }

        viewModel.updatedRepairRequest.observe(this) {
            this.repairRequest = it
            fillInFields(it)
            displayMessage(this, getString(R.string.updated_info_success))
        }

        viewModel.errorUpdate.observe(this) {
            if (!it.status()) {
                displayMessage(this, it.message())
            }
        }

        viewModel.screenData.observe(this) {
            fillSpinner(it)
            checkSpinners()
            if (!isLoading()) {
                showInputLayout()
            }
        }

        viewModel.errorLoadData.observe(this) {
            if (!it.status()) {
                displayMessage(this, getString(R.string.search_is_empty))
                back()
            }
        }

    }

    private fun addAction() {
        binding.buttonCancel.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    private fun back() {
        finish()
    }

    private fun save(request: RepairRequest) {
        viewModel.update(request)
    }

    private fun getFields(repairRequest: RepairRequest) {
        val condominiumSpinner = binding.spinnerCondominium.selectedItem as SpinnerDTO<*>
        val typeProblemSpinner = binding.spinnerTypeProblem.selectedItem as SpinnerDTO<*>
        repairRequest.apply {
            condominium = condominiumSpinner.getModel() as Condominium
            //  date = formatDateFromApi(Date())
            apartmentNumber = binding.editApartment.text.toString()
            problemDescription = binding.editDescriptionProblem.text.toString()
            typeProblem = typeProblemSpinner.getModel() as TypeProblem
        }
    }

    private fun loadData(id: Int) {
        if (id <= 0) {
            displayMessage(this, getString(R.string.search_is_empty))
            finish()
        }
        showOrHideView(binding.layoutInputData, false)
        showOrHideView(binding.progressUpdateRepairRequest, true)
        viewModel.getById(id)
        viewModel.loadDataScreenRepairRequest()
    }

    private fun fillInFields(repairRequest: RepairRequest) {
        binding.editId.setText(repairRequest.id.toString())
        for (index in 0 until condominiumList.size) {
            if (condominiumList[index].id == repairRequest.condominium.id) {
                binding.spinnerCondominium.setSelection(index)
                break
            }
        }

        binding.editApartment.setText(repairRequest.apartmentNumber)
        binding.editDescriptionProblem.setText(repairRequest.problemDescription)
        for (index in 0 until typeProblemList.size) {
            if (typeProblemList[index].id == repairRequest.typeProblem.id) {
                binding.spinnerTypeProblem.setSelection(index)
                break
            }
        }
    }

    private fun fillSpinner(repairRequestRegisterDataDTO: RepairRequestRegisterDataDTO) {
        condominiumList = repairRequestRegisterDataDTO.condominiums
        typeProblemList = repairRequestRegisterDataDTO.typeProblems
        spinnerAdapterCondominium.addAll(condominiumList.map { CondominiumSpinnerDTO(it) })
        spinnerAdapterTypeProblem.addAll(typeProblemList.map { TypeProblemSpinnerDTO(it) })
    }

    private fun checkSpinners() {
        repairRequest?.let {
            for (index in 0 until condominiumList.size) {
                if (condominiumList[index].id == it.condominium.id) {
                    binding.spinnerCondominium.setSelection(index)
                    break
                }
            }

            for (index in 0 until typeProblemList.size) {
                if (typeProblemList[index].id == it.typeProblem.id) {
                    binding.spinnerTypeProblem.setSelection(index)
                    break
                }
            }
        }
    }

    private fun isLoading(): Boolean {
        return (repairRequest == null
                && condominiumList.isEmpty()
                && typeProblemList.isEmpty())
    }

    private fun showInputLayout() {
        showOrHideView(binding.layoutInputData, true)
        showOrHideView(binding.progressUpdateRepairRequest, false)
    }
}