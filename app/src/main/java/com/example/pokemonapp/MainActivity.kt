package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import java.io.File
import java.io.InputStreamReader
import java.io.Reader

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonAppTheme {
                val cardItems = remember { mutableStateListOf<CardItems>() } // State list for cards
                ReadData(cardItems)  // Load data into the list
                Recycle(cardItems)   // Pass the list to the Recycler
            }
        }
    }

    fun ReadData(cardItems: MutableList<CardItems>) {
        val assetManager = resources.assets
        val inputStream = assetManager.open("data.txt")
        val bufferedReader = inputStream.bufferedReader()

        // Read each line from data.txt and convert to CardItems
        bufferedReader.forEachLine { line ->
            val tokens = line.split(",")
            if (tokens.size == 5) {
                val cardItem = CardItems(
                    name = tokens[0],
                    type = tokens[1],
                    spAttack = tokens[2].toIntOrNull(),
                    spDefense = tokens[3].toIntOrNull(),
                    icon = tokens[4]
                )
                cardItems.add(cardItem) // Add to the list
            }
        }
    }
}
@Composable
fun Card(item: CardItems) {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .background(
                color = when (item.type) {
                    "grass" -> colorResource(R.color.grassCardBg)
                    "fire" -> colorResource(R.color.fireCardBg)
                    else -> colorResource(R.color.waterCardBg)
                },
                shape = RoundedCornerShape(15.dp)
            )
            .padding(20.dp)
    ) {
        ConstraintLayout {
            val (name, type, attack, defence, image, atkPower, defPower) = createRefs()

            Text(item.name ?: "Unknown", color = Color.White, fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 7.dp)
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                    }
            )
            Box(modifier = Modifier
                .background(
                    color = when (item.type) {
                        "grass" -> colorResource(R.color.grassTypeBg)
                        "fire" -> colorResource(R.color.fireTypeBg)
                        else -> colorResource(R.color.waterTypeBg)
                    },
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(5.dp)
                .constrainAs(type) {
                    top.linkTo(name.bottom)
                }
            ) {
                Text(text = item.type ?: "Unknown", color = Color.White, fontSize = 16.sp)
            }

            Text("Attack:", fontSize = 18.sp, color = Color.White, modifier = Modifier
                .padding(start = 15.dp, top = 10.dp)
                .constrainAs(attack) {
                    start.linkTo(type.end)
                    top.linkTo(name.bottom)
                    bottom.linkTo(type.top)
                })

            Text("Defence:", fontSize = 18.sp, color = Color.White, modifier = Modifier
                .padding(start = 15.dp, top = 10.dp)
                .constrainAs(defence) {
                    start.linkTo(type.end)
                    top.linkTo(attack.bottom)
                })

            Text(item.spAttack?.toString() ?: "0", fontSize = 18.sp, color = Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp, top = 10.dp)
                    .constrainAs(atkPower) {
                        start.linkTo(attack.end)
                        top.linkTo(name.bottom)
                        bottom.linkTo(type.top)
                    })

            Text(item.spDefense?.toString() ?: "0", fontSize = 18.sp, color = Color.Black,
                modifier = Modifier
                    .padding(start = 5.dp, top = 10.dp)
                    .constrainAs(defPower) {
                        start.linkTo(defence.end)
                        top.linkTo(attack.bottom)
                    })

            // Box for Image at the end
            Box(
                modifier = Modifier
                    .padding(start = 60.dp)
                    .constrainAs(image) {
                        start.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .width(110.dp)
                    .height(110.dp)
                    .background(
                        shape = CircleShape,
                        color = when (item.type) {
                            "grass" -> colorResource(R.color.grassImageBG)
                            "fire" -> colorResource(R.color.fireImageBG)
                            else -> colorResource(R.color.waterImageBg)
                        }
                    )
            ) {
                val painter = painterResource(
                    id = when (item.icon) {
                        "bulbasaur" -> R.drawable.bulbasaur
                        "ivysaur" -> R.drawable.ivysaur
                        "venusaur" -> R.drawable.venusaur
                        "charmander" -> R.drawable.charmander
                        "charmeleon" -> R.drawable.charmeleon
                        "charizard" -> R.drawable.charizard
                        "squirtle" -> R.drawable.squirtle
                        "wartortle" -> R.drawable.wartortle
                        "blastoise" -> R.drawable.blastoise
                        else -> R.drawable.ivysaur // Replace with a placeholder image
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = item.name,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Recycle(cardItems: List<CardItems>) {
    if (cardItems.isEmpty()) {
        // Show loading or empty state
        Text(text = "Loading...", color = Color.White)
    } else {
        LazyColumn {
            items(cardItems.size) { index ->
                val item = cardItems[index]
                Card(item)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    val sampleItems = listOf(
        CardItems("Bulbasaur", "grass", 65, 65, "bulbasaur"),
        CardItems("Charmander", "fire", 60, 50, "charmander")
    )
    Recycle(cardItems = sampleItems)
}



