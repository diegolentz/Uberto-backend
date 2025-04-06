package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.DriverNotAvaliableException
//import ar.edu.unsam.phm.uberto.repository.AvaliableInstance
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

//a jackarta no le gusta mucho recibir parametros por constructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Driver():User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id:Int = 0

    @Column(length = 255)
    override lateinit var firstName: String

    @Column(length = 255)
    override lateinit var lastName: String

    @Column
    override  var balance: Double = 0.0


    @OneToMany(mappedBy = "driver", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    override var trips: MutableList<Trip> = mutableListOf()

    @Column(length = 255)
    override lateinit var img: String

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", unique = true, nullable = false)
    lateinit var userId: UserAuthCredentials

    @Column
    var model:Int = 0

    @Column(length = 255)
    lateinit var brand:String

    @Column(length = 255)
    lateinit var serial:String

    @Column
    var basePrice:Double = 0.0



    fun avaliable(tripDate: LocalDateTime, tripDuration: Int):Boolean{
        val finishedDate =  tripDate.plus(tripDuration.toLong(), ChronoUnit.MINUTES)
        return !this.tripOverlapping(tripDate, finishedDate) || trips.isEmpty()
    }

    fun tripOverlapping(tripStart: LocalDateTime, tripEnd: LocalDateTime):Boolean{
        return this.pendingTrips().any{ pending:Trip ->
            tripStart < pending.finalizationDate() && tripEnd > pending.date
        }
    }

    fun scoreAVG():Double{
        val avg = getScoredTrips().map { it.score!!.scorePoints }.average()
        return if(avg.isNaN()) 0.0 else avg
    }

    fun fee(time: Int, numberPassenger: Int):Double{
        return this.basePrice + this.plusBasePrice(time, numberPassenger) * time
    }

    abstract fun plusBasePrice(time: Int, numberPassengers: Int):Double

    override fun getScores(): List<TripScore> {
        return this.getScoredTrips().map{ it.score!! }
    }

    private fun getScoredTrips():List<Trip>{
        return this.trips.filter { it.score != null }
    }

    fun addTrip(trip:Trip) {
        this.trips.add(trip)
        acceditTrip(trip.price())
    }

    fun acceditTrip(price: Double){
        this.balance += price
    }

    fun pendingTrips() = trips.filter { trip:Trip ->trip.pendingTrip()}
    fun finishedTrips() = trips.filter { trip:Trip ->trip.finished()}

    fun responseTrip(newTrip: Trip, time: Int) {
        if(!avaliable(newTrip.date, time)){
            throw DriverNotAvaliableException()
        }
        addTrip(newTrip)
    }

}

