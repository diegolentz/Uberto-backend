package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

//instancio para que no de error
class Trip(
    var duration: Double = 0.0,
    var numberPassengers: Int = 0,
    var date: LocalDate = LocalDate.now(),
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

    fun pendingTrip()  : Boolean = date > LocalDate.now()
    fun finishedTrip() : Boolean = date < LocalDate.now() // Local date + tiempo < localDate .now()

}
