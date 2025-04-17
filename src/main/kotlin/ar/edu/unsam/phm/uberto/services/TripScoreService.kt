package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.TripScore
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripScoreRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TripScoreService(
    private val tripScoreRepo: TripScoreRepository,
    private val tripRepo: TripsRepository,
    private val driverRepo: DriverRepository,
    private val passengerRepo: PassengerRepository
) {
    fun getFromPassenger(trips:List<Trip>): List<Trip?>{
        val tripsScore  = trips.filter { it.score != null }
        return tripsScore
    }

    fun getFromDriver(trips: List<Trip>): List<Trip?> {
        val tripsScore  = trips.filter { it.score != null }
        return tripsScore
    }

    @Transactional
    fun create(trip : Trip , score: TripScore) {
        val passenger = passengerRepo.findById(trip.client.id!!).get()
        passenger.scoreTrip(trip,score.message,score.scorePoints)
        tripRepo.save(trip)
    }

    @Transactional
    fun delete(passenger: Passenger, trip: Trip) {
        if (trip.client.id != passenger.id) {
            throw BusinessException("User has no ratings to delete")
        }
        if(trip.score == null){
            throw BusinessException("The trip has no score")
        }
        trip.deleteScore()
        tripRepo.save(trip)
    }

}