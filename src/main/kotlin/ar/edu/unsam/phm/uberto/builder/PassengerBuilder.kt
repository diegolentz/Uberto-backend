package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Passenger

class PassengerBuilder(val newPassenger: Passenger = Passenger()) {

    fun username(user: String): PassengerBuilder = apply {
        newPassenger.username = user
    }
    fun password(pass: String): PassengerBuilder = apply {
        newPassenger.password = pass
    }
    fun firstName(name: String): PassengerBuilder = apply {
        newPassenger.firstName = name
    }

    fun lastName(name: String): PassengerBuilder = apply {
        newPassenger.lastName = name
    }

    fun age(age: Int): PassengerBuilder = apply {
        newPassenger.age = age
    }

    fun cellphone(cellphoneNumber: Int): PassengerBuilder = apply {
        newPassenger.cellphone = cellphoneNumber
    }

    fun balance(moneyBalance: Double): PassengerBuilder = apply {
        newPassenger.balance = moneyBalance
    }

    fun build(): Passenger = newPassenger

}