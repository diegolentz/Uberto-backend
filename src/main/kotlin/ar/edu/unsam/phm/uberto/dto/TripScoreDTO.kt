package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.TripScore
import java.time.LocalDateTime

data class TripScoreDTOMongo(
    val tripId: Long ,
    val avatarUrlPassenger: String,
    val date: LocalDateTime,
    val passengerName: String,
    val message: String,
    val scorePoints: Int,
)

fun Trip.toTripScoreDTOMongo() = TripScoreDTOMongo(
    tripId = id!!,
    avatarUrlPassenger = client.img,
    date = date,
    passengerName = client.firstName + client.lastName,
    message = score!!.message,
    scorePoints = score!!.scorePoints
)
