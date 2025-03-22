package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Driver

class DriverBuilder(val newDriver: Driver) {

    fun userId(id: Int): DriverBuilder = apply {
        newDriver.userId = id
    }

//    fun username(user: String): DriverBuilder = apply {
//        newDriver.username = user
//    }
//    fun password(pass: String): DriverBuilder = apply {
//        newDriver.password = pass
//    }
    fun firstName(name: String): DriverBuilder = apply {
        newDriver.firstName = name
    }

    fun lastName(name: String): DriverBuilder = apply {
        newDriver.lastName = name
    }

    fun balance(moneyBalance: Double): DriverBuilder = apply {
        newDriver.balance = moneyBalance
    }


    fun build(): Driver = newDriver

}