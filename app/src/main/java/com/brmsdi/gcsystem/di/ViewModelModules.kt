package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.ui.activity.detailRepairRequest.DetailRepairRequestViewModel
import com.brmsdi.gcsystem.ui.activity.newRepairRequest.NewRepairRequestViewModel
import com.brmsdi.gcsystem.ui.fragment.repairRequest.RepairRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModules = module {
    viewModel { NewRepairRequestViewModel(get(), get()) }
    viewModel { RepairRequestViewModel(get(), get()) }
    viewModel { DetailRepairRequestViewModel(get(), get()) }
}