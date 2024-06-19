package com.dtran.real_estate_compose.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dtran.real_estate_compose.R
import com.dtran.real_estate_compose.data.local.DataStoreUtil
import com.dtran.real_estate_compose.ui.widget.AppButtonDark
import org.koin.compose.koinInject

@Composable
fun WelcomeScreen(
    dataStore: DataStoreUtil = koinInject(),
    goToHome: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painterResource(id = R.drawable.img_welcome), contentDescription = null,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_screen))
                    .padding(top = 60.dp, bottom = dimensionResource(id = R.dimen.padding_screen))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val annotatedString = buildAnnotatedString {
                    val findYourHome = stringResource(id = R.string.find_your_home)
                    val findYourHomeDesc = stringResource(id = R.string.find_your_home_description)
                    withStyle(ParagraphStyle(lineHeight = 35.sp)) {
                        withStyle(SpanStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)) {
                            append(findYourHome)
                        }
                    }
                    withStyle(ParagraphStyle(lineHeight = 24.sp)) {
                        append(findYourHomeDesc)
                    }
                }
                Text(annotatedString)
                AppButtonDark(buttonText = stringResource(id = R.string.get_started), onButtonClick = {
                    goToHome.invoke()
                    dataStore.setFirstLaunch(false)
                })
            }


        }
    }
}


@Composable
@Preview
fun WelcomeScreenPreview() {
    WelcomeScreen {}
}