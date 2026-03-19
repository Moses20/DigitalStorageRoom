package com.example.digitalstorageroom.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalstorageroom.item.ItemsScreen
import com.example.digitalstorageroom.ui.icons.Barcode
import com.example.digitalstorageroom.ui.icons.HomeStorage
import com.example.digitalstorageroom.ui.icons.Material

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

//TODO Change this, this is to limiting, what if we want a camera route

enum class  Route {
    STORAGE,
    ALBUM,
    PLAYLIST,
    ITEMS,
    CAMERA,
    EDIT;
}

enum class Destination(
    val route: Route,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    STORAGE(Route.STORAGE, "Storage", Material.HomeStorage, "Storage"),

    //CHECK("album", "Album", Icons.Default.Check, "Album"),
    EDIT(Route.EDIT, "Playlist", Icons.Default.Edit, "Playlist"),
}


object DestinationInit {
    val START = Destination.STORAGE
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    routes: Map<Route, @Composable (() -> Unit)>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route.name
    ) {
        Route.entries.forEach { route ->
            composable(route.name) {
                when (route) {
                    Route.STORAGE -> SongsScreen()
                    //Destination.CHECK -> AlbumScreen()
                    Route.EDIT -> ItemsScreen()
                    Route.CAMERA -> routes[Route.CAMERA]?.invoke()
                    else -> {
                        Log.e("ERROR", "Route not found: $route")
                    }
                }
                //TODO: When can be replaced by
                // routes[route]?.invoke()
            }
        }
    }
}

//TODO: We need a way to get the height
val PARENT_BOX_HEIGHT = 104.dp
val BUTTON_HEIGHT = 70.dp
val BUTTON_CUTOUT_HEIGHT = BUTTON_HEIGHT + 40.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    selectedDestination: Int,
    selectDestination: (Int) -> Unit,
    navController: NavHostController = rememberNavController(),
    barCodeButtonOnClick: () -> Unit,
    onTakePhotoClick: () -> Unit = {},
    floatingActionButton: @Composable (() -> Unit)? = null
) {

    // calculate the offset, so that the half of the circular button is on the top edge of the BoxWithContstraints
    val yOffsetButton = (-((PARENT_BOX_HEIGHT - BUTTON_HEIGHT) / 2) - (BUTTON_HEIGHT / 2))

    Box(
        contentAlignment = Alignment.Center
    ) {

        NavigationBar(
            windowInsets = NavigationBarDefaults.windowInsets,

            //Cutout: https://blog.jakelee.co.uk/how-to-make-cutouts-in-jetpack-compose-boxes/
            modifier = Modifier
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()
                    val yOffsetButtonCutout =
                        ((size.height - BUTTON_CUTOUT_HEIGHT.value) / 2 + (BUTTON_CUTOUT_HEIGHT.value / 2))
                    drawCircle(
                        color = Color(0xFFFFFFFF),
                        center = size.center.minus(Offset(x = 0f, y = yOffsetButtonCutout)),
                        radius = BUTTON_CUTOUT_HEIGHT.value,
                        blendMode = BlendMode.DstOut
                    )
                }
        ) {
            Destination.entries.forEachIndexed { index, destination ->
                NavigationBarItem(
                    selected = selectedDestination == index,
                    onClick = {
                        navController.navigate(route = destination.route.name)
                        selectDestination(index)
                    },
                    icon = {
                        Icon(
                            destination.icon,
                            contentDescription = destination.contentDescription
                        )
                    },
                    label = { Text(destination.label) }
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(y = yOffsetButton)
                .padding(2.dp)
                .width(BUTTON_HEIGHT)
                .height(BUTTON_HEIGHT)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    //If the camera screen is already open we want to use this button to scan an item
                    if (navController.currentDestination?.route == Route.CAMERA.name) onTakePhotoClick() else barCodeButtonOnClick()
                },
            contentAlignment = Alignment.Center
            //onClick = {println("Ahhh")}
        ) {
            Icon(
                Material.Barcode,
                contentDescription = "Barcode",
            )

        }
    }
}


//TODO: THIS IS UNUSED
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
                    .background(Color.Green),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
            Row(
                Modifier
                    .offset(y = (-20).dp)
                    .clip(CircleShape)
                    .border(5.dp, Color.Transparent)
                    .background(Color.LightGray),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {
                IconButton(

                    onClick = { println("Do nothing!") },
                    //border = BorderStroke(width = 5.dp, Color.Black)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Scan QR Code")
                }
            }
        }


    }
}
