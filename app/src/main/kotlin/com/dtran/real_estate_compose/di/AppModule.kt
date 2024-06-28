package com.dtran.real_estate_compose.di

import com.dtran.real_estate_compose.data.local.DataStoreUtil
import com.dtran.real_estate_compose.data.network.RepositoryImpl
import com.dtran.real_estate_compose.ui.MainActivityViewModel
import com.dtran.real_estate_compose.ui.screen.home.HomeViewModel
import com.dtran.real_estate_compose.ui.screen.search.SearchViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // network
    single {
        createSupabaseClient(
            supabaseUrl = "https://gplldjcpdskwjitlstps.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdwbGxkamNwZHNrd2ppd" +
                    "GxzdHBzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTg2ODA4NzIsImV4cCI6MjAzNDI1Njg3Mn0.7--HJ7gNw3EIvRTj9LY-u" +
                    "d26OWdeOwZq33UGhWKtnrk",
        ) {
            install(Postgrest)
        }
    }

    // data
    single { RepositoryImpl(get()) }
    single { DataStoreUtil(androidContext()) }

    // viewmodel
    viewModel { HomeViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }
}