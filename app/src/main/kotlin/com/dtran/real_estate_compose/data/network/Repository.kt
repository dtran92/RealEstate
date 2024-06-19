package com.dtran.real_estate_compose.data.network

import com.dtran.real_estate_compose.data.Response
import com.dtran.real_estate_compose.data.model.Property
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllProperties(): Flow<Response<List<Property>>>

    fun getPropertiesFiltered(
        name: String? = null,
        type: Int? = null,
        city: String? = null,
        country: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        featured: Boolean? = null,
    ): Flow<Response<List<Property>>>

    fun getTopProperties(): Flow<Response<List<Property>>>
}