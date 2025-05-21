package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.FailSaveException
import ar.edu.unsam.phm.uberto.dto.TripCreateDTO
import ar.edu.unsam.phm.uberto.dto.toTripDriverDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TripService(
    val tripRepo: TripsRepository,
    val driverRepo: MongoDriverRepository
) {

    fun getById(id: Long): Trip {
        return tripRepo.findById(id).get()
    }

    @Transactional
    fun createTrip(trip: TripCreateDTO, client: Passenger, driver: Driver): ResponseEntity<String> {

        val newTrip = Trip().apply {
            duration = trip.duration
            numberPassengers = trip.numberPassengers
            date = trip.date
            origin = trip.origin
            destination = trip.destination
            this.client = client
            this.driver = driver
            driverId = driver.id!!
        }

        try{ //TODO mandar a un metodo privado
            client.requestTrip(newTrip)
            driver.responseTrip(newTrip, trip.duration)
        }catch (e: Exception){
            return ResponseEntity.badRequest().body("${e.message}")
        }

        try{
            tripRepo.save(newTrip)
            driver.tripsDTO.add(newTrip.toTripDriverDTO())
            driverRepo.save(driver)
        }catch (e: DataAccessException){ //TODO atrapar las 2 excepciones porque son de 2 db distintas
            throw FailSaveException("Error en la creación del viaje")
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Se reserva de viaje exitosamente")

    }

    fun getAllByPassenger(passenger: Passenger): List<Trip> {
        return tripRepo.findByClient(passenger)
    }

    fun getAllByDriver(driverId: String): List<Trip> {
        return tripRepo.findByDriverId(driverId)
    }

    fun getFinishedTripPassenger(passenger: Passenger): List<Trip> {
        return getAllByPassenger(passenger).filter { it.finished() }
    }

    fun getFinishedTripDriver(driverId: String): List<Trip> {
        return tripRepo.findByDriverIdFinishedTrips(driverId)
    }

    fun getDriverFinishedTripByPassenger(passengerId: Long): List<Driver> {
        return driverRepo.findByPassengerIdFinishedTripsDTO(passengerId, LocalDateTime.now())
    }

    fun getDriverPendingTripByPassenger(passengerId: Long): List<Driver> {
        return driverRepo.findByPassengerIdPassengerTripsDTO(passengerId, LocalDateTime.now())
    }

    fun getTripsPendingFromDriver(
        origin: String,
        destination: String,
        numberPassenger: Int,
        name: String,
        driverId: String): List<Trip> {
        return tripRepo.searchByForm(origin, destination, numberPassenger, name, driverId)
    }
}