package ar.edu.unsam.phm.uberto.model
import ar.edu.unsam.phm.uberto.*
import jakarta.persistence.*
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property

import java.time.LocalDate
import java.time.Period
import org.springframework.data.annotation.Transient as TransianN4j

@Entity
@Node
class Passenger : User {
    @Id
    @org.springframework.data.neo4j.core.schema.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(length = 50)
    @Property("first_name")
    override var firstName: String = ""

    @OneToOne
    @TransianN4j
    @JoinColumn(referencedColumnName = "id")
    var credentials: UserAuthCredentials? = null


    @Column(length = 50)
    @Property("last_name")
    override var lastName: String = ""

    @Column
    @TransianN4j
    override var balance: Double = 0.0

    @OneToMany
    @Property("trips")
    @TransianN4j
    override val trips: MutableList<Trip> = mutableListOf()

    @Column
    @TransianN4j
    var cellphone: Int = 0

    @Column
    @TransianN4j
    var birthDate: LocalDate = LocalDate.now()

    @Column(length = 255)
    override var img: String = ""

    @ManyToMany(fetch = FetchType.LAZY)
    @Property("friends")
    var friends: MutableSet<Passenger> = mutableSetOf()

//    @Transient
//    var driver : List<String> = trips.map { it.driverId }

    fun requestTrip(trip: Trip) {
        if (validateTrip(trip)) {
            throw InsufficientBalanceException()
        }
        this.addTrip(trip)
    }

    fun loadBalance(balance: Double) {
        if (balance <= 0) {
            throw BalanceAmmountNotValidException()
        }
        this.balance += balance
    }

    fun isFriendOf(passenger: Passenger) = this.friends.contains(passenger)

    fun addFriend(friend: Passenger) {
        if (this.isFriendOf(friend)) {
            throw FriendAlreadyExistException()
        }
        friends.add(friend)
    }

    fun removeFriend(friend: Passenger) {
        if (!this.isFriendOf(friend)) {
            throw FriendNotExistException()
        }
        friends.remove(friend)
    }

    fun scoreTrip(trip: Trip, message: String, scorePoints: Int) {
        validateScoreTrip(message, scorePoints)
        val score = TripScore()
        score.message = message
        score.scorePoints = scorePoints
        trip.addScore(score)
    }

    fun age(): Int = Period.between(birthDate, LocalDate.now()).years

    private fun validateScoreTrip(message: String, scorePoints: Int) {
        if (message.isEmpty()) {
            throw IsEmptyException()
        }
        if (scorePoints <= 0 || scorePoints > 6) {
            throw IncorrectValuesException()
        }
    }

    private fun validateTrip(trip: Trip): Boolean {
        return this.balance < trip.price
    }

    private fun addTrip(trip: Trip) {
        this.payTrip(trip.price)
        this.trips.add(trip)
    }

    private fun payTrip(price: Double) {
        this.balance -= price
    }

    override fun getScores(): List<TripScore> {
        return this.getScoredTrips().map { trip: Trip -> trip.score!! }
    }

    private fun getScoredTrips(): List<Trip> {
        return this.trips.filter { it.score != null }
    }
}