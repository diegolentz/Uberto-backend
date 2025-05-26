package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.TripScore
import java.time.LocalDateTime

data class TripScoreDTO(
    val tripId: Long,
    val message: String,
    val scorePoints: Int,
    val date: String,
    val passengerName: String,
    val driverName: String,
    val avatarUrlPassenger: String,
    val avatarUrlDriver: String,
    val delete: Boolean
)

data class TripScoreDTOMongo(
    val id: Long ,
    val avatarUrlPassenger: String,
    val date: LocalDateTime,
    val passengerName: String,
    val message: String,
    val scorePoints: Int,
)

fun Trip.toTripScoreDTOMongo() = TripScoreDTOMongo(
    id = score!!.id!!,
    avatarUrlPassenger = client.img,
    date = date,
    passengerName = client.firstName + client.lastName,
    message = score!!.message,
    scorePoints = score!!.scorePoints
)
