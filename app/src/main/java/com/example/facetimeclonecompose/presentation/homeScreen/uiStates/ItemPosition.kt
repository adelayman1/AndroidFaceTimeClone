package com.example.facetimeclonecompose.presentation.homeScreen.uiStates

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

sealed class ItemPosition(var roundedCornerShape: RoundedCornerShape) {
    object FirstItem : ItemPosition(
        roundedCornerShape = RoundedCornerShape(
            topEnd = 7.dp,
            topStart = 7.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    )
    object MiddleItem : ItemPosition(
        roundedCornerShape = RoundedCornerShape(
            topEnd = 0.dp,
            topStart = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    )
    object LastItem : ItemPosition(
        roundedCornerShape = RoundedCornerShape(
            topEnd = 0.dp,
            topStart = 0.dp,
            bottomEnd = 7.dp,
            bottomStart = 7.dp
        )
    )
    object SeparatedItem : ItemPosition(
        roundedCornerShape = RoundedCornerShape(
            topEnd = 7.dp,
            topStart = 7.dp,
            bottomEnd = 7.dp,
            bottomStart = 7.dp
        )
    )
}
/*
class ItemPositionEnum {
    FIRST_ITEM(),
    MIDDLE_ITEM(),
    LAST_ITEM(),
    SEPARATE_ITEM()
}*/
