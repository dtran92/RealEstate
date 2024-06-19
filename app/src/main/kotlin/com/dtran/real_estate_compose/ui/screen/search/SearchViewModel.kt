package com.dtran.real_estate_compose.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dtran.real_estate_compose.data.Response
import com.dtran.real_estate_compose.data.network.RepositoryImpl
import com.dtran.real_estate_compose.ui.model.PropertyUiModel
import kotlinx.coroutines.flow.*

class SearchViewModel(private val repository: RepositoryImpl) : ViewModel() {
    private val _propertyList = MutableStateFlow<List<PropertyUiModel>>(emptyList())
    val propertyList = _propertyList.asStateFlow()

    val initial = repository.getAllProperties().map {
        when (it) {
            is Response.Loading -> Response.Loading()
            is Response.Error -> Response.Error(error = it.error)
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Response.Loading())

    fun updateData(data: List<PropertyUiModel>) {
        _propertyList.value = data
    }

    fun search(keyword: String): StateFlow<Response<List<PropertyUiModel>?>> {
        return repository.getPropertiesFiltered(name = keyword).map {
            when (it) {
                is Response.Loading -> Response.Loading()
                is Response.Error -> Response.Error(error = it.error)
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
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Response.Loading())
    }
}