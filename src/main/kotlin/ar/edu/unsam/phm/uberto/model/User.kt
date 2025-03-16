package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException

class User(
    firstName: String = "",
    lastName: String = "",
    username: String = "",
    password: String = "",
    age: Int = 0,
    money: Double = 0.0,
    var phone: Int = 0,

    ) : Person(
    firstName,
    lastName,
    username,
    password,
    age,
    money
) {
    override var id: Int = -1
    override var isDriver: Boolean = false
    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

    var balance: Double = 0.0


    //Recibe por parametro el conductor con el cual se va a reservar y el saldo que se va a debitar
    fun bookTrip(driver: Driver, balance: Double) {
        // driver.checkin Se le reserva el horario al conductor
        // removeBalance se resta el saldo
        // agregar a la alista de viajes pendientes
    }

    //Recibe por parametro el conductor del viaje y la calificacion
    fun rateDriver(driver: Driver, rating: Double) {
        driver.addRating(rating)
        ratings.add(rating)
    }

    fun addBalance(money: Double) {
        balance += money
    }

    fun removeBalance(money: Double) {
        val newBalance: Double = balance - money
        if (!operationIsValid(newBalance)) {
            throw BusinessException("Not enough money to perform the operation")
        }
        balance -= newBalance
    }


    private fun operationIsValid(newBalance: Double): Boolean = newBalance >= 0.0
}