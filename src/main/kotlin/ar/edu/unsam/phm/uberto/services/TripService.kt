package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.EntityResponse

@Service
class TripService(val passengerRepo: PassengerRepository, val driverRepo: DriverRepository, val tripRepo: TripsRepository) {

    fun createTrip(trip: TripDTO): ResponseEntity<String> {

        val client = passengerRepo.searchByUserID(trip.userId)
        val driver = driverRepo.searchByUserID(trip.driverId)
        if(client == null || driver == null){
            throw Exception("Fallo en la creacion de viaje")
        }

        val newTrip =
            Trip(
                trip.duration,
                trip.numberPassengers,
                trip.date,
                trip.origin,
                trip.destination,
                client,
                driver
            )

        client.requestTrip(newTrip)
        driver.responseTrip(newTrip, trip.duration)

        passengerRepo.update(client)
        driverRepo.update(driver)
        val successful = tripRepo.create(newTrip)
        if(!successful){
            throw BusinessException("No se pudo crear viaje")
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Se reserva de viaje exitosamente")

    }

    fun getAllTrips(): List<Trip> {
        return tripRepo.instances.toMutableList()
    }

}