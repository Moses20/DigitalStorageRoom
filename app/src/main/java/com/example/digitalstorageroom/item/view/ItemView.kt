package com.example.digitalstorageroom.item.view

import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.digitalstorageroom.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random


data class Item(
    val name: String,
    val amount: Int = 0,
    val description: String = "",
    val barCode: String? = null,
    val qrCode: String? = null,
)

//TODO: Keine Ahnung was hier abgeht. Wie sollten StateFlow verwendet werden? Und was macht ein ViewModel?
class AnimatedOrderedListViewModel : ViewModel() {

    private val itemNames = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten")
    private val _data = itemNames.map { Item(it, Random.nextInt(0, 100), "This is a description for $it") }
    private val _displayedItems: MutableStateFlow<List<Item>> = MutableStateFlow(_data)
    val displayedItems: StateFlow<List<Item>> = _displayedItems

    fun resetOrder() {
        _displayedItems.value = _data.filter { it in _displayedItems.value }
    }

    fun sortAlphabetically() {
        _displayedItems.value = _displayedItems.value.sortedBy { it.name }
    }

    fun sortByAmount() {
        _displayedItems.value = _displayedItems.value.sortedBy { it.amount }
    }

    fun addItem() {
        // Avoid duplicate items
        val remainingItems = _data.filter { it.name !in _displayedItems.value.map(Item::name) }
        if (remainingItems.isNotEmpty()) _displayedItems.value += remainingItems.first()
    }

    fun removeItem() {
        _displayedItems.value = _displayedItems.value.dropLast(1)
    }
}


val staticItems: List<Item> = List(20) { Item("Item $it", it, "Das ist eine Beschreibung: $it")}
    /*listOf(
    Item("Kartoffeln", 2, "Gold braune Kartoffeln aus der Grundwaldebene. Geerntet von den deutschesten Bauern!"),
    Item("Dosenmilch", 3, description = "Es ist nicht einfach nur Milch aus der Dose, sondern Dosenmilch!"),
    Item("Maggi", 50, "Maika macht das Maggi.")
)*/




@Composable
fun AnimatedOrderedListScreen(
    viewModel: AnimatedOrderedListViewModel,
    modifier: Modifier = Modifier,
) {
    val displayedItems by viewModel.displayedItems.collectAsStateWithLifecycle()

    ListAnimatedItemsExample(
        displayedItems,
        onAddItem = viewModel::addItem,
        onRemoveItem = viewModel::removeItem,
        resetOrder = viewModel::resetOrder,
        onSortAlphabetically = viewModel::sortAlphabetically,
        onSortByLength = viewModel::sortByAmount,
        modifier = modifier
    )
}

@Composable
private fun ListAnimatedItemsExample(
    data: List<Item>,
    modifier: Modifier = Modifier,
    onAddItem: () -> Unit = {},
    onRemoveItem: () -> Unit = {},
    resetOrder: () -> Unit = {},
    onSortAlphabetically: () -> Unit = {},
    onSortByLength: () -> Unit = {},
) {
    val canAddItem = data.size < 10
    val canRemoveItem = data.isNotEmpty()

    Scaffold(modifier) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Buttons that change the value of displayedItems.
            AddRemoveButtons(canAddItem, canRemoveItem, onAddItem, onRemoveItem)
            OrderButtons(resetOrder, onSortAlphabetically, onSortByLength)

            // List that displays the values of displayedItems.
            ListAnimatedItems(data)
        }
    }
}

@Composable
private fun AddRemoveButtons(
    canAddItem: Boolean,
    canRemoveItem: Boolean,
    onAddItem: () -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(enabled = canAddItem, onClick = onAddItem) {
            Text("Add Item")
        }
        Spacer(modifier = Modifier.padding(25.dp))
        Button(enabled = canRemoveItem, onClick = onRemoveItem) {
            Text("Delete Item")
        }
    }
}

@Composable
private fun OrderButtons(
    resetOrder: () -> Unit,
    orderAlphabetically: () -> Unit,
    orderByLength: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Reset", "Alphabetical", "Amount")

        SingleChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = {
                        Log.d("AnimatedOrderedList", "selectedIndex: $selectedIndex")
                        selectedIndex = index
                        when (options[selectedIndex]) {
                            "Reset" -> resetOrder()
                            "Alphabetical" -> orderAlphabetically()
                            "Amount" -> orderByLength()
                        }
                    },
                    selected = index == selectedIndex
                ) {
                    Text(label)
                }
            }
        }
    }
}

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

@Preview
@Composable
fun AnimatedOrderedListScreenPreview() {
    val viewModel = remember { AnimatedOrderedListViewModel() }
    AnimatedOrderedListScreen(viewModel)
}
