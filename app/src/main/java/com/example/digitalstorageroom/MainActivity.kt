package com.example.digitalstorageroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalstorageroom.ui.theme.DigitalStorageRoomTheme
import com.example.digitalstorageroom.item.view.ItemsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


val randomList = (1..20).map { Item("T_$it") }

class MainActivity : ComponentActivity() {
    val smartTv: SmartTvDevice = SmartTvDevice(
        "Samsung",
        "TV"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigitalStorageRoomTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting(name = "Android", "From Klaus")
                    //VolumeScreen(smartTv)
                    ItemsView()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {


}

@Composable
fun VolumeScreen(device: SmartTvDevice) {

    Column(
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "Volume: ${device.displayedVolume}")
        Row {
            Button(
                //onClick = { volume += 1 },
                onClick = device::increaseSpeakerVolume
            ) {
                Text(text = "+")
            }
            Button(
                onClick = device::decreaseSpeakerVolume
            ) {
                Text(text="-")
            }
        }
    }
}


class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int,
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int = fieldData

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Int
    ) {
        if (value in minValue..maxValue)
            fieldData = value
    }
}

open class SmartDevice(
    val name: String,
    val category: String
) {
    var deviceStatus = "online"
    open val deviceType = "undefined"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }
}

class SmartTvDevice(
    deviceName: String,
    deviceCategory: String,
) : SmartDevice(
    deviceName,
    deviceCategory
) {
    override val deviceType = "Smart TV"

    var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
    var displayedVolume by mutableIntStateOf(speakerVolume)

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

     fun increaseSpeakerVolume() {
        CoroutineScope(Dispatchers.IO).launch {
            sleep(1000)
            speakerVolume++
            displayedVolume = speakerVolume
            sleep(5000)
            println("Speaker volume increased to $speakerVolume.")
        }
    }

    fun decreaseSpeakerVolume() {
        speakerVolume--
        displayedVolume = speakerVolume
        println("Speaker volume decreased to $speakerVolume.")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}


data class Item(
    val title: String,
    val isbn: String = "111-111-111",
)

@Composable
fun ItemsOverview(vararg items: Item) = Column {
    items.forEach {
        ItemPreview(it)
    }
}

@Composable
fun ItemPreview(item: Item) = Text(
    text = item.title,
    fontSize = 10.sp,
    textAlign = TextAlign.Center
)


@Composable
fun Greeting(name: String, from: String, modifier: Modifier = Modifier) {
    Column (modifier) {
        Text(
            text = "Happy Birthday $name!",
            //modifier = modifier.padding(24.dp),
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DigitalStorageRoomTheme {
       //Greeting("Patrick", "From Klaus")
        VolumeScreen(SmartTvDevice(
            "Samsung",
            "TV"
        ))
    }
}