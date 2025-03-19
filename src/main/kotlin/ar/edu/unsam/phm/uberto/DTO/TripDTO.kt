package ar.edu.unsam.phm.uberto.DTO

import ar.edu.unsam.phm.uberto.model.Trip
import java.time.LocalDate
import java.util.*

class TripDTO(
    var userId: Int,
    var driverDTO: DriverDTO,
    var duration: Double,
    val numberPassengers: Int,
    val date: LocalDate,
    val origin: String,
    val destination: String,

    ) {


}

fun Trip.toDTO() = TripDTO(
    userId = client.id,
    driverDTO = DriverDTO(),
    duration = duration,
    numberPassengers = numberPassengers,
    date = date,
    origin = origin,
    destination = destination,

)