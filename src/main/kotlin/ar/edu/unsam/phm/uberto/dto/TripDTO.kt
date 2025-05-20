package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Trip
import java.time.LocalDateTime

//class TripDTO(
//    val id: Long,
//    var userId: Long,
//    var driverId: String,
//    var duration: Int,
//    val numberPassengers: Int,
//    val date: LocalDateTime,
//    val origin: String,
//    val destination: String,
//    val price: Double,
//    val driverName: String?,
//    val passengerName: String?,
//    val imgPassenger: String?,
//    val imgDriver: String?,
//    val scored: Boolean?
//) {
//    fun toTrip(tripDTO: TripDTO): Trip = Trip().apply {
//        duration = tripDTO.duration
//        numberPassengers = tripDTO.numberPassengers
//        date = tripDTO.date
//        origin = tripDTO.origin
//        destination = tripDTO.destination
//    }
//}
//
//fun Trip.toDTO() : TripDTO{
//    val clientId = requireNotNull(client.id) { "Client ID is null" }
//    val driverId = requireNotNull(driverId) { "Driver ID is null" }
//    val id = requireNotNull(id) { "ID is null" }
//
//    return TripDTO(
//    userId = clientId,
//    driverId = driverId,
//    driverName = driver.firstName + " " + driver.lastName,
//    passengerName = client.firstName + " " + client.lastName,
//    duration = duration,
//    numberPassengers = numberPassengers,
//    date = date,
//    origin = origin,
//    destination = destination,
//    price = driver.fee(duration, numberPassengers),
//    id = id,
//    imgPassenger = client.img,
//    imgDriver = driver.img,
//    scored = this.scored()
//)
//}

fun Trip.scoreToDTO(userId: Long?) = TripScoreDTO(
    tripId = id!!,
    message = score!!.message,
    scorePoints = score!!.scorePoints,
    date = date.toString(),
    passengerName= client.firstName + "" + client.lastName,
    driverName = driver.firstName + "" + driver.lastName,
    avatarUrlPassenger = client.img,
    avatarUrlDriver = driver.img,
    delete = if (userId != null) canDeleteScore(userId) else false
)

data class FormTripDTO(
    val origin: String,
    val destination: String,
    val numberPassengers: Int,
    val name: String,
    val userId: Long
){}

fun matchDriverFromTrip(drivers: List<Driver>, trips: List<Trip>): List<Trip>{
    trips.forEach{ trip ->
        trip.driver  = drivers.find { driver -> driver.id == trip.driverId }!!
    }
    return trips
}

data class TripGenericDTO(
    val id: Long,
    val entityId: String,
    val name: String,
    var duration: Int,
    val numberPassengers: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String,
    val imgAvatar: String,
    val price: Double,
    val scored: Boolean
){
    fun toTrip(tripGenericDTO: TripGenericDTO): Trip = Trip().apply {
        duration = tripGenericDTO.duration
        numberPassengers = tripGenericDTO.numberPassengers
        date = tripGenericDTO.date
        origin = tripGenericDTO.origin
        destination = tripGenericDTO.destination
    }
}

fun Trip.toPassengerGenericDTO() : TripGenericDTO {
    val clientId = requireNotNull(client.id) { "Client ID is null" }
    val id = requireNotNull(id) { "ID is null" }

    return TripGenericDTO(
        id = id,
        entityId = clientId.toString(),
        name = client.firstName + " " + client.lastName,
        destination = destination,
        duration = duration,
        numberPassengers = numberPassengers,
        date = date,
        origin = origin,
        imgAvatar = client.img,
        scored = this.scored(),
        price = price
    )
}

fun Driver.toDriverGenericDTO(tripDriver: TripDriver) : TripGenericDTO {
    val id = requireNotNull(id) { "ID is null" }

    return TripGenericDTO(
        id = tripDriver.id,
        entityId = id,
        name = firstName + " " + lastName,
        destination = tripDriver.destination,
        duration = tripDriver.duration,
        numberPassengers = tripDriver.numberPassengers,
        date = tripDriver.date,
        origin = tripDriver.origin,
        imgAvatar = img,
        scored = false,
        price = tripDriver.price
    )
}

data class TripDriver(
    val id: Long,
    val duration: Int,
    val numberPassengers: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String,
    val price: Double,
    val finishedDateTime: LocalDateTime
){}


fun Trip.toTripDriverDTO() = TripDriver(
    id = id!!,
    duration = duration,
    origin = origin,
    destination = destination,
    date = date,
    numberPassengers = numberPassengers,
    price = price,
    finishedDateTime = finishedDateTime
)

data class TripCreateDTO(
    val driverId: String,
    val duration: Int,
    val numberPassengers: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String
)