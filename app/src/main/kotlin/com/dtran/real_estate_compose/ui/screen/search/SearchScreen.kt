package com.dtran.real_estate_compose.ui.screen.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.dtran.real_estate_compose.R
import com.dtran.real_estate_compose.data.Response
import com.dtran.real_estate_compose.ui.widget.AppBottomBar
import com.dtran.real_estate_compose.ui.widget.AppSearchBar
import com.dtran.real_estate_compose.ui.widget.LottieProgressBar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = koinViewModel()) {
    val keyword = remember { mutableStateOf("") }
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val dataList = viewModel.dataList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initial.collectLatest {
            when (it) {
                is Response.Loading -> isLoading.value = true
                is Response.Error -> {
                    isLoading.value = false
                    it.error?.localizedMessage?.let { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                is Response.Success -> {
                    isLoading.value = false
                    viewModel.updateData(it.data ?: emptyList())
                }
            }
        }
    }

    Scaffold(bottomBar = { AppBottomBar(navController = navController) }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(
                    top = dimensionResource(id = R.dimen.padding_screen),
                    start = dimensionResource(id = R.dimen.padding_screen),
                    end = dimensionResource(id = R.dimen.padding_screen),
                    bottom = 0.dp
                )
        ) {
            AppSearchBar(keyword = keyword.value, onKeywordChanged = { term -> keyword.value = term }, onSearch = {
                coroutineScope.launch {
                    viewModel.search(keyword = keyword.value).collectLatest { response ->
                        when (response) {
                            is Response.Loading -> isLoading.value = true
                            is Response.Error -> {
                                isLoading.value = false
                                response.error?.localizedMessage?.let { msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            }

                            is Response.Success -> {
                                isLoading.value = false
                                response.data?.let { list ->
                                    viewModel.updateData(list)
                                }
                            }
                        }
                    }
                }
            })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_item)))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_screen))) {
                items(items = viewModel.dataList.value, key = { item -> item.id }) { item ->
                    val isFavourite = rememberSaveable { mutableStateOf(item.isFavourite) }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_screen)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            SubcomposeAsyncImage(
                                model = item.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                loading = {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) { CircularProgressIndicator() }
                                },
                                modifier = Modifier
                                    .size(125.dp)
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.radius_item)))
                            )
                            if (!isFavourite.value) {
                                IconButton(
                                    onClick = {
                                        isFavourite.value = true
                                        viewModel.addToFavourite(item.id)
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        tint = Color.Red,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        isFavourite.value = false
                                        viewModel.removeFromFavourite(item.id)
                                    },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        tint = Color.Red,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        Column {
                            Text(text = item.name ?: "", fontWeight = FontWeight.Bold)
                            Row {
                                Image(painterResource(id = R.drawable.ic_location), contentDescription = null)
                                Text(text = "${item.city}, ${item.country}")
                            }
                        }
                    }
                }
            }
        }
    }

    LottieProgressBar(loading = isLoading.value)
}