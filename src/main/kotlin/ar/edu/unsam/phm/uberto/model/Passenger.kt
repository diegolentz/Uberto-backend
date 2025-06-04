package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BalanceAmmountNotValidException
import ar.edu.unsam.phm.uberto.FriendAlreadyExistException
import ar.edu.unsam.phm.uberto.FriendNotExistException
import ar.edu.unsam.phm.uberto.IncorrectValuesException
import ar.edu.unsam.phm.uberto.InsufficientBalanceException
import ar.edu.unsam.phm.uberto.IsEmptyException
import jakarta.persistence.Entity
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue as JpaGeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Id as Neo4jId
import org.springframework.data.neo4j.core.schema.GeneratedValue as Neo4jGeneratedValue
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.LocalDate
import java.time.Period

@Entity // JPA: Marks the class as an entity for Postgres
@Node("Passenger") // Neo4J: Marks the class as a node for Neo4J
open class Passenger(
    @Neo4jId // Neo4J: Marks the identifier for Neo4J
    @jakarta.persistence.Id // JPA: Marks the identifier for Postgres
    @JpaGeneratedValue(strategy = GenerationType.IDENTITY) // JPA: Auto-generated ID for Postgres
    @Neo4jGeneratedValue // Neo4J: Auto-generated ID for Neo4J
    var id: Long? = null,

    @Column(length = 50) // JPA: Specifies column attributes for Postgres
    var firstName: String = "",

    @Column(length = 50) // JPA: Specifies column attributes for Postgres
    var lastName: String = "",

    var balance: Double = 0.0,
    var cellphone: Int = 0,
    var birthDate: LocalDate = LocalDate.now(),

    @Column(length = 255) // JPA: Specifies column attributes for Postgres
    var img: String = "",

    @Relationship(type = "HAS_CREDENTIALS", direction = Relationship.Direction.OUTGOING) // Neo4J: Relationship for graph
    @OneToOne // JPA: Defines a one-to-one relationship for Postgres
    @JoinColumn(name = "credentials_id") // JPA: Specifies the foreign key column in Postgres
    var credentials: UserAuthCredentials? = null,

    @Relationship(type = "HAS_TRIP", direction = Relationship.Direction.OUTGOING) // Neo4J: Relationship for graph
    @OneToMany()
    val trips: MutableList<Trip> = mutableListOf(),

    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING) // Neo4J: Relationship for graph
    @ManyToMany(fetch = FetchType.LAZY)
    val friends: MutableSet<Passenger> = mutableSetOf()
) {
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
        validateScoreTrip(message,scorePoints)
        val score = TripScore()
        score.message = message
        score.scorePoints = scorePoints
        trip.addScore(score)
    }

    fun age(): Int = Period.between(birthDate, LocalDate.now()).years

    private fun validateScoreTrip(message: String,scorePoints: Int) {
        if (message.isEmpty()) { throw IsEmptyException() }
        if (scorePoints <= 0 || scorePoints > 6) { throw IncorrectValuesException() }
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


    fun getScores(): List<TripScore> {
        return this.getScoredTrips().map { trip: Trip -> trip.score!! }
    }

    private fun getScoredTrips(): List<Trip> {
        return this.trips.filter { it.score != null }
    }

    // Business logic methods...
}