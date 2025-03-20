package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Trip
import java.time.LocalDateTime

class TripDTO(
    var userId: Int,
    var driverDTO: DriverDTO,
    var duration: Int,
    val numberPassengers: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String,

    ) {


}

fun Trip.toDTO() = TripDTO(
    userId = client.id,
    driverDTO = driver.toDTO(),
    duration = duration,
    numberPassengers = numberPassengers,
    date = date,
    origin = origin,
    destination = destination,

)