package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

class Vehicle(val brand: VehicleBrand = VehicleBrand.FIAT, val model: Int = 0, val isCar: Boolean = true, var licensePlate : String = "") {
    var LIMIT_YEARS_NEW = 10

    fun typeOf(): TypeDriver = if (isCar) {
        if ((LocalDate.now().year - model) <= LIMIT_YEARS_NEW) PremiumDrive() else SimpleDriver()
    } else {
        MotorbikeDriver()
    }
}

