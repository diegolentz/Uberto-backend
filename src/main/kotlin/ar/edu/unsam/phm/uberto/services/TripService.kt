package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.FailSaveEntity
import ar.edu.unsam.phm.uberto.InsufficientBalanceException
import ar.edu.unsam.phm.uberto.NotFoundEntityException
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TripService(val tripRepo: TripsRepository) {

    fun getById(id: Long): Trip {
        return tripRepo.findById(id).get()
    }

    @Transactional
    fun createTrip(trip: TripDTO, client: Passenger, driver: Driver): ResponseEntity<String> {

        val newTrip = Trip().apply {
            duration = trip.duration
            numberPassengers = trip.numberPassengers
            date = trip.date
            origin = trip.origin
            destination = trip.destination
            this.client = client
            this.driver = driver
        }

        try{

        client.requestTrip(newTrip)
        driver.responseTrip(newTrip, trip.duration)
        }catch (e: Exception){
            return ResponseEntity.badRequest().body("${e.message}")
        }

        try{
            tripRepo.save(newTrip)
        }catch (e: DataAccessException){
            throw FailSaveEntity("Error en la creación del viaje")
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Se reserva de viaje exitosamente")

    }

    fun getAllByPassengerId(id: Long): List<Trip> {
        return tripRepo.findByClient_Id(id)
    }

    fun getAllByDriverId(id: Long): List<Trip> {
        return tripRepo.findByDriver_Id(id)
    }

    fun getPendingTripPassenger(id: Long): List<Trip> {
        return getAllByPassengerId(id).filter { it.pendingTrip() }
    }

    fun getFinishedTripPassenger(id: Long): List<Trip> {
        return getAllByPassengerId(id).filter { it.finished() }
    }

    fun getFinishedTripDriver(id: Long): List<Trip> {
        return getAllByDriverId(id).filter { it.finished() }
    }

    fun getTripsPendingFromDriver(
        origin: String,
        destination: String,
        numberPassenger: Int,
        name: String,
        driverId: Long): List<Trip> {
        return tripRepo.searchByForm(origin, destination, numberPassenger, name, driverId)
    }
}