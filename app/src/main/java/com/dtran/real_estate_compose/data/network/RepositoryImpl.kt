package com.dtran.real_estate_compose.data.network

import com.dtran.real_estate_compose.data.Response
import com.dtran.real_estate_compose.data.model.Property
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


class RepositoryImpl(private val supabaseClient: SupabaseClient) : Repository {
    override fun getAllProperties(): Flow<Response<List<Property>>> =
        flow<Response<List<Property>>> {
            emit(
                Response.Success(
                    supabaseClient.from("properties").select().decodeList()
                )
            )
        }
            .onStart { emit(Response.Loading()) }
            .catch { emit(Response.Error(error = it)) }
            .flowOn(Dispatchers.IO)

    override fun getPropertiesFiltered(
        name: String?,
        type: Int?,
        city: String?,
        country: String?,
        minPrice: Int?,
        maxPrice: Int?,
        featured: Boolean?
    ): Flow<Response<List<Property>>> = flow<Response<List<Property>>> {
        emit(
            Response.Success(
                supabaseClient.from("properties").select {
                    filter {
                        or {
                            if (name != null) {
                                ilike("name", "%${name}%")
                            }
                            if (type != null) {
                                eq("type", type)
                            }
                            if (city != null) {
                                eq("city", city)
                            }
                            if (country != null) {
                                eq("country", country)
                            }
                            if (minPrice != null) {
                                gte("price", minPrice)
                            }
                            if (maxPrice != null) {
                                lte("price", maxPrice)
                            }
                            if (featured != null) {
                                eq("featured", featured)
                            }
                        }
                    }
                }.decodeList()
            )
        )
    }
        .onStart { emit(Response.Loading()) }
        .catch { emit(Response.Error(error = it)) }
        .flowOn(Dispatchers.IO)

    override fun getTopProperties(): Flow<Response<List<Property>>> = flow<Response<List<Property>>> {
        emit(
            Response.Success(
                supabaseClient.from("properties").select {
                    order(column = "rating", order = Order.DESCENDING)
                }.decodeList()
            )
        )
    }
        .onStart { emit(Response.Loading()) }
        .catch { emit(Response.Error(error = it)) }
        .flowOn(Dispatchers.IO)
}