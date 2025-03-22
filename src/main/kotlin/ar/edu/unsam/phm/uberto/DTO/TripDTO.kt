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
    ) {


}

fun Trip.toDTO() = TripDTO(
    userId = client.userId,
    driverId = driver.userId,
    duration = duration,
    numberPassengers = numberPassengers,
    date = date,
    origin = origin,
    destination = destination,
)