package ar.edu.unsam.phm.uberto.DTO

import ar.edu.unsam.phm.uberto.model.Driver

class DriverDTO(
    var driverID: Int,
) {

}

fun Driver.toDTO() = DriverDTO(
    driverID = id
)