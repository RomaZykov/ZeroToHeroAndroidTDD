package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VisibleTextWithButton()
                }
            }
        }
    }
}

@Composable
fun VisibleTextWithButton() {
    var counter by rememberSaveable { mutableStateOf("0") }
    val count = Count.Base(counter.toInt(), 1)
    Text(counter)
    Button(
        onClick = {
            val result = count.increment()
            counter = result.toString()
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text("increment")
    }
}