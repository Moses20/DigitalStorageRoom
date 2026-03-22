package com.example.digitalstorageroom.item

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalstorageroom.item.view.ItemView

@Preview
@Composable
fun ItemsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn (
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(staticItems) { item ->
            ItemView(item, Modifier.animateItem())
        }
    }
}