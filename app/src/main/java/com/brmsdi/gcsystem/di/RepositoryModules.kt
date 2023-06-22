package com.brmsdi.gcsystem.di

import com.brmsdi.gcsystem.data.repositories.AuthenticableRepository
import com.brmsdi.gcsystem.data.repositories.impl.EmployeeRepositoryImpl
import com.brmsdi.gcsystem.data.repositories.impl.LesseeRepositoryImpl
import com.brmsdi.gcsystem.ui.utils.AuthType.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val RepositoryModules = module {
    single<AuthenticableRepository>(named(EMPLOYEE.type)) { EmployeeRepositoryImpl() }
    single<AuthenticableRepository>(named(LESSEE.type)) { LesseeRepositoryImpl() }
}