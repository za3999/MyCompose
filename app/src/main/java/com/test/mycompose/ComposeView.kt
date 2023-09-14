package com.test.mycompose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.test.mycompose.ui.theme.MyComposeTheme

@Composable
fun TestView(name: String, toSecondActivity: Boolean = true, context: Context = LocalContext.current) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            ContextCompat.startActivity(context, Intent(context, if (toSecondActivity) SecondActivity::class.java else ExpandedActivity::class.java), null)
        }) {
            Text(text = "Hello $name!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestView() {
    MyComposeTheme {
        TestView("Android")
    }
}