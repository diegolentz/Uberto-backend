package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Trip
import java.time.LocalDateTime

class TripDTO( //este es sin confimar, no lleva tripscore
    var userId: Int,
    var driverId: Int,
    var duration: Int,
    val numberPassengers: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String,
    val price: Double,
    val driverName: String,
    val passengerName: String,
    ) {


}

fun Trip.toDTO() = TripDTO(
    userId = client.userId,
    driverId = driver.userId,
    driverName = driver.firstName + " " + driver.lastName,
    passengerName = client.firstName + " " + client.lastName,
    duration = duration,
    numberPassengers = numberPassengers,
    date = date,
    origin = origin,
    destination = destination,
    price = driver.fee(duration, numberPassengers),
)

fun Trip.scoreToDTO() = TripScoreDTO(
    message = score!!.message,
    scorePoints = score!!.scorePoints,
    date = date.toString(),
    passengerName= client.firstName + ' ' + client.lastName,
    driverName = driver.firstName + ' ' + driver.lastName,
    avatarUrlPassenger = client.img,
    avatarUrlDriver = driver.img
)

data class FormTripDTO(
    val userId: Int,
    val origin: String?,
    val destination: String?,
    val numberPassengers: Int?,
    val name: String?
){}