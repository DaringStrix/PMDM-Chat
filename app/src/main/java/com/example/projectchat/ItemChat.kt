package com.example.projectchat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var acc: String? = null

@Composable
fun ItemChat(message: MyMessage, currentUser: String) {

    var clickado by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = if (isRemoto(message)) Alignment.Start else Alignment.End
    ) {
        Card(
            Modifier
                .padding(10.dp)
                .clickable { clickado = !clickado },
            backgroundColor = if(isRemoto(message = message))MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
            elevation = 8.dp,
            shape = if (currentUser == acc) RoundedCornerShape(20f) else MessageShape(isRemoto(message))

        ) {
            Column(
                Modifier
                    .padding(15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = message.body)
                Spacer(Modifier.height(8.dp))
                if (clickado) {
                    val horaActual: Int = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm")))

                    Text(text =  "Hace "+ (horaActual-message.hora) + " minutos")

                }
            }
        }
    }
    acc = currentUser
}


fun isRemoto(message: MyMessage) = (message.usuario == "Remoto")

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