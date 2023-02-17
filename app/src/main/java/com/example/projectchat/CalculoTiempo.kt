package com.example.projectchat

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun calcularTiempo(originTime: LocalTime): String {
    val diferenciaHoras: Int
    val diferenciaMinutos: Int
    val diferencia: String


    val horaActual: LocalTime = LocalTime.now()

    diferenciaHoras = Integer.parseInt(
        horaActual.minusHours(originTime.hour.toLong()).format(
            DateTimeFormatter.ofPattern("HH")
        )
    )
    diferenciaMinutos = Integer.parseInt(
        horaActual.minusMinutes(originTime.minute.toLong()).format(
            DateTimeFormatter.ofPattern("mm")
        )
    )


    if (diferenciaHoras > 0) {
        diferencia = "$diferenciaHoras horas y $diferenciaMinutos minutos"
    } else {
        if (diferenciaMinutos > 0) {
            diferencia = "$diferenciaMinutos minutos"
        } else diferencia = "0 minutos"
    }

    return diferencia
}
