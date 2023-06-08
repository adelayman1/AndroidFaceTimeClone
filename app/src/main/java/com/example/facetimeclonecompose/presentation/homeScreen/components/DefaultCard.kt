package com.example.facetimeclonecompose.presentation.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import com.example.facetimeclonecompose.presentation.ui.theme.LightGray
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCard(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LightGray,
    icon: ImageVector,
    rotate: Float = 0f,
    onClick: () -> Unit
) {
    return Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier.then(Modifier.height(50.sdp)),
        shape = RoundedCornerShape(6.sdp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(22.sdp)
                    .rotate(rotate),
                imageVector = icon,
                contentDescription = text,
                tint = Color.White
            )
            Text(
                text = text,
                fontSize = 11.ssp,
                color = Color.White,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}