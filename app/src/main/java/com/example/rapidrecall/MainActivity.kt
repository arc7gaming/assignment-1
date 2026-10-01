package com.example.rapidrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rapidrecall.ui.theme.RapidRecallTheme

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
fun rapidRecall() {
    startScreen(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
}

@Composable
fun startScreen(modifier: Modifier = Modifier) {
    var length by remember { mutableStateOf("") }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Choose a length")
        }
        Row(
            modifier = modifier.weight(1f).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = length,
                onValueChange = { length = it },
                label = { Text("Enter length") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun showSequence(modifier: Modifier = Modifier, length: Int) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("")
        }
    }
}