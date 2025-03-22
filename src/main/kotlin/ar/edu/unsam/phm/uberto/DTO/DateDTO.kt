package ar.edu.unsam.phm.uberto.DTO

import java.time.LocalDateTime

data class DateDTO(
    val origin: String,
    val destination: String,
    var date: LocalDateTime
)