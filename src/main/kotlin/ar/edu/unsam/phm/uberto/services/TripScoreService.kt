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
    fun create(tripScoreDTO: TripScoreDTO) {
        val trip = tripRepo.findById(tripScoreDTO.tripId).get()
        val passenger = passengerRepo.findById(trip.client.id!!).get()
        passenger.scoreTrip(trip,tripScoreDTO.message!!,tripScoreDTO.scorePoints!!)
        tripRepo.save(trip)
    }


//    fun delete(userId: Int, tripId: Int): ResponseEntity<String> {
//        val trip = tripRepo.getByID(tripId)
//        if (trip.client.id != userId) {
//            throw BusinessException("Usuario no posee calificaciones para eliminar")
//        }
//        if(trip.score == null){
//            throw BusinessException("El viaje no tiene puntuacion")
//        }
//        val tripScore = tripScoreRepo.getByID(trip.score!!.id)
//        tripScoreRepo.delete(tripScore)
//        trip.deleteScore()
//        tripRepo.update(trip)
//        return ResponseEntity
//            .status(HttpStatus.OK)
//            .body("Elimino calificacion")
//    }

}