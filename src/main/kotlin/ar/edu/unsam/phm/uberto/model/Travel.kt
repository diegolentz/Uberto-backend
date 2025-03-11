package ar.edu.unsam.phm.uberto.model

import java.util.Date

//instancio para que no de error
class Travel(
    val time: Int,
    val numberPassengers: Int,
    val date: Date,
    val origin: String,
    val destination: String,
    val user: User
)
