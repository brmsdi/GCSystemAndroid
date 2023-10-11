package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.ui.activity.detailRepairRequest.DetailRepairRequestViewModel
import com.brmsdi.gcsystem.ui.activity.newRepairRequest.NewRepairRequestViewModel
import com.brmsdi.gcsystem.ui.activity.updateRepairRequest.UpdateRepairRequestViewModel
import com.brmsdi.gcsystem.ui.fragment.contract.ContractViewModel
import com.brmsdi.gcsystem.ui.fragment.debt.DebtViewModel
import com.brmsdi.gcsystem.ui.fragment.repairRequest.RepairRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModules = module {
    viewModel { NewRepairRequestViewModel(get(), get()) }
    viewModel { RepairRequestViewModel(get(), get()) }
    viewModel { DetailRepairRequestViewModel(get(), get()) }
    viewModel { UpdateRepairRequestViewModel(get(), get()) }
    viewModel { DebtViewModel(get(), get()) }
    viewModel { ContractViewModel(get(), get()) }
}