package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/trip")

class TripsController(
    private val tripService: TripService,
    private val passengerService: PassengerService,
    private val driverService: DriverService,
    private val jwtUtil: TokenJwtUtil) {

    @PostMapping("/create") //TODO
    fun createTrip(@RequestBody trip: TripCreateDTO, request: HttpServletRequest): ResponseEntity<String> {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val client = passengerService.getByIdTrip(idToken)
        val driver = driverService.getByIdTrip(trip.driverId)
        return tripService.createTrip(trip, client, driver)
    }

    @GetMapping("/pending") //TODO OK
    fun getTripsPendingFromDriver(
        @RequestParam origin: String,
        @RequestParam destination: String,
        @RequestParam name: String,
        @RequestParam numberPassenger: Int,
        request: HttpServletRequest
    ): List<TripGenericDTO> {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        val trips = tripService.getTripsPendingFromDriver(
            origin,
            destination,
            numberPassenger,
            name,
            idToken,
        )
        return trips.map { it.toPassengerGenericDTO() }
    }

    @GetMapping("/profile/passenger")
    fun getProfilePassenger(request: HttpServletRequest): PendingAndFinishedTripsDTO {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val driverWithfinishedTrips = tripService.getDriverFinishedTripByPassenger(idToken)
        val driverWithpendingTrips = tripService.getDriverPendingTripByPassenger(idToken)
        return PendingAndFinishedTripsDTO(driverWithfinishedTrips.map {
            it.tripsDTO.map { trip -> it.toDriverGenericDTO(trip) } }.flatten(),
            driverWithpendingTrips.map {it.tripsDTO.map {
                trip -> it.toDriverGenericDTO(trip) } }.flatten())
    }

    @GetMapping("/profile/driver") //TODO machomenos machomenos, probar query
    fun getProfileDriver(request: HttpServletRequest): PendingAndFinishedTripsDTO {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        val finishedTrips = tripService.getFinishedTripDriver(idToken)
        val pendingTrips = listOf<TripGenericDTO>()
        return PendingAndFinishedTripsDTO(finishedTrips.map { it.toPassengerGenericDTO() }, pendingTrips)
    }
}