package com.test.mycompose

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.test.mycompose.ocr.TextAnalyzer
import com.test.mycompose.ui.theme.MyComposeTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme { // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
                    TestView("Second Activity", ActivityFlog.THIRD)
                }
            }
        }
        TextAnalyzer(this).apply {
            analyze(BitmapFactory.decodeResource(resources, R.drawable.test))
            analyze(BitmapFactory.decodeResource(resources, R.drawable.test1))
            analyze(BitmapFactory.decodeResource(resources, R.drawable.test3))
            analyze(BitmapFactory.decodeResource(resources, R.drawable.test4))
        }
    }
}
