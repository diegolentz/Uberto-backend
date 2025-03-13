package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Driver


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

    fun build(): Driver = newDriver

}