package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

data class Vehicle(val brand: VehicleBrand, val model: Int, val isCar: Boolean) {
    var LIMIT_YEARS_NEW = 10
    fun typeOf(): TypeDriver = if (isCar) {
        if ((LocalDate.now().year - model) <= LIMIT_YEARS_NEW) PremiumDrive() else SimpleDriver()
    } else {
        MotorbikeDriver()
    }
}

