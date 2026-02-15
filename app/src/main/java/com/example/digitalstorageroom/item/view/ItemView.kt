package com.example.digitalstorageroom.item.view

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.digitalstorageroom.ui.theme.DigitalStorageRoomTheme
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



@Preview
@Composable
fun MyAppPreview() {
    DigitalStorageRoomTheme {
        MyApp(Modifier.fillMaxSize())
    }
}


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
fun MyApp(modifier: Modifier = Modifier) {
    Surface(modifier) {
        ItemsView()
    }
}

@Preview
@Composable
fun ItemsView(modifier: Modifier = Modifier) {
    LazyColumn (modifier = modifier.padding(vertical = 4.dp)) {
        items(staticItems) { item ->
            ItemView(item, Modifier.animateItem())
        }
    }
}


@Composable
fun ItemView(item: Item, modifier: Modifier = Modifier) {
    var showItemDetailView by remember { mutableStateOf(false) }

    //SingleChoiceSegmentedButtonRow





    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column() {
            Row (modifier = Modifier.padding(12.dp),
                //verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier
                        //Important: there's no 'alignEnd' so weighted elements
                        // pushes away all elements without a weight.
                        .weight(1f)
                ) {
                    Text(
                        text = item.name)
                    Text(
                        text = item.amount.toString(),
                    )
                }
                ElevatedButton(
                    onClick = { showItemDetailView = !showItemDetailView },
                    ) {
                    Text(text = if(showItemDetailView) "Close" else "Open")
                }
            }
            if (showItemDetailView) {
                ItemDetailView(item)
            }
        }
    }
}

@Composable
fun ItemDetailView(item: Item) {
    Text(text = item.description)
}

@Preview
@Composable
fun AnimatedOrderedListScreenPreview() {
    val viewModel = remember { AnimatedOrderedListViewModel() }
    AnimatedOrderedListScreen(viewModel)
}
