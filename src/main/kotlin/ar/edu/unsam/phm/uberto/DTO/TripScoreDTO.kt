package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.TripScore
import java.time.LocalDate

class TripScoreDTO(
    message: String,
    scorePoints: Int,
    date: LocalDate,
    passenger: Passenger)


fun TripScore.toDTO() = TripScoreDTO(
    message = message,
    scorePoints = scorePoints,
    date = date,
    passenger= passenger)