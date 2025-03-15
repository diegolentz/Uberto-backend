package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.MotorbikeDriver
import ar.edu.unsam.phm.uberto.model.PremiumDrive
import ar.edu.unsam.phm.uberto.model.SimpleDriver


class DriverBuilder(val newDriver: Driver = Driver()) {

    fun firstName(name: String): DriverBuilder = apply {
        newDriver.firstName = name
    }

    fun lastName(name: String): DriverBuilder = apply {
        newDriver.lastName = name
    }

    fun age(age: Int): DriverBuilder = apply {
        newDriver.age = age
    }

    fun balance(moneyBalance: Double): DriverBuilder = apply {
        newDriver.money = moneyBalance
    }

    fun simple(moneyBalance: Double): DriverBuilder = apply {
        newDriver.vehicle = SimpleDriver()
    }

    fun premium(moneyBalance: Double): DriverBuilder = apply {
        newDriver.vehicle = PremiumDrive()
    }

    fun moto(moneyBalance: Double): DriverBuilder = apply {
        newDriver.vehicle = MotorbikeDriver()
    }

    fun build(): Driver = newDriver

}