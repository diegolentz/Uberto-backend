package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.repository.Repository
import org.springframework.stereotype.Service

@Service
class TripService {
    val passengerRepo: Repository<Passenger> = Repository()
    val tripRepo: Repository<Trip> = Repository()
    val driverRepo: Repository<Driver> = Repository()

    fun createTrip(trip: TripDTO): TripDTO {

        val client = UserService.passengerRepo.getByID(trip.userId)
        val driver = UserService.driverRepo.getByID(trip.driverDTO.driverID)

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
        driver.responseTrip(newTrip)

        UserService.passengerRepo.update(client)
        UserService.driverRepo.update(driver)
        UserService.tripRepo.create(newTrip)

        return newTrip.toDTO()

    }

    fun getAllTrips(): List<Trip> {
        return UserService.tripRepo.instances.toMutableList()
    }

}