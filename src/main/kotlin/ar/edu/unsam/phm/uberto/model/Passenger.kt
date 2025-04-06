package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BalanceAmmountNotValidException
import ar.edu.unsam.phm.uberto.FriendAlreadyExistException
import ar.edu.unsam.phm.uberto.FriendNotExistException
import ar.edu.unsam.phm.uberto.InsufficientBalanceException
import jakarta.persistence.*

@Entity
class Passenger : User {

    @Id
    override var id: Long = 0
    override var userId: Long = 0
    @Column(length = 50)
    override var firstName: String = ""
    @Column(length = 50)
    override var lastName: String = ""
    override var balance: Double = 0.0
    @OneToMany(fetch = FetchType.LAZY) @OrderColumn
    override val trips: MutableList<Trip> = mutableListOf()
    var cellphone: Int = 0
    var age: Int = 0
    @Column(length = 150)
    override var img: String = ""
    @OneToMany
    val friends: MutableSet<Passenger> = mutableSetOf()

    fun requestTrip(trip: Trip) {
        if (validateTrip(trip)) {
            throw InsufficientBalanceException()
        }
        this.addTrip(trip)
    }

    private fun validateTrip(trip: Trip): Boolean {
        return this.balance < trip.price()
    }

    private fun addTrip(trip: Trip) {
        this.payTrip(trip.price())
        this.trips.add(trip)
    }

    private fun payTrip(price: Double) {
        this.balance -= price
    }

    fun loadBalance(balance: Double) {
        if(balance <= 0){
            throw BalanceAmmountNotValidException()
        }
        this.balance += balance
    }

    override fun getScores(): List<TripScore> {
        return this.getScoredTrips().map { trip:Trip -> trip.score!! }
    }

    private fun getScoredTrips():List<Trip>{
        return this.trips.filter { it.score != null }
    }

    fun isFriendOf(passenger: Passenger) = this.friends.contains(passenger)

    fun addFriend(friend: Passenger) {
        if(this.isFriendOf(friend)){
            throw FriendAlreadyExistException()
        }
        friends.add(friend)
    }

    fun removeFriend(friend: Passenger) {
        if(!this.isFriendOf(friend)){
            throw FriendNotExistException()
        }
        friends.remove(friend)
    }

    fun pendingTrips() = trips.filter { trip:Trip ->trip.pendingTrip()}
    fun finishedTrips() = trips.filter { trip:Trip ->trip.finished()}

    fun scoreTrip(trip: Trip, message:String, scorePoints:Int){
        val score = TripScore(message=message, scorePoints = scorePoints)
        trip.addScore(score)
    }

}