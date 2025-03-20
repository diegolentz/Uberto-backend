package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Driver

class DriverDTO(
    var driverID: Int,
)

class DriverCartDTO(
    var driverID: Int,
    var serial: String,
    var name: String,
    var brand: String,
    var model: Int,
    var basePrice: Double,
    var img: String
)


fun Driver.toCartDTO() = DriverCartDTO(
    driverID = id,
    serial = serial,
    brand = brand,
    name = firstName + lastName,
    model = model,
    basePrice = basePrice,
    img = img)


fun Driver.toDTO() = DriverDTO(
    driverID = id
)
