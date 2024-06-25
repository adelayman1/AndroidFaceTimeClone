package com.example.facetimeclonecompose.presentation.otpScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.facetimeclonecompose.presentation.otpScreen.uiStates.OtpUiState
import com.example.facetimeclonecompose.presentation.ui.theme.BorderGray
import com.example.facetimeclonecompose.presentation.ui.theme.LightButtonColor
import com.example.facetimeclonecompose.presentation.ui.theme.Red
import com.example.facetimeclonecompose.presentation.ui.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun OtpCodeView(
    state: OtpUiState,
    onTextChanged: (String) -> Unit,
    focusRequester: FocusRequester
) {
//    val code = remember(initialCode) {
//        mutableStateOf())
//    }
//    val focusRequester = FocusRequester()
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
    Column {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            BasicTextField(
                value = TextFieldValue(
                    state.code.text,
                    TextRange(state.code.text.length)
                ),
                onValueChange = { onTextChanged(it.text) },
                modifier = Modifier.focusRequester(focusRequester = focusRequester),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                decorationBox = {
                    CodeInputDecoration(state.code.text, state.codeLength)
                }
            )
        }
        AnimatedVisibility(state.code.errorMessage != null) {
            Text(
                modifier = Modifier
                    .padding(start = 20.sdp, top = 3.sdp, bottom = 6.sdp)
                    .fillMaxWidth(),
                text = state.code.errorMessage ?: "",
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal,
                fontSize = 10.ssp,
                color = Red
            )
        }
    }
}

@Composable
private fun CodeInputDecoration(code: String, length: Int) {
    Box(modifier = Modifier) {
        Row(horizontalArrangement = Arrangement.Center) {
            for (i in 0 until length) {
                val text = if (i < code.length) code[i].toString() else ""
                CodeEntry(text)
            }
        }
    }
}

@Composable
private fun CodeEntry(text: String) {
    Box(
        modifier = Modifier
            .padding(4.sdp)
            .width(35.sdp)
            .height(55.sdp),
        contentAlignment = Alignment.Center
    ) {
        val color = animateColorAsState(
            targetValue = if (text.isEmpty()) Color.Gray.copy(alpha = .8f)
            else BorderGray
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontSize = 20.ssp,
            fontWeight = FontWeight.Medium,
            color = LightButtonColor
        )
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 6.sdp, end = 6.sdp, bottom = 8.sdp)
                .height(2.sdp)
                .fillMaxWidth()
                .background(color.value)
        )
    }
}