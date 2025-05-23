package com.example.rps15game  // Your package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RPSGameUI()
        }
    }
}

@Composable
fun RPSGameUI() {
    val gestures = listOf(
        "✊ Rock", "🔫 Gun", "⚡ Lightning", "😈 Devil", "🐉 Dragon",
        "💧 Water", "🌬️ Air", "📄 Paper", "🧽 Sponge", "🐺 Wolf",
        "🌳 Tree", "🧑 Human", "🐍 Snake", "✂️ Scissors", "🔥 Fire"
    )

    val rules = mapOf(
        "✊ Rock" to listOf("🔥 Fire", "✂️ Scissors", "🐍 Snake", "🧑 Human", "🐺 Wolf", "🧽 Sponge", "🌳 Tree"),
        "🔫 Gun" to listOf("✊ Rock", "🔥 Fire", "✂️ Scissors", "🐍 Snake", "🧑 Human", "🐺 Wolf", "🧽 Sponge"),
        "⚡ Lightning" to listOf("🔫 Gun", "✊ Rock", "🔥 Fire", "✂️ Scissors", "🐍 Snake", "🧑 Human", "🐺 Wolf"),
        "😈 Devil" to listOf("⚡ Lightning", "🔫 Gun", "✊ Rock", "🔥 Fire", "✂️ Scissors", "🐍 Snake", "🧑 Human"),
        "🐉 Dragon" to listOf("😈 Devil", "⚡ Lightning", "🔫 Gun", "✊ Rock", "🔥 Fire", "✂️ Scissors", "🐍 Snake"),
        "💧 Water" to listOf("🐉 Dragon", "😈 Devil", "⚡ Lightning", "🔫 Gun", "✊ Rock", "🔥 Fire", "✂️ Scissors"),
        "🌬️ Air" to listOf("💧 Water", "🐉 Dragon", "😈 Devil", "⚡ Lightning", "🔫 Gun", "✊ Rock", "🔥 Fire"),
        "📄 Paper" to listOf("🌬️ Air", "💧 Water", "🐉 Dragon", "😈 Devil", "⚡ Lightning", "🔫 Gun", "✊ Rock"),
        "🧽 Sponge" to listOf("📄 Paper", "🌬️ Air", "💧 Water", "🐉 Dragon", "😈 Devil", "⚡ Lightning", "🔫 Gun"),
        "🐺 Wolf" to listOf("🧽 Sponge", "📄 Paper", "🌬️ Air", "💧 Water", "🐉 Dragon", "😈 Devil", "⚡ Lightning"),
        "🌳 Tree" to listOf("🐺 Wolf", "🧽 Sponge", "📄 Paper", "🌬️ Air", "💧 Water", "🐉 Dragon", "😈 Devil"),
        "🧑 Human" to listOf("🌳 Tree", "🐺 Wolf", "🧽 Sponge", "📄 Paper", "🌬️ Air", "💧 Water", "🐉 Dragon"),
        "🐍 Snake" to listOf("🧑 Human", "🌳 Tree", "🐺 Wolf", "🧽 Sponge", "📄 Paper", "🌬️ Air", "💧 Water"),
        "✂️ Scissors" to listOf("🐍 Snake", "🧑 Human", "🌳 Tree", "🐺 Wolf", "🧽 Sponge", "📄 Paper", "🌬️ Air"),
        "🔥 Fire" to listOf("✂️ Scissors", "🐍 Snake", "🧑 Human", "🌳 Tree", "🐺 Wolf", "🧽 Sponge", "📄 Paper")
    )

    var playerChoice by remember { mutableStateOf<String?>(null) }
    var computerChoice by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<String?>(null) }
    var playerWins by remember { mutableStateOf(0) }
    var computerWins by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rock-Paper-Scissors 15",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid Layout for All Gestures
        Column {
            for (row in gestures.chunked(3)) { // 3 buttons per row
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    row.forEach { gesture ->
                        Button(
                            onClick = {
                                playerChoice = gesture
                                computerChoice = gestures[Random.nextInt(gestures.size)]
                                result = determineWinner(playerChoice!!, computerChoice!!, rules)
                                if (result == "Player Wins!") playerWins++ else if (result == "Computer Wins!") computerWins++
                            },
                            modifier = Modifier.padding(4.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                        ) {
                            Text(text = gesture, fontSize = 18.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Computer: ${computerChoice ?: "?"}",
            fontSize = 20.sp,
            color = Color.White
        )
        Text(
            text = "Player: ${playerChoice ?: "?"}",
            fontSize = 20.sp,
            color = Color.White
        )

        Text(
            text = "Result: ${result ?: "Choose a move"}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Player Wins: $playerWins | Computer Wins: $computerWins", fontSize = 20.sp, color = Color.Cyan)

        Button(
            onClick = { playerWins = 0; computerWins = 0 },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text("Reset Game", fontSize = 18.sp, color = Color.White)
        }
    }
}

fun determineWinner(player: String, computer: String, rules: Map<String, List<String>>): String {
    return when {
        player == computer -> "It's a tie!"
        rules[player]?.contains(computer) == true -> "Player Wins!"
        else -> "Computer Wins!"
    }
}
