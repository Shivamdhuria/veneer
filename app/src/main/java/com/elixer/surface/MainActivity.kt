package com.elixer.surface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elixer.surface.ui.theme.SurfaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurfaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun CircleShape() {
    ExampleBox(shape = CircleShape)
}

@Composable
fun ExampleBox(shape: Shape) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(shape)
//                .background(Color.Red)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFF5B5B5B),
                            Color(0xFFC5C5C5),
                            Color(0xFFF5F5F5),
                            Color(0xFF5B5B5B),
                            Color(0xFFC5C5C5),
                            Color(0xFFF5F5F5),
                            Color(0xFF5B5B5B),
                            Color(0xFFC5C5C5),
                            Color(0xFFF5F5F5),
                        )
                    )
                )

        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SurfaceTheme {
        Greeting("Android")
        CircleShape()
    }
}