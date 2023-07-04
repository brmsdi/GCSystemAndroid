package com.brmsdi.gcsystem.ui.activity.updateRepairRequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import com.brmsdi.gcsystem.R
import com.brmsdi.gcsystem.data.model.Condominium
import com.brmsdi.gcsystem.data.model.RepairRequest
import com.brmsdi.gcsystem.data.model.TypeProblem
import com.brmsdi.gcsystem.databinding.ActivityUpdateRepairRequestBinding
import com.brmsdi.gcsystem.ui.utils.Mock
import com.brmsdi.gcsystem.ui.utils.RepairRequestData
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.displayMessage
import com.brmsdi.gcsystem.ui.utils.TextUtils.Companion.fieldsIsNotEmpty

class UpdateRepairRequest : AppCompatActivity(), RepairRequestData, OnClickListener {
    private lateinit var binding: ActivityUpdateRepairRequestBinding
    private var repairRequest: RepairRequest? = null
    private var condominiumList = mutableListOf<Condominium>()
    private var typeProblemList = mutableListOf<TypeProblem>()
    private lateinit var spinnerAdapterCondominium: ArrayAdapter<Condominium>
    private lateinit var spinnerAdapterTypeProblem: ArrayAdapter<TypeProblem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRepairRequestBinding.inflate(layoutInflater)
        //repairRequest = loadRepairRequestData(intent.extras)
        repairRequest = Mock.listRepairRequestList()[0]
        spinnerAdapterCondominium = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<Condominium>()
        )
        spinnerAdapterTypeProblem = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<TypeProblem>()
        )
        binding.spinnerCondominium.adapter = spinnerAdapterCondominium
        binding.spinnerTypeProblem.adapter = spinnerAdapterTypeProblem
        loadData()
        loadSpinner()
        fillInFields(repairRequest!!)
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
                )) {
                    displayMessage(this, getString(R.string.fields_empty))
                    return
                }
                save(repairRequest!!)
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
        displayMessage(this, getString(R.string.updated_info_success))
        finish()
    }

    private fun getFields(repairRequest: RepairRequest) {
        repairRequest.apply {
            condominium = binding.spinnerCondominium.selectedItem as Condominium
            apartmentNumber = binding.editApartment.text.toString()
            problemDescription = binding.editDescriptionProblem.text.toString()
            typeProblem = binding.spinnerTypeProblem.selectedItem as TypeProblem
        }
    }


    private fun loadData() {
        condominiumList = Mock.condominiumList()
        typeProblemList = Mock.typeProblemList()
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

    private fun loadSpinner() {
        spinnerAdapterCondominium.addAll(condominiumList)
        spinnerAdapterTypeProblem.addAll(typeProblemList)
    }
}