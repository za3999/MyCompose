package com.test.mycompose

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.test.mycompose.split.startActivity
import com.test.mycompose.ui.theme.MyComposeTheme

enum class ActivityFlog {
    SECOND,
    THIRD,
    FOUR,
    EXPANDED,
    FINISH
}


@Composable
fun TestView(name: String, toActivity: ActivityFlog = ActivityFlog.SECOND, clearOthers: Boolean = false, context: Context = LocalContext.current) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            if (toActivity == ActivityFlog.FINISH) {
                (context as Activity).finish()
                return@Button
            }
            var toActivity = when (toActivity) {
                ActivityFlog.SECOND -> SecondActivity::class.java
                ActivityFlog.THIRD -> ThirdActivity::class.java
                ActivityFlog.FOUR -> FourActivity::class.java
                ActivityFlog.EXPANDED -> ExpandedActivity::class.java
                else -> PlaceholderActivity::class.java
            }
            context.startActivity(toActivity, clearOthers)
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