package com.dtran.real_estate_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.dtran.real_estate_compose.navigation.Navigation
import com.dtran.real_estate_compose.ui.MainActivityViewModel
import com.dtran.real_estate_compose.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.annotation.KoinExperimentalAPI


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    KoinAndroidContext {
                        Navigation(navController)
                    }
                }
            }
        }
    }

    private fun setUpSplashScreen() {
        var shouldShowSplashScreen = true
        lifecycleScope.launch {
            viewModel.isReady.collectLatest {
                shouldShowSplashScreen = !it
            }
        }
        installSplashScreen().setKeepOnScreenCondition {
            shouldShowSplashScreen
        }
    }
}