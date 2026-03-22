package com.example.digitalstorageroom.item.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.digitalstorageroom.R

data class Item(
    val name: String,
    val amount: Int = 0,
    val description: String = "",
    val barCode: String? = null,
    val qrCode: String? = null,
)

@Composable
fun ListAnimatedItems(
    items: List<Item>,
    modifier: Modifier = Modifier
) {
    val lazyColumnListState = rememberLazyListState()
    LazyColumn(
        modifier,
        state = lazyColumnListState,
        ) {
        
        // Use a unique key per item, so that animations work as expected.
        items(items, key = {it.name}) {
            ListItem(
                supportingContent = {Text(it.description)},
                leadingContent = {Text("LeadingContent")},
                overlineContent = {Text("OverlineContent")},
                headlineContent = { Text(it.name) },
                trailingContent = { Text("Amount: ${it.amount}")},
                modifier = Modifier
                    .animateItem(
                        // Optionally add custom animation specs
                    )
                    .fillParentMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 0.dp),
            )
        }
    }
}

@Composable
fun ItemView(item: Item, modifier: Modifier = Modifier) {
    var showItemDetailView by rememberSaveable { mutableStateOf(false) }

    /*val extraPadding by animateDpAsState(
        if (showItemDetailView) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )*/

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            //TIP: Achieve card look through shadow and clop, alternatively use the Material Card.
            .shadow(4.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                /*.padding(
                    bottom = extraPadding
                        // Make sure that the padding is never negativ
                        .coerceAtLeast(0.dp)
                ),*/
            //verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    //Important: there's no 'alignEnd' so weighted elements
                    // pushes away all elements without a weight.
                    .weight(1f)
                    .padding(12.dp)

            ) {
                Text(
                    text = item.name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(text = item.amount.toString())
                if (showItemDetailView) {
                    ItemDetailView(item)
                }
            }
            IconButton(
                onClick = { showItemDetailView = !showItemDetailView },
            ) {
                Icon(
                    imageVector = if (showItemDetailView) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (showItemDetailView) stringResource(R.string.show_less) else stringResource(
                        R.string.show_more
                    )
                )
            }
            /*ElevatedButton(
                onClick = { showItemDetailView = !showItemDetailView },
                ) {
                Text(text = if(showItemDetailView) "Close" else "Open")
            }*/
        }

    }
}

@Composable
fun ItemDetailView(
    item: Item,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "${item.description}\n".repeat(5)
    )
}

