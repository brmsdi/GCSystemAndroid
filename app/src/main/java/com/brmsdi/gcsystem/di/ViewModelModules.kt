package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.ui.activity.newRepairRequest.NewRepairRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModules = module {
    viewModel { NewRepairRequestViewModel(get(), get()) }
}