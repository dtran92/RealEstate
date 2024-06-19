package com.dtran.real_estate_compose.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.dtran.real_estate_compose.R

@Composable
fun AppButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_item)),

        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = onButtonClick
    ) {
        Text(text = buttonText)
    }
}

@Composable
fun AppButtonDark(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius_item)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        onClick = onButtonClick
    ) {
        Text(text = buttonText, color = MaterialTheme.colorScheme.primary)
    }
}

@Preview
@Composable
fun AppButtonPreview() {
    AppButton(buttonText = "Button", onButtonClick = {})
}