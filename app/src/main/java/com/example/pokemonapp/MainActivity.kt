package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Card

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



