package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

//instancio para que no de error
class Travel(
    val time: Int,
    val numberPassengers: Int,
    val date: LocalDate,
    val origin: String,
    val destination: String,
    val user: User
)
