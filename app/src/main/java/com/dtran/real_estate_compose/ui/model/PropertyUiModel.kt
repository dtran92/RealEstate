package com.dtran.real_estate_compose.ui.model

data class PropertyUiModel(
    val id: String,
    val name: String?,
    val type: Int?,
    val address: String?,
    val city: String?,
    val country: String?,
    val description: String?,
    val imageUrl: String?,
    val price: Long?,
    val area: Int?,
    val rating: Double?,
    val featured: Boolean?
)