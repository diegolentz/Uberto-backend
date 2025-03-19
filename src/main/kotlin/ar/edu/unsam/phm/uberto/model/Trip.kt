package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

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
    var score: TravelScore? = null

    fun addScore(mensaje:String,rate:Int){
        if(score != null){
            throw BusinessException("Solo adquiriendo version premium")
        }
        this.score = TravelScore(mensaje,rate, LocalDate.now())
    }

    fun deleteScore(){
        score = null
    }

    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

    fun priceTrip(): Double = this.driver.fee(this)

    fun pendingTrip()  : Boolean = date > LocalDateTime.now()

    fun dateTimeFinished() : LocalDateTime = date.plus(duration.toLong(), ChronoUnit.MINUTES)
    fun finishedTrip() : Boolean  {
        return dateTimeFinished() < LocalDateTime.now()
    }

}
