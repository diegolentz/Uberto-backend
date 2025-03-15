package ar.edu.unsam.phm.uberto.model

import java.time.LocalDate
import java.util.Date

class Driver(
    firstName: String = "",
    lastName: String = "",
    username: String = "",
    password: String = "",
    age: Int = 0,
    money: Double = 0.0,
    var vehicle: Vehicle = SimpleDriver(),
    var BASE_PRICE: Double = 0.0
) : Person(
    firstName,
    lastName,
    username,
    password,
    age,
    money)
{
    override var id: Int = -1
    override var isDriver = true
    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

    val ratings: MutableList<Double> = mutableListOf()
    val travelRealizated : MutableList<String> = mutableListOf()


    fun addRating(rating: Double) = ratings.add(rating)
    fun avgRating(): Double = ratings.average()
    fun tripPrice(): Double = 0.0 // ???
    fun tripTime(): Double = 0.0 // ???
    fun setBasePrice(price: Double) {
        BASE_PRICE = price
    }

    fun travelPrice(travel : Travel): Double =
        BASE_PRICE + vehicle.calculatePlus(travel.time, travel.numberPassengers)

    fun commission(travel: Travel): Double = travelPrice(travel) * 0.5

    fun addRecaudation(travel: Travel){
        money += travelPrice(travel) - commission(travel)
    }



//    el pica debe implementar el date en travel para ver como terminar este metodo
    fun busy(date : LocalDate): Boolean = pendingTrips.any{ it.date.equals(date) }


}


