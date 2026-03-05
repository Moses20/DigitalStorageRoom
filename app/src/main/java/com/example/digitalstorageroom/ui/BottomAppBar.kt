package com.example.digitalstorageroom.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.BottomAppBar as MaterialBottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarDefaults.bottomAppBarFabColor
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.digitalstorageroom.R
import com.example.digitalstorageroom.ui.icons.Barcode
import com.example.digitalstorageroom.ui.icons.HomeStorage
import com.example.digitalstorageroom.ui.icons.Material
import androidx.navigation.compose.rememberNavController

@Composable
fun SongsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Songs Screen")
    }
}

@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Album Screen")
    }
}

@Composable
fun PlaylistScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Playlist Screen")
    }
}

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    STORAGE("storage", "Storage", Material.HomeStorage, "Storage"),
    CHECK("album", "Album", Icons.Default.Check, "Album"),
    EDIT("playlist", "Playlist", Icons.Default.Edit, "Playlist"),
}

object DestinationInit {
    val START = Destination.STORAGE
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.STORAGE -> SongsScreen()
                    Destination.CHECK -> AlbumScreen()
                    Destination.EDIT -> PlaylistScreen()
                }
            }
        }
    }
}


@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    selectedDestination: Int,
    selectDestination: (Int) -> Unit,
    navController: NavHostController = rememberNavController(),
    floatingActionButton: @Composable (() -> Unit)? = null
) {

   /* Surface(
        color = color,
        modifier = modifier
    ) {
        Box(
            Modifier.padding(0.dp),
            contentAlignment = Alignment.Center
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(0.5f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 50.dp,
                            topEnd = 50.dp,
                            bottomEnd = 50.dp,
                            bottomStart = 50.dp
                        )
                    )
                    .background(Color.Green)

                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
            Row(
                Modifier
                    .offset(y = (-20).dp)
                    .clip(CircleShape)
                    .border(5.dp, Color.Transparent)
                    .background(Color.LightGray)

                    ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {
                IconButton (

                    onClick = { println("Do nothing!") },
                    //border = BorderStroke(width = 5.dp, Color.Black)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Scan QR Code")
                }
            }
        }


    }*/


    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {

        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
            Destination.entries.forEachIndexed { index, destination ->
                NavigationBarItem(
                    selected = selectedDestination == index,
                    onClick = {
                        navController.navigate(route = destination.route)
                        selectDestination(index)
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.contentDescription
                        )
                    },
                    label = { Text(destination.label)}
                )
            }
        }

        ElevatedButton(
            onClick = {println("Ahhh")}
        ) {
            Icon(
                Material.Barcode,
                contentDescription = "Barcode"
            )

        }
    }



    /*MaterialBottomAppBar(
        actions = actions,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        },
    )*/

}

@Composable
fun CustomBottomAppBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    color: Color = Color.Transparent,
    actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = { /* do something */ }) {
            Icon(Icons.Filled.Check, contentDescription = "Localized description")
        }
        IconButton(onClick = { /* do something */ }) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Localized description",
            )
        }
    },
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
) {
    Surface(
        color = color,
        modifier = modifier
    ) {
        Box(
            Modifier.padding(0.dp),
            contentAlignment = Alignment.Center
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(0.5f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 50.dp,
                            topEnd = 50.dp,
                            bottomEnd = 50.dp,
                            bottomStart = 50.dp
                        )
                    )
                    .background(Color.Green)

                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
            Row(
                Modifier
                    .offset(y = (-20).dp)
                    .clip(CircleShape)
                    .border(5.dp, Color.Transparent)
                    .background(Color.LightGray)

                ,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {
                IconButton (

                    onClick = { println("Do nothing!") },
                    //border = BorderStroke(width = 5.dp, Color.Black)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Scan QR Code")
                }
            }
        }


    }
}
