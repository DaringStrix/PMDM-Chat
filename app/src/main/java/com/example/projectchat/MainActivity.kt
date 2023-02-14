package com.example.projectchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.projectchat.ui.theme.ProjectChatTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Principal("Mon Talbo")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    ProjectChatTheme {
        Principal("Mon Talbo")
    }
}

@Composable
fun Principal(user: String) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(title = {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_compass),
                    contentDescription = "Icono scafold"
                )
                Text(user)
            })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(messages) { message -> OnClickChat(message) }
            }
        }
    }
}


@Composable
fun OnClickChat(message: MyMessage) {

    var clickado by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = onRemoto(
            message,
            Alignment.Start,
            Alignment.End
        ) as Alignment.Horizontal
    ) {
        Card(
            Modifier
                .padding(10.dp)
                .clickable { clickado = !clickado },
            backgroundColor = onRemoto(
                message = message,
                MaterialTheme.colors.primaryVariant,
                MaterialTheme.colors.secondary
            ) as Color,
            elevation = 8.dp,
            shape = onRemoto(message, MessageShape(true), MessageShape(false)) as Shape

        ) {
            Column(
                Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = message.body)
                Spacer(Modifier.height(8.dp))
                if (clickado) {
                    Text(
                        text = message.fecha
                    )
                }
            }
        }
    }
}


data class MyMessage(
    val usuario: String,
    val body: String,
    val fecha: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))

)

val messages: List<MyMessage> = listOf(
    MyMessage(
        usuario = "Remoto",
        body = "You can think of Modifiers as implementations of the decorator pattern"
    ),
    MyMessage(
        usuario = "Local",
        body = "Yeah, or you can think about anything else"
    ),
    MyMessage(
        usuario = "Remoto",
        body = "Nah"
    ),
    MyMessage(
        usuario = "Remoto",
        body = "Don't think so"
    ),
)

fun onRemoto(message: MyMessage, condicion1: Any, condicion2: Any) =
    if (message.usuario == "Remoto") condicion1 else condicion2

class MessageShape(private val remoto: Boolean) : Shape {
    private val cornerShape = 50f
    private val arrowWidth = 30f
    private val arrowHeight = 40f

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                reset()
                // Move to x = cornerShape (16), y = 0
                moveTo(cornerShape, 0f)

                // top line left ot right
                lineTo(if (remoto) size.width else size.width + arrowWidth, 0f)

                // top right arc
                if (!remoto) {
                    arcTo(
                        rect = Rect(
                            offset = Offset(size.width + arrowWidth, 0f),
                            size = Size(10f, 10f)
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 0f,
                        forceMoveTo = false
                    )
                } else {
                    arcTo(
                        rect = Rect(
                            offset = Offset(size.width - cornerShape, 0f),
                            size = Size(cornerShape, cornerShape)
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }

                //slanting line
                lineTo(size.width, if (remoto) cornerShape else arrowHeight)

                // bottom
                lineTo(size.width, size.height - cornerShape)

                // the bottom left arc
                arcTo(
                    rect = Rect(
                        offset = Offset(size.width - cornerShape, size.height - cornerShape),
                        size = Size(cornerShape, cornerShape)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                // bottom line from left to right
                lineTo(size.width - cornerShape, size.height)

                // bottom right arc
                arcTo(
                    rect = Rect(
                        offset = Offset(0f, size.height - cornerShape),
                        size = Size(cornerShape, cornerShape)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )

                //bottom to top line on right
                lineTo(0f, arrowHeight)

                //top right arc
                if (remoto) {
                    arcTo(
                        rect = Rect(
                            offset = Offset(-arrowWidth * 1.5f, 0f),
                            size = Size(10f, 10f)
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 0f,
                        forceMoveTo = false
                    )
                } else {
                    arcTo(
                        rect = Rect(
                            offset = Offset(0f, 0f),
                            size = Size(cornerShape, cornerShape)
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
                }
                close()
            }
        )
    }
}