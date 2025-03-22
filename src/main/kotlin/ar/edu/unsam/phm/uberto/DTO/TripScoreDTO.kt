package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.TripScore
import java.time.LocalDate

data class TripScoreDTO(
    val message: String,
    val scorePoints: Int,
    val date: String,
    val passengerId: Int,
    val passengerImg: String)


fun TripScore.toDTO() = TripScoreDTO(
    message = message,
    scorePoints = scorePoints,
    date = date.toString(),
    passengerId= passenger.userId,
    passengerImg = passenger.img)

