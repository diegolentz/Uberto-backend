package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.dto.FormTripDTO
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Service
class TripService(val passengerRepo: PassengerRepository, val driverRepo: DriverRepository, val tripRepo: TripsRepository) {

    @Transactional
    fun createTrip(trip: TripDTO): ResponseEntity<String> {

        val client = passengerRepo.findById(trip.userId).get()
        val driver = driverRepo.findById(trip.driverId).get()

        val newTrip = Trip().apply {
            duration = trip.duration
            numberPassengers = trip.numberPassengers
            date = trip.date
            origin = trip.origin
            destination = trip.destination
            this.client = client
            this.driver = driver
        }

        client.requestTrip(newTrip)
        driver.responseTrip(newTrip, trip.duration)

        try{
            tripRepo.save(newTrip)
        }catch (e: SQLException){
            throw RuntimeException("Error en la creación del viaje")
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Se reserva de viaje exitosamente")

    }

    fun getById(id: Long, rol: String): List<Trip> {
        return if(rol == "PASSENGER"){
            tripRepo.findByClient_Id(id)
        }else{
            tripRepo.findByDriver_Id(id)
        }
    }

    fun getPending(id: Long, rol: String): List<Trip> {
        return getById(id, rol).filter { it.pendingTrip() }
    }

    fun getFinished(id: Long, rol: String): List<Trip> {
        return getById(id, rol).filter { it.finished() }
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