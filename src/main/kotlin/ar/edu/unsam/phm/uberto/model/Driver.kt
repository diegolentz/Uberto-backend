package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

abstract class Driver(
    override var firstName: String = "",
    override var lastName: String = "",
    override var balance: Double = 0.0,
    override var trips: MutableList<Trip> = mutableListOf(),
    override var img: String = "",
    override var userId: Int = 0,
    var model:Int = 0,
    var brand:String = "",
    var serial:String = "",
    var basePrice:Double = 0.0
):User, AvaliableInstance {

    fun avaliable(date: LocalDateTime, time: Int):Boolean{
        val finishedDate =  date.plus(time.toLong(), ChronoUnit.MINUTES)
        return trips.any{ it.dateTimeFinished() < date || it.date > finishedDate}
    }

    fun scoreAVG():Double{
        return this.getScoredTrips().map { it.score!!.scorePoints }.average()
    }

    fun fee(trip:Trip):Double{
        return this.basePrice + this.plusFee(trip)
    }

    fun plusFee(trip:Trip):Double{
        return this.plusBasePrice(trip)*trip.duration
    }

    abstract fun plusBasePrice(trip:Trip):Double

    override fun getScores(): List<TripScore> {
        return this.getScoredTrips().map{ it.score!! }
    }

    private fun getScoredTrips():List<Trip>{
        return this.trips.filter { it.score != null }
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

    fun responseTrip(newTrip: Trip, time: Int) {
        if(!avaliable(newTrip.date, time)){
            throw BusinessException("El chofer no se encuentra disponible")
        }
        addTrip(newTrip)
    }


}


