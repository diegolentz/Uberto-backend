package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import java.time.LocalDateTime

class TripDTO(
    val id: Long,
    var userId: Long,
    var driverId: String,
    var duration: Int,
    val numberPassengers: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String,
    val price: Double,
    val driverName: String?,
    val passengerName: String?,
    val imgPassenger: String?,
    val imgDriver: String?,
    val scored: Boolean?
) {
    fun toTrip(tripDTO: TripDTO): Trip = Trip().apply {
        duration = tripDTO.duration
        numberPassengers = tripDTO.numberPassengers
        date = tripDTO.date
        origin = tripDTO.origin
        destination = tripDTO.destination
    }
}


fun Trip.toDTO() : TripDTO{
    val clientId = requireNotNull(client.id) { "Client ID is null" }
    val driverId = requireNotNull(driverMongo.id) { "Driver ID is null" }
    val id = requireNotNull(id) { "ID is null" }

    return TripDTO(
    userId = clientId,
    driverId = driverId,
    driverName = driverMongo.firstName + " " + driverMongo.lastName,
    passengerName = client.firstName + " " + client.lastName,
    duration = duration,
    numberPassengers = numberPassengers,
    date = date,
    origin = origin,
    destination = destination,
    price = driverMongo.fee(duration, numberPassengers),
    id = id,
    imgPassenger = client.img,
    imgDriver = driverMongo.img,
    scored = this.scored()
)
}

fun Trip.scoreToDTO(userId: Long?) = TripScoreDTO(
    tripId = id!!,
    message = score!!.message,
    scorePoints = score!!.scorePoints,
    date = date.toString(),
    passengerName= client.firstName + "" + client.lastName,
    driverName = driverMongo.firstName + "" + driverMongo.lastName,
    avatarUrlPassenger = client.img,
    avatarUrlDriver = driverMongo.img,
    delete = if (userId != null) canDeleteScore(userId) else false
)

data class FormTripDTO(
    val origin: String,
    val destination: String,
    val numberPassengers: Int,
    val name: String,
    val userId: Long
){}