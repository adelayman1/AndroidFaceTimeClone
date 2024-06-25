package com.example.facetimeclonecompose.presentation.loginScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.DarkWhite
import com.example.facetimeclonecompose.presentation.ui.theme.Red
import com.example.facetimeclonecompose.presentation.ui.theme.TextHint
import com.example.facetimeclonecompose.presentation.ui.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentInputField(
    text: String,
    leadingText: String,
    hint: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    isErrorVisible: Boolean = false,
    keyboardType: KeyboardType,
    textVisible: Boolean = true
) {
    Column(modifier = Modifier.background(DarkGray)){

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            singleLine = true,
            onValueChange = onValueChange,
            visualTransformation = if (textVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DarkGray,
                cursorColor = Color.White,
                textColor = DarkWhite,
                focusedIndicatorColor = DarkGray,
                unfocusedIndicatorColor = DarkGray
            ),
            placeholder = {
                Text(
                    modifier = Modifier.padding(start = 10.sdp),
                    text = hint,
                    fontSize = 14.ssp,
                    color = TextHint,
                )
            },
            leadingIcon = {
                Text(
                    modifier = Modifier.padding(start = 20.sdp),
                    text = leadingText,
                    fontSize = 14.ssp,
                    color = Color.White,
                )
            }
        )
        AnimatedVisibility(isErrorVisible) {
            Text(
                modifier = Modifier
                    .padding(start = 20.sdp, top = 3.sdp, bottom = 6.sdp)
                    .fillMaxWidth(),
                text = error ?: "",
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal,
                fontSize = 10.ssp,
                color = Red
            )
        }
    }
}
