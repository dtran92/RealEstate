package com.dtran.real_estate_compose.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.dtran.real_estate_compose.R
import com.dtran.real_estate_compose.data.Response
import com.dtran.real_estate_compose.ui.widget.AppBottomBar
import com.dtran.real_estate_compose.ui.widget.LottieProgressBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController
) {
    val featuredList = viewModel.featuredPropertyList.collectAsStateWithLifecycle()
    val isLoading = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { AppBottomBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
                .padding(it)
        ) {
            Text(stringResource(id = R.string.featured_unit), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                featuredList.value.apply {
                    when (this) {
                        is Response.Loading -> isLoading.value = true
                        is Response.Error -> {
                            isLoading.value = false
                            this.error?.localizedMessage?.let { msg ->
                                Toast.makeText(LocalContext.current, msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                        is Response.Success -> {
                            isLoading.value = false
                            this.data?.let {
                                LazyVerticalGrid(
                                    columns = GridCells.Adaptive(150.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                ) {
                                    items(items = it, key = { item -> item.id }) { item ->
                                        Card(
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                            elevation = CardDefaults.cardElevation(4.dp),
                                            modifier = Modifier.padding(vertical = 10.dp)
                                        ) {
                                            SubcomposeAsyncImage(
                                                model = item.imageUrl,
                                                loading = {
                                                    Box(
                                                        contentAlignment = Alignment.Center,
                                                        modifier = Modifier.fillMaxSize()
                                                    ) {
                                                        CircularProgressIndicator(
                                                            strokeCap = StrokeCap.Round
                                                        )
                                                    }
                                                },
                                                contentDescription = null,
                                                contentScale = ContentScale.FillWidth,
                                                modifier = Modifier.height(100.dp)
                                            )
                                            Text(
                                                text = "${item.name}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(text = "${item.city}, ${item.country}")
                                        }
                                    }
                                }
                            } ?: run { Text("Error") }
                        }
                    }
                }
            }

        }
    }
    LottieProgressBar(
        loading = isLoading.value
    )
}