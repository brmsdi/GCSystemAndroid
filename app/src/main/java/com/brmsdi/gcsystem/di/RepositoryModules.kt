package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.data.repository.AuthenticableRepository
import com.brmsdi.gcsystem.data.repository.ContractRepository
import com.brmsdi.gcsystem.data.repository.DebtRepository
import com.brmsdi.gcsystem.data.repository.EmployeeRepository
import com.brmsdi.gcsystem.data.repository.LesseeRepository
import com.brmsdi.gcsystem.data.repository.OrderServiceRepository
import com.brmsdi.gcsystem.data.repository.RepairRequestRepository
import com.brmsdi.gcsystem.data.repository.impl.ContractRepositoryImpl
import com.brmsdi.gcsystem.data.repository.impl.DebtRepositoryImpl
import com.brmsdi.gcsystem.data.repository.impl.EmployeeRepositoryImpl
import com.brmsdi.gcsystem.data.repository.impl.LesseeRepositoryImpl
import com.brmsdi.gcsystem.data.repository.impl.OrderServiceRepositoryImpl
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
    single<DebtRepository> { DebtRepositoryImpl() }
    single<ContractRepository> { ContractRepositoryImpl() }
    single<OrderServiceRepository> { OrderServiceRepositoryImpl() }
}