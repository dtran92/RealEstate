package com.dtran.real_estate_compose.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.dtran.real_estate_compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    keyword: String, onKeywordChanged: (String) -> Unit, onSearch: (String) -> Unit, modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    SearchBar(
        query = keyword,
        onQueryChange = onKeywordChanged,
        onSearch = {
            keyboardController?.hide()
            onSearch(keyword)
        },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_item)),
        active = false,
        onActiveChange = {},
        trailingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                onSearch.invoke(keyword)
            }, modifier = modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search"
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {}

//    SearchBar(inputField = {
//        TextField(
//            value = keyword,
//            onValueChange = onKeywordChanged,
//            shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_item)),
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//            ),
//            singleLine = true,
//            trailingIcon = {
//                IconButton(onClick = {
//                    keyboardController?.hide()
//                    onSearch.invoke(keyword) }, modifier = modifier.size(36.dp)) {
//                    Icon(
//                        imageVector = Icons.Default.Search, contentDescription = "Search"
//                    )
//                }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
//            keyboardActions = KeyboardActions(
//                onSearch = {
//                    keyboardController?.hide()
//                    onSearch(keyword)
//                }
//            )
//        )
//    }, expanded = false, onExpandedChange = { }, modifier = modifier.fillMaxWidth()
//    ) {}
}