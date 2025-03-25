package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

//instancio para que no de error
class Trip(
    var duration: Int = 0,
    var numberPassengers: Int = 0,
    var date: LocalDateTime = LocalDateTime.now(),
    var origin: String = "",
    var destination: String = "",
    var client: Passenger = Passenger(),
    var driver: Driver = SimpleDriver(),
    override var id: Int = 0
):AvaliableInstance{
    var score: TripScore? = null

    fun addScore(newScore: TripScore){
        if(score != null){
            throw BusinessException("Solo adquiriendo version premium")
        }
        this.score = newScore
    }

    fun canDeleteScore(userId: Int) = userId == client.userId

    fun delete(){
        score = null
    }

    fun priceTrip(): Double = this.driver.fee(duration, numberPassengers)

    fun pendingTrip()  : Boolean = date > LocalDateTime.now()

    fun dateTimeFinished() : LocalDateTime = date.plus(duration.toLong(), ChronoUnit.MINUTES)
    fun finishedTrip() : Boolean  {
        return dateTimeFinished() < LocalDateTime.now()
    }

    fun onlyTimeToStr(date: LocalDateTime): String {
        val time = date.toLocalTime()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter).toString()
    }

}
