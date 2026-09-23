package com.example.digitalstorageroom.space

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalstorageroom.R
import com.example.digitalstorageroom.space.data.StorageSpace
import com.example.digitalstorageroom.space.data.local.StorageSpaceType

@Composable
fun SpacesScreen(
    modifier: Modifier = Modifier,
   //onSpaceClick: (StorageSpace) -> Unit,
    // onAddSpaceClick: () -> Unit,
    //viewModel: SpacesViewModel = hiltViewModel()
) {

    val onSpaceClick: (StorageSpace) -> Unit = {
        println("You clicked on space \"${it.title}\"")
    }

    val onAddSpaceClick: () -> Unit = {
        println("TODO: you tried to add a new space!")
    }

    Box(modifier = Modifier
        //Make the SpaceContent clip the nav bar
        .statusBarsPadding()
        .fillMaxSize()) {
        SpaceContent(
            onSpaceClick = onSpaceClick,
            storageSpaces = hardcodedStorageSpaces()
        )
        //Spacer(modifier.padding.navigationBarsPadding())
        FloatingActionButton(
            onClick = onAddSpaceClick,
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),

        ) {
            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_storage_space))
        }
    }
}


@Composable
fun SpaceContent(
    modifier: Modifier = Modifier,
    onSpaceClick: (StorageSpace) -> Unit,
    storageSpaces: List<StorageSpace>,
) {
    val navBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = navBarHeight + 80.dp // Navigation bar inset + space for FAB
        )
    ) {
        items(storageSpaces) { storageSpace ->
            SpaceCard(
                space = storageSpace,
                onSpaceClick = onSpaceClick
            )
        }
    }
}

fun hardcodedStorageSpaces() : List<StorageSpace> = listOf(
    StorageSpace(
        "1",
        "Refrigerator kitchen",
        StorageSpaceType.REFRIGERATOR
    ),
    StorageSpace(
        "2",
        "Freezer kitchen",
        StorageSpaceType.FREEZER
    ),
    StorageSpace(
        "3",
        "Refrigerator cellar",
        StorageSpaceType.REFRIGERATOR
    ),
    StorageSpace(
        "4",
        "Storage cellar",
        StorageSpaceType.NORMAL
    ),
    StorageSpace(
        "5",
        "Storage cellar",
        StorageSpaceType.NORMAL
    ),
    StorageSpace(
        "6",
        "Storage cellar",
        StorageSpaceType.NORMAL
    ),
    StorageSpace(
        "7",
        "Storage cellar",
        StorageSpaceType.NORMAL
    ),
)

@Preview
@Composable
private fun SpaceContentPreview() {
    Surface(
        Modifier.fillMaxSize()
    ) {
        SpaceContent(
            onSpaceClick = { Log.d("SpaceCard", "Space clicked") },
            storageSpaces = hardcodedStorageSpaces()
        )

    }
}

@Composable
fun SpaceCard(
    modifier: Modifier = Modifier,
    space: StorageSpace,
    onSpaceClick: (StorageSpace) -> Unit
) {
    val color = Color(
            when (space.type) {
                StorageSpaceType.FREEZER -> 0xA00000ff
                StorageSpaceType.REFRIGERATOR ->  0x200000ff
                StorageSpaceType.NORMAL ->  0xA0ff0000
                else -> 0
            }
        )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        ),
        onClick = { onSpaceClick(space) }

    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .height(96.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    text = space.title,
                    color = Color.Black
                )
                //Spacer(modifier = Modifier.)
                Icon(Icons.Filled.Store, contentDescription = space.title)
            }

        }
    }

}
