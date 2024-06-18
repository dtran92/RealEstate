package com.dtran.real_estate_compose.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreUtil(private val context: Context) {
    companion object {
        private const val FIRST_LAUNCH = "first_launch"
    }

    @OptIn(DelicateCoroutinesApi::class)
    val firstLaunch = context.dataStore.data
        .map { preferences -> preferences[booleanPreferencesKey(FIRST_LAUNCH)] ?: true }
        .catch { _ -> emit(true) }
        .stateIn(GlobalScope, SharingStarted.Lazily, null)

    fun setFirstLaunch(value: Boolean) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(FIRST_LAUNCH)] = value
            }
        }
    }


}