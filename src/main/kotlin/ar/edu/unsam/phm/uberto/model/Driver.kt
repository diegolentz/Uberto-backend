package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import java.time.LocalDateTime

abstract class Driver(
    override var username: String = "",
    override var password: String = "",
    override var firstName: String = "",
    override var lastName: String = "",
    override var balance: Double = 0.0,
    override var trips: MutableList<Trip> = mutableListOf(),
    override var img: String = "",
    var model:Int = 0,
    var brand:String = "",
    var serial:String = "",
    var basePrice:Double = 0.0
):User, AvaliableInstance {

    fun avaliable(trip: Trip):Boolean{
        return trip.dateTimeFinished() < LocalDateTime.now()
    }

    fun scoreAVG():Double{
        return this.trips.filter { it.score != null }.map { it.score!!.scorePoints }.average()
    }

    fun fee(trip:Trip):Double{
        return this.basePrice + this.plusFee(trip)
    }

    fun plusFee(trip:Trip):Double{
        return this.plusBasePrice(trip)*trip.duration
    }

    abstract fun plusBasePrice(trip:Trip):Double

    override fun getScores(): List<TripScore> {
        return this.trips.filter { it.score != null }.map { it.score!! }
    }

    fun addTrip(trip:Trip) {
        this.trips.add(trip)
        acceditTrip(trip.priceTrip())
    }

    fun acceditTrip(price: Double){
        this.balance += price
    }

    fun pendingTrips() = trips.filter { trip:Trip ->trip.pendingTrip()}
    fun finishedTrips() = trips.filter { trip:Trip ->trip.finishedTrip()}
    fun responseTrip(newTrip: Trip) {
        if(!avaliable(newTrip)){
            throw BusinessException("El chofer no se encuentra disponible")
        }
        addTrip(newTrip)
    }

}


