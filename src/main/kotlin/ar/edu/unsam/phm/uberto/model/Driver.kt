package ar.edu.unsam.phm.uberto.model

import java.util.Date

class Driver(
    override var firstName: String,
    override var lastName: String,
    override var username: String,
    override var password: String,
    override var age: Int,
    override var celNumber: String,
    override var money: Double,

    var vehicle: Vehicle,
) : Person {

    override val performedTrips: MutableList<Travel> = mutableListOf()
    override val pendingTrips: MutableList<Travel> = mutableListOf()


    // conductor recibe por parametro el vehiculo
    var basePrice: Double = 0.0
    val ratings: MutableList<Double> = mutableListOf()
    val typeDriver: TypeDriver = vehicle.typeOf()
    val travelRealizated : MutableList<String> = mutableListOf()


    fun addRating(rating: Double) = ratings.add(rating)
    fun averageRating(): Double = ratings.average()
    fun tripPrice(): Double = 0.0
    fun tripTime(): Double = 0.0
    fun setBasePrice(price: Double) {
        basePrice = price
    }

    fun travelPrice(travel : Travel): Double {
        return basePrice + typeDriver.calculatePlus(travel.time, travel.numberPassengers)
    }
    fun commission(travel: Travel): Double {
       return travelPrice(travel) * 0.5
    }
    fun addRecaudation(travel: Travel){
        money += travelPrice(travel) - commission(travel)
    }


//    el pica debe implementar el date en travel para ver como terminar este metodo
    fun busy(date : Date): Boolean {
        return pendingTrips.any{ it.date == date }
    }


}


