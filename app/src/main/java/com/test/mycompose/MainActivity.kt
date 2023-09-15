package com.test.mycompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.mycompose.split.startActivity
import com.test.mycompose.ui.theme.MyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnBoarding) {
        OnBoardingScreen(onContinueClick = { shouldShowOnBoarding = false })
    } else {
        Greetings(onBackClick = { shouldShowOnBoarding = true })
    }
}

@Composable
fun Greetings(onBackClick: () -> Unit = {}, modifier: Modifier = Modifier, names: List<String> = List(1000) { "$it" }) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onBackClick) {
                Text("back")
            }
        }
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.Center) {
            items(items = names) { name ->
                Greeting(name = name)
            }
        }
    }
}


@Composable
fun Greeting(name: String, context: Context = LocalContext.current) {
    var expanded by remember { mutableStateOf(false) }
    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = Modifier
            .padding(top = 12.dp, end = 12.dp)
            .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(12.dp)) {
                Text(text = "Hello, ")
                Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold))
                if (expanded) {
                    Text(text = ("Composem ipsum color sit lazy, " + "padding theme elit, sed do bouncy. ").repeat(4))
                }
            }
            ElevatedButton(onClick = {
                expanded = !expanded
                if (expanded) {
                    context.startActivity(SecondActivity::class.java)
                } else {
                    context.startActivity(FourActivity::class.java, true)
                }
            }) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnBoardingScreen(onContinueClick: () -> Unit = {}, modifier: Modifier = Modifier) { // TODO: This state should be hoisted
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Welcome to the My test!")
        Button(modifier = Modifier.padding(vertical = 24.dp), onClick = onContinueClick) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 150)
@Composable
fun OnBoardingPreview() {
    MyComposeTheme {
        OnBoardingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeTheme {
        Greeting("Android")
    }
}