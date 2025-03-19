package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.repository.AvaliableInstance

abstract class Driver(
    override var username: String = "",
    override var password: String = "",
    override var firstName: String = "",
    override var lastName: String = "",
    override var balance: Double = 0.0,
    override var trips: MutableList<Trip> = mutableListOf(),
    var basePrice:Double = 0.0
):User, AvaliableInstance {
    companion object Vehicle {
        var model:String = ""
        var brand:String = ""
        var serial:String = ""
    }

    fun avaliable():Boolean{
        return true
    }

    fun score():Double{
        return this.trips.map { trip:Trip -> trip.score.scorePoints }.average()
    }
    fun fee(trip:Trip):Double{
        return this.basePrice + this.plusFee(trip)
    }

    fun plusFee(trip:Trip):Double{
        return this.plusBasePrice(trip)*trip.duration
    }

    abstract fun plusBasePrice(viaje:Trip):Double

    override fun getScores(): List<TravelScore> {
        return this.trips.map { trip:Trip -> trip.score }
    }

    fun addTrip(trip:Trip) {
        this.trips.add(trip)
        acceditTrip(trip.priceTrip())
    }

    fun acceditTrip(price: Double){
        this.balance += price
    }

    fun pendingTrips() = trips.map { trip:Trip ->trip.pendingTrip()}
    fun finishedTrips() = trips.map { trip:Trip ->trip.finishedTrip()}
    fun responseTrip(newTrip: Trip) {

    }

}


////    el pica debe implementar el date en travel para ver como terminar este metodo
//    fun busy(date : LocalDate): Boolean = pendingTrips.any{ it.date.equals(date) }

