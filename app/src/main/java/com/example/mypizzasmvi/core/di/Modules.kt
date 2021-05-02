package com.example.mypizzasmvi.core.di

import com.example.mypizzasmvi.core.connection.ConnectionChecker
import com.example.mypizzasmvi.core.connection.ConnectionCheckerImpl
import com.example.mypizzasmvi.core.retrofit.provideRetrofit
import com.example.mypizzasmvi.core.services.provideServices
import com.example.mypizzasmvi.core.utils.Constants
import org.koin.dsl.module

val serviceModule = module(override = true) {
    factory<ConnectionChecker>{ ConnectionCheckerImpl(get()) }
    single { provideServices(get()) }
    single {
        provideRetrofit(baseUrl = Constants.BASE_URL)
    }
}