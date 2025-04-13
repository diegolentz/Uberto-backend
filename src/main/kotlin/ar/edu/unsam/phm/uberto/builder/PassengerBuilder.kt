package ar.edu.unsam.phm.uberto.builder

import ar.edu.unsam.phm.uberto.model.Passenger
import java.time.LocalDate

class PassengerBuilder(val newPassenger: Passenger = Passenger()) {

    fun userId(id: Long): PassengerBuilder = apply {
        newPassenger.userId!!.id = id //arregllaaaaaar
    }

    fun firstName(name: String): PassengerBuilder = apply {
        newPassenger.firstName = name
    }

    fun img(image: String): PassengerBuilder = apply {
        newPassenger.img = image
    }

    fun lastName(name: String): PassengerBuilder = apply {
        newPassenger.lastName = name
    }

    fun birthDate(birthDate: LocalDate): PassengerBuilder = apply {
        newPassenger.birthDate = birthDate
    }

    fun cellphone(cellphoneNumber: Int): PassengerBuilder = apply {
        newPassenger.cellphone = cellphoneNumber
    }

    fun balance(moneyBalance: Double): PassengerBuilder = apply {
        newPassenger.balance = moneyBalance
    }

    fun build(): Passenger = newPassenger

}