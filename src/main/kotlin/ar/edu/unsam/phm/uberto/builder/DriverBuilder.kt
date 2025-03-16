package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.MotorbikeVehicle
import ar.edu.unsam.phm.uberto.model.PremiumVehicle
import ar.edu.unsam.phm.uberto.model.SimpleVehicle


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
        newDriver.vehicle = SimpleVehicle()
    }

    fun premium(moneyBalance: Double): DriverBuilder = apply {
        newDriver.vehicle = PremiumVehicle()
    }

    fun moto(moneyBalance: Double): DriverBuilder = apply {
        newDriver.vehicle = MotorbikeVehicle()
    }

    fun build(): Driver = newDriver

}