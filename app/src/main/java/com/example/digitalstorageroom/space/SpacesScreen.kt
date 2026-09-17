package com.example.digitalstorageroom.space

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
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
    onSpaceClick: (StorageSpace) -> Unit,
    onAddSpaceClick: () -> Unit,
    //viewModel: SpacesViewModel = hiltViewModel()
) {
    Surface() {
        SmallFloatingActionButton(onClick = onAddSpaceClick) {
            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_storage_space))
        }
        Spacer(modifier = modifier)
        SpaceContent(
            onSpaceClick = onSpaceClick,
            spaces = emptyList()
        )
    }
}


@Composable
fun SpaceContent(
    modifier: Modifier = Modifier,
    onSpaceClick: (StorageSpace) -> Unit,
    spaces: List<StorageSpace>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(spaces) { space ->
            SpaceCard(
                space = space,
                onSpaceClick = onSpaceClick
            )
        }
    }
}

@Preview
@Composable
private fun SpaceContentPreview() {
    Surface(
        Modifier.fillMaxSize()
    ) {
        SpaceContent(
            onSpaceClick = { Log.d("SpaceCard", "Space clicked") },
            spaces = listOf(
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
            )
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
        modifier = Modifier.fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        )

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
                    text = space.title
                )
                //Spacer(modifier = Modifier.)
                Icon(Icons.Filled.Store, contentDescription = space.title)
            }

        }
    }

}
