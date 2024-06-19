package com.dtran.real_estate_compose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtran.real_estate_compose.data.Response
import com.dtran.real_estate_compose.data.network.RepositoryImpl
import com.dtran.real_estate_compose.ui.model.PropertyUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: RepositoryImpl
) : ViewModel() {

    val featuredPropertyList = repository.getPropertiesFiltered(featured = true).map {
        when (it) {
            is Response.Error -> Response.Error(error = it.error)
            is Response.Loading -> Response.Loading()
            is Response.Success -> Response.Success(data = it.data?.map { property ->
                PropertyUiModel(
                    id = property.id,
                    price = property.price,
                    featured = property.featured,
                    description = property.description,
                    address = property.address,
                    name = property.name,
                    imageUrl = property.imageUrl,
                    type = property.type,
                    area = property.area,
                    city = property.city,
                    country = property.country,
                    rating = property.rating
                )
            })
        }
    }.flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = Response.Loading())
}