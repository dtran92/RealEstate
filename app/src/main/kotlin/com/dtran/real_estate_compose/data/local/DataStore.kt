package com.dtran.real_estate_compose.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreUtil(private val context: Context) {
    companion object {
        private const val FIRST_LAUNCH = "first_launch"
        private const val FAVOURITE_LIST = "favourite_list"
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

    val favouriteList = context.dataStore.data
        .map { preferences -> preferences[stringSetPreferencesKey(FAVOURITE_LIST)]?.toList() ?: emptyList() }
        .catch { _ -> emit(emptyList()) }

    suspend fun saveToFavourite(value: String) {
        context.dataStore.edit { preferences ->
            val data = preferences[stringSetPreferencesKey(FAVOURITE_LIST)] ?: mutableSetOf()
            data.apply {
                preferences[stringSetPreferencesKey(FAVOURITE_LIST)] = this.toMutableSet().apply {
                    add(value)
                }
            }
        }
    }

    suspend fun removeFromFavourite(value: String) {
        context.dataStore.edit { preferences ->
            val data = preferences[stringSetPreferencesKey(FAVOURITE_LIST)]
            data?.let {
                preferences[stringSetPreferencesKey(FAVOURITE_LIST)] = it.toMutableSet().apply {
                    remove(value)
                }
            }
        }
    }
}