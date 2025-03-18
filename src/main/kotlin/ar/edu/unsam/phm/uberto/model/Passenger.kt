package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import exceptions.BusinessException

class Passenger(
    override var username: String = "",
    override var password: String = "",
    override var firstName: String = "",
    override var lastName: String = "",
    override var balance: Double = 0.0,
    override var trips: MutableList<Trip> = mutableListOf(),
    var cellphone:Int = 0,
    var age:Int = 0,
    override var id: Int = 0
) : User, AvaliableInstance {

    var currentTrip:Trip? = null

    fun requestTrip(trip:Trip){
        trip.driver.addTrip(trip)
        this.trips.add(trip)
    }

    fun rateDriver(){
//        FALTA AGREGAR CONDICION DEL VIAJE FINALIZADO
        if((currentTrip != null) and true) this.setTravelScore()
        else throw BusinessException("Not currently on a Trip")
    }

    fun setTravelScore() {
//        Asocia el score al viaje
        val newScore = TravelScore()
        this.currentTrip?.addScore(newScore)
//        Agrega el viaje al conductor y a si mismo
        this.currentTrip?.driver?.addTrip(currentTrip!!)
        this.trips.add(currentTrip!!)
    }

    fun deleteTravelScore() {
        TODO("Not yet implemented")
    }

    override fun getScores(): List<TravelScore> {
        return this.trips.map { trip:Trip -> trip.score }
    }

    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

}
