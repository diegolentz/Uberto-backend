package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Driver

data class DriverDTO(
    var driverID: Int,
    var serial: String,
    var name: String,
    var brand: String,
    var model: Int,
    var basePrice: Double,
    var img: String,
    val trips: List<TripDTO>
)

fun Driver.toDTO() = DriverDTO(
    driverID = id,
    serial = serial,
    name = firstName + lastName,
    brand = brand,
    model = model,
    basePrice = basePrice,
    img = img,
    trips = trips.map { it.toDTO() }
)

data class DriverCardDTO(
    var driverID: Int,
    var serial: String,
    var name: String,
    var brand: String,
    var model: Int,
    var basePrice: Double,
    var img: String
)


fun Driver.toCardDTO() = DriverCardDTO(
    driverID = id,
    serial = serial,
    brand = brand,
    name = firstName + lastName,
    model = model,
    basePrice = basePrice,
    img = img
)