package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException

class User(
    firstName: String = "",
    lastName: String = "",
    username: String = "",
    password: String = "",
    age: Int = 0,
    celNumber: Int = 0,
    money: Double = 0.0,

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

    // Se le pasa por parametro un viaje y se añade a la lista
    fun addPerformedTrip() {}

    // Se le pasa por parametro un viaje y se añade a la lista
    fun addPendingTrip() {}

    // Se le pasa por parametro un viaje y se lo remueve a la lista
    fun removePendingTrip() {}

    //Recibe por parametro el conductor con el cual se va a reservar y el saldo que se va a debitar
    fun bookTrip() {
        // driver.checkin Se le reserva el horario al conductor
        // removeBalance se resta el saldo
    }

    //Recibe por parametro el conductor del viaje y la calificacion
    fun rateDriver() {
        //driver.AddRating(rating) se agrega en el Array del chofer la calificacion
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