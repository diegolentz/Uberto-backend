package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import java.time.LocalDate
import kotlin.random.Random

//instancio para que no de error
class Trip(
    var duration: Double = Random.nextDouble(),
    var numberPassengers: Int = 0,
    var date: LocalDate = LocalDate.now(),
    var origin: String = "",
    var destination: String = "",
    var client: Passenger = Passenger(),
    var driver: Driver = SimpleDriver(),
    var score:TravelScore = TravelScore(),
    override var id: Int = 0
):AvaliableInstance{
    fun addScore(score:TravelScore){
        this.score = score
    }

    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }
}
