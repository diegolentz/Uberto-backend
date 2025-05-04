package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.FormTripDTO
import ar.edu.unsam.phm.uberto.dto.PendingAndFinishedTripsDTO
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import ar.edu.unsam.phm.uberto.services.TripService
import exceptions.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/trip")

class TripsController(private val tripService: TripService, private val passengerService: PassengerService, private val driverService: DriverService) {

    @PostMapping("/create")
    fun createTrip(@RequestBody trip: TripDTO): ResponseEntity<String> {
        val client = passengerService.getByIdTrip(trip.userId)
        val driver = driverService.getByIdTrip(trip.driverId)
        return tripService.createTrip(trip, client, driver)
    }

    @GetMapping("/passenger/{id}")
    fun getAllByPassengerId(@PathVariable id: Long): List<TripDTO> {
        val passenger = passengerService.getByIdTrip(id)
        return tripService.getAllByPassenger(passenger).map { it.toDTO() }
    }

    @GetMapping("/driver/{id}")
    fun getAllByDriverId(@PathVariable id: Long): List<TripDTO> {
        val driver = driverService.getByIdTrip(id)
        return tripService.getAllByDriver(driver).map { it.toDTO() }
    }

    @GetMapping("/pending")
    fun getTripsPendingFromDriver(
        @RequestParam origin: String,
        @RequestParam destination: String,
        @RequestParam driverId: Long,
        @RequestParam name: String,
        @RequestParam numberPassenger: Int,
    ): List<TripDTO> {

        return tripService.getTripsPendingFromDriver(
            origin,
            destination,
            numberPassenger,
            name,
            driverId,
        ).map { it.toDTO() }
    }

    @GetMapping("/profile/passenger/{id}")
    fun getProfilePassenger(@PathVariable id:Long): PendingAndFinishedTripsDTO {
        val passenger = passengerService.getByIdTrip(id)
        val finishedTrips = tripService.getFinishedTripPassenger(passenger).map { it.toDTO() }
        val pendingTrips = tripService.getPendingTripPassenger(passenger).map { it.toDTO() }
        return PendingAndFinishedTripsDTO(pendingTrips, finishedTrips)
    }

    @GetMapping("/profile/driver/{id}")
    fun getProfileDriver(@PathVariable id:Long): PendingAndFinishedTripsDTO {
        val driver = driverService.getByIdTrip(id)
        val finishedTrips = tripService.getFinishedTripDriver(driver).map { it.toDTO() }
        val pendingTrips = listOf<TripDTO>()
        return PendingAndFinishedTripsDTO(pendingTrips, finishedTrips)
    }
}