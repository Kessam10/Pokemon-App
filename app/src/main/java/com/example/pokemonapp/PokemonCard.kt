package com.example.pokemonapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun PokemonCard(item: CardItems) {
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
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
                    .constrainAs(image) {
                        end.linkTo(parent.end)
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
                PokemonCard(item)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PokemonCardPreview() {
    val sampleItems = listOf(
        CardItems("Bulbasaur", "grass", 65, 65, "bulbasaur"),
        CardItems("Charmander", "fire", 60, 50, "charmander")
    )
    Recycle(cardItems = sampleItems)
}
