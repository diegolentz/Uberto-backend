package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.FormTripDTO
import ar.edu.unsam.phm.uberto.dto.PendingAndFinishedTripsDTO
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/trip")

class TripsController(private val tripService: TripService, private val passengerService: PassengerService, private val driverService: DriverService) {


    @PostMapping("/create")
    fun createTrip(@RequestBody trip: TripDTO): ResponseEntity<String> {
        val client = passengerService.getById(trip.userId)
        val driver = driverService.getDriverData(trip.driverId)
        return tripService.createTrip(trip, client, driver)
    }

    @GetMapping("/passenger") //Metodo desdoblado (antes ruta "/trips?rol= & id=" )
    fun getAllByPassengerId(@RequestParam id: Long): List<TripDTO> {
        return tripService.getAllByPassengerId(id).map { it.toDTO() }
    }

    @GetMapping("/driver") //Metodo desdoblado (antes ruta "/trips?rol= & id=" )
    fun getAllByDriverId(@RequestParam id: Long): List<TripDTO> {
            return tripService.getAllByDriverId(id).map { it.toDTO() }
    }

    @PostMapping("/pending")
    fun getTripsPendingFromDriver(@RequestBody formTripDTO: FormTripDTO): List<TripDTO> {
        val origin = formTripDTO.origin
        val destination = formTripDTO.destination
        val driverId = formTripDTO.userId
        val name = formTripDTO.name
        val numberPassenger = formTripDTO.numberPassengers

        return tripService.getTripsPendingFromDriver(
            origin,
            destination,
            numberPassenger,
            name,
            driverId,
        ).map { it.toDTO() }
    }

    @GetMapping("/profile/passenger")
    fun getProfilePassenger(@RequestParam id:Long): PendingAndFinishedTripsDTO {
        val finishedTrips = tripService.getFinishedTripPassenger(id).map { it.toDTO() }
        val pendingTrips = tripService.getPendingTripPassenger(id).map { it.toDTO() }
        return PendingAndFinishedTripsDTO(pendingTrips, finishedTrips)
    }

    @GetMapping("/profile/driver")
    fun getProfileDriver(@RequestParam id:Long): PendingAndFinishedTripsDTO {
        val finishedTrips = tripService.getFinishedTripDriver(id).map { it.toDTO() }
        val pendingTrips = listOf<TripDTO>()
        return PendingAndFinishedTripsDTO(pendingTrips, finishedTrips)
    }
}