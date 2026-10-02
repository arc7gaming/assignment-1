package com.example.rapidrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rapidrecall.ui.theme.RapidRecallTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screen() {
    START,
    GAME,
    FEEDBACK,
    SUMMARY
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RapidRecallTheme {
                rapidRecall()
            }
        }
    }
}

@Preview
@Composable
fun rapidRecall(
    navController: NavHostController = rememberNavController()
) {
    val game = remember { Game() }

    var sequence by remember { mutableStateOf(listOf<Int>()) }
    var lengthInput by remember { mutableStateOf("") }
    var currentGuess by remember { mutableStateOf("") }
    var currentAttempt by remember { mutableStateOf(Attempt()) }

    Scaffold() {
        innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.START.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.START.name) {
                startScreen(
                    lengthInput,
                    onLengthChange = { newLength -> lengthInput = newLength },
                    onStartClicked = {
                        val length = lengthInput.toInt()
                        val newSequence = Sequence(length)
                        newSequence.createSequence()
                        sequence = newSequence.getSequence()
                        navController.navigate(Screen.GAME.name)
                    },
                    onSummaryClicked = { navController.navigate(Screen.SUMMARY.name) },
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )
            }
            composable(route = Screen.GAME.name) {
                showSequence(
                    sequence,
                    onGuess = {
                        newGuess -> currentGuess = newGuess
                        val guess = currentGuess.map { it.digitToInt() }
                        currentAttempt = game.recordAttempt(sequence, guess)
                        navController.navigate(Screen.FEEDBACK.name)
                    },
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )
            }
            composable(route = Screen.FEEDBACK.name) {
                showFeedback(
                    currentAttempt,
                    onButtonClicked = { navController.navigate(Screen.START.name) },
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )
            }
            composable(route = Screen.SUMMARY.name) {
                showSummary(
                    game.displaySummary(),
                    onButtonClicked = { navController.navigate(Screen.START.name) },
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun startScreen(
    length: String,
    onLengthChange: (String) -> Unit,
    onStartClicked: () -> Unit,
    onSummaryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Choose a length")
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = length,
            onValueChange = { onLengthChange(it) },
            label = { Text("Enter length") },
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onStartClicked() }
            ) {
                Text("Start")
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onSummaryClicked() }
            ) {
                Text("View Summary")
            }
        }
    }
}

@Composable
fun showSequence(
    sequence: List<Int>,
    onGuess: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var sequenceIndex by remember { mutableStateOf(0) }

    if (sequenceIndex < sequence.size) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${sequence[sequenceIndex]}")
            }
            Row(
                modifier = modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { sequenceIndex++ }
                ) {
                    Text("Next")
                }
            }
        }
    }
    else {
        var userInput by remember { mutableStateOf("") }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it }
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { onGuess(userInput) }
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun showFeedback(
    feedback: Attempt,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sequence length: ${feedback.sequenceLength}")
        }
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Target sequence: ${feedback.targetSequence}")
        }
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("User input: ${feedback.userInput}")
        }
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (feedback.correct) {
                Text("Correct")
            }
            else {
                Text("Incorrect")
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onButtonClicked() }
            ) {
                Text("New Game")
            }
        }
    }
}

@Composable
fun showSummary(
    summary: Summary,
    onButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total attempts: ${summary.totalAttempts}")
        }
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total correct attempts: ${summary.totalCorrectAttempts}")
        }
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Accuracy: ${summary.accuracy * 100}%")
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onButtonClicked() }
            ) {
                Text("Back")
            }
        }
    }
}