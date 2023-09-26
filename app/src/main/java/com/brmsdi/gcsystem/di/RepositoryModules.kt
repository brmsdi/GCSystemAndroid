package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.data.repository.EmployeeRepository
import com.brmsdi.gcsystem.data.repository.LesseeRepository
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.data.repository.impl.EmployeeRepositoryImpl
import com.brmsdi.gcsystem.data.repository.impl.LesseeRepositoryImpl
import com.brmsdi.gcsystem.data.repository.impl.RepairRequestRepositoryImpl
import com.brmsdi.gcsystem.ui.utils.AuthType.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val RepositoryModules = module {
    single<AuthenticableRepository>(named(EMPLOYEE.type)) { EmployeeRepositoryImpl() }
    single<AuthenticableRepository>(named(LESSEE.type)) { LesseeRepositoryImpl() }
    single<EmployeeRepository>(named(EMPLOYEE.type)) { EmployeeRepositoryImpl() }
    single<LesseeRepository>(named(LESSEE.type)) { LesseeRepositoryImpl() }
    single<RepairRequestRepository> { RepairRequestRepositoryImpl() }
}