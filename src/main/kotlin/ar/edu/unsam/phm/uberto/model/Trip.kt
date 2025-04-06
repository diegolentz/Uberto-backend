package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.ScoredTripException
import ar.edu.unsam.phm.uberto.TripNotFinishedException
import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Entity
class Trip(
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var duration: Int = 0
    @Column
    var numberPassengers: Int = 0
    @Column
    var date: LocalDateTime = LocalDateTime.now()
    @Column(length = 40)
    var origin: String = ""
    @Column(length = 40)
    var destination: String = ""

    @OneToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id", unique = true, nullable = false)
    var client: Passenger = Passenger()

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", unique = true, nullable = false)
    var driver: Driver = SimpleDriver()

    @OneToOne
    @JoinColumn(name = "tripscore_id", referencedColumnName = "id", unique = true, nullable = false)
    var score: TripScore? = null

    fun addScore(newScore: TripScore){
        if(!this.finished()) throw TripNotFinishedException()
        if(this.scored()) throw ScoredTripException()
        this.score = newScore
    }

    fun scored():Boolean = (this.score != null)
    fun canDeleteScore(userId: Int) = userId == client.userId

    fun deleteScore(){
        score = null
    }

    fun price(): Double = this.driver.fee(duration, numberPassengers)

    fun pendingTrip()  : Boolean = date > LocalDateTime.now()

    fun finalizationDate() : LocalDateTime = date.plus(duration.toLong(), ChronoUnit.MINUTES)
    fun finished() : Boolean  {
        return finalizationDate() < LocalDateTime.now()
    }

    fun onlyTimeToStr(date: LocalDateTime): String {
        val time = date.toLocalTime()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter).toString()
    }

}