package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import org.springframework.stereotype.Service

@Service
class TripService(val passengerRepo: PassengerRepository, val driverRepo: DriverRepository, val tripRepo: TripsRepository) {

    fun createTrip(trip: TripDTO): TripDTO {

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
        tripRepo.create(newTrip)

        return newTrip.toDTO()

    }

    fun getAllTrips(): List<Trip> {
        return tripRepo.instances.toMutableList()
    }

}