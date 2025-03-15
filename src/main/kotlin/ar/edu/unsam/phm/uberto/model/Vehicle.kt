package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate

abstract class Vehicle(val brand: VehicleBrand = VehicleBrand.FIAT, val model: Int = 0, val isCar: Boolean = true, var licensePlate : String = "") {
    abstract val K_PLUS: Double
    abstract fun calculatePlus(time: Int, numberPassengers: Int): Double
}

class SimpleDriver(
    brand: VehicleBrand =VehicleBrand.FIAT,
    model: Int = 0,
    isCar: Boolean = true,
    licensePlate: String ="") :
    Vehicle(brand, model, isCar, licensePlate) {

    override var K_PLUS = 1000.00
    override fun calculatePlus(time: Int, numberPassengers: Int): Double = K_PLUS
}

class PremiumDrive(
    brand: VehicleBrand =VehicleBrand.FIAT,
    model: Int = 0,
    isCar: Boolean = true,
    licensePlate: String ="") :
    Vehicle(brand, model, isCar, licensePlate) {
    var LIMIT_YEARS_NEW = 10
    var PRICE_MORE_PASSENGERS = 1500.00
    override var K_PLUS = 2000.00

    override fun calculatePlus(time: Int, numberPassengers: Int): Double = if ( numberPassengers > 1) PRICE_MORE_PASSENGERS * time else K_PLUS * time
}

class  MotorbikeDriver(
    brand: VehicleBrand =VehicleBrand.FIAT,
    model: Int = 0,
    isCar: Boolean = true,
    licensePlate: String ="") :
    Vehicle(brand, model, isCar, licensePlate) {
    var PRICE_MORE_TIME = 600.00
    var K_TIME = 30
    override var K_PLUS = 500.00
    override fun calculatePlus(time: Int, numberPassengers: Int): Double = if ( time <= K_TIME) K_PLUS * time else PRICE_MORE_TIME * time
}