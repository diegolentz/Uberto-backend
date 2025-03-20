package ar.edu.unsam.phm.uberto.dto

import ar.edu.unsam.phm.uberto.model.Driver

class DriverDTO(
    var driverID: Int,
) {

}

fun Driver.toDTO() = DriverDTO(
    driverID = id
)