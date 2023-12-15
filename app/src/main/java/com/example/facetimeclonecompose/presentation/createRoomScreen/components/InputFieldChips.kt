@file:Suppress("UNREACHABLE_CODE")

package com.example.facetimeclonecompose.presentation.createRoomScreen.components
//
import android.view.KeyEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.facetimeclonecompose.presentation.createRoomScreen.uiStates.ParticipantsInputFieldUiState
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGray
import com.example.facetimeclonecompose.presentation.ui.theme.DarkGraySecond
import com.example.facetimeclonecompose.presentation.ui.theme.Gray60
import com.example.facetimeclonecompose.presentation.ui.theme.Red
import com.example.facetimeclonecompose.presentation.ui.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TextFieldContent(
    state: ParticipantsInputFieldUiState,
    onValueChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    onClickEnter: () -> Unit
) {
    Column {
        // TODO("EDIT THAT")
//        if (textFieldValue.text.isEmpty() && listOfChips.isEmpty()) {
//            Text(
//                text = "placeholder",
//                color = if (emphasizePlaceHolder && !isFocused.value) {
//                    MaterialTheme.colors.onSurface
//                } else {
//                    if (MaterialTheme.colors.isLight) {
//                        LocalCustomColors.current.muted
//                    } else {
//                        Color.Gray
//                    }
//                },
//                modifier = Modifier.align(alignment = Alignment.CenterStart)
//            )
//        }
        TextField(
            value = state.emailFieldUiState.text,
            onValueChange = onValueChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.sdp)
                .focusRequester(focusRequester)
                .onKeyEvent {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        focusRequester.requestFocus()
                        true
                    }
                    false
                },
            leadingIcon = { Text(text = "To ", color = Gray60) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClickEnter()
                    focusRequester.requestFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DarkGray,
                textColor = Color.White
            ),

            )
        AnimatedVisibility(state.emailFieldUiState.errorMessage != null) {
            Text(
                modifier = Modifier
                    .padding(start = 20.sdp, top = 3.sdp, bottom = 6.sdp)
                    .fillMaxWidth(),
                text = state.emailFieldUiState.errorMessage ?: "",
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal,
                fontSize = 10.ssp,
                color = Red
            )
        }
        FlowRow(
            modifier = Modifier
                .background(DarkGray)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 10.sdp),
            horizontalArrangement = Arrangement.spacedBy(5.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Text(text = "To", color = Gray60)
            state.addedParticipantsUiState.forEach { participant ->
                InputChip(
                    onClick = { participant.onDelete() },
                    selected = true,
                    label = {
                        Text(
                            text = participant.userEmail,
                            color = if (participant.userExist) Color.White else Color.Red
                        )
                    },
                    modifier = Modifier.wrapContentWidth(),
                    colors = InputChipDefaults.inputChipColors(
                        containerColor = DarkGraySecond,
                        selectedContainerColor = DarkGraySecond
                    ),
                    trailingIcon = {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(3.dp)
                        ) {
                            if (participant.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.then(Modifier.size(12.dp))
                                )
                            } else {
                                Icon(
                                    painter = rememberVectorPainter(image = Icons.Rounded.Close),
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = Color.Black
                                )
                            }

                        }
                    },
                )
            }

            /* BasicTextField( //TODO("EDIT")
                 value = TextFieldValue(state.emailFieldUiState.text),
                 onValueChange = {
                     onValueChanged(it)
                 },
                 modifier = Modifier
                     .padding(start = 5.sdp)
                     .focusRequester(focusRequester)
                     .onKeyEvent {
                         if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                             focusRequester.requestFocus()
                             true
                         }
                         false
                     },
                 textStyle = LocalTextStyle.current.copy(color = Color.White),
                 singleLine = true,
                 decorationBox = { innerTextField ->
                     Row(
                         modifier = Modifier
                             .wrapContentWidth()
                             .defaultMinSize(minHeight = 48.dp),
                         verticalAlignment = Alignment.CenterVertically,
                         horizontalArrangement = Arrangement.Start
                     ) {
                         Box(
                             modifier = Modifier.wrapContentWidth(),
                             contentAlignment = Alignment.CenterStart
                         ) {
                             Row(
                                 modifier = Modifier
                                     .defaultMinSize(minWidth = 4.dp)
                                     .wrapContentWidth(),
                             ) {
                                 innerTextField()
                             }
                         }
                     }
                 },
                 readOnly = false,
                 keyboardOptions = KeyboardOptions(
                     imeAction = ImeAction.Done,
                     keyboardType = KeyboardType.Email
                 ),
                 keyboardActions = KeyboardActions(
                     onDone = {
                         onClickEnter()
                         focusRequester.requestFocus()
                     }
                 )
             )*/
        }
    }
}
