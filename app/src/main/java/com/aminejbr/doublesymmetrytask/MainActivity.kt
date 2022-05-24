package com.aminejbr.doublesymmetrytask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aminejbr.doublesymmetrytask.presentation.music_list.MusicListScreen
import com.aminejbr.doublesymmetrytask.ui.theme.DoubleSymmetryTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoubleSymmetryTaskTheme {
                MusicListScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DoubleSymmetryTaskTheme {
        //Greeting("Android")
    }
}