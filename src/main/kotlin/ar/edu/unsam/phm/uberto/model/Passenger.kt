package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import exceptions.BusinessException

class Passenger(
    override var username: String = "",
    override var password: String = "",
    override var firstName: String = "",
    override var lastName: String = "",
    override var balance: Double = 0.0,
    override val trips: MutableList<Trip> = mutableListOf(),
    var cellphone: Int = 0,
    var age: Int = 0,
    override var id: Int = 0,
    override var img: String = "",
    val friends: MutableList<Passenger> = mutableListOf()
) : User, AvaliableInstance {

    //var currentTrip:Trip? = null

    fun requestTrip(trip: Trip) {
        if (validateTrip(trip)) {
            throw BusinessException("you don't have enough money")
        }
        this.addTrip(trip)
    }

    private fun validateTrip(trip: Trip): Boolean {
        return this.balance < trip.priceTrip()
    }

    fun addTrip(trip: Trip) {
        this.payTrip(trip.priceTrip())
        this.trips.add(trip)
    }

    fun payTrip(price: Double) {
        this.balance -= price
    }

    fun loadBalance(balance: Double) {
        this.balance += balance
    }


    fun deleteTravelScore() {
        TODO("Not yet implemented")
    }

    override fun getScores(): List<TripScore> {
        TODO()//return this.trips.map { trip:Trip -> trip.score }
    }

    override fun cumpleCriterioBusqueda(texto: String): Boolean {
        TODO("Not yet implemented")
    }

    fun addFriend(friend: Passenger) {
        friends.add(friend)
    }

    fun removeFriend(friend: Passenger) {
        friends.remove(friend)
    }

    override fun rol(): String {
        return "passenger"
    }
}
