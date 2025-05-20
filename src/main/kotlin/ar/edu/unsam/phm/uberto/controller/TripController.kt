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

    @PostMapping("/create")
    fun createTrip(@RequestBody trip: TripDTO, request: HttpServletRequest): ResponseEntity<String> {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val client = passengerService.getByIdTrip(idToken)
        val driver = driverService.getByIdTrip(trip.driverId)
        return tripService.createTrip(trip, client, driver)
    }

    @GetMapping("/passenger") //TODO ACA ES IR A MONGO Y TRAERSE COSAS DEL TRIP
    fun getAllByPassengerId(request: HttpServletRequest): List<TripGenericDTO> {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val passenger = passengerService.getByIdTrip(idToken)
        val trips = tripService.getAllByPassenger(passenger)
        val driversMongo = driverService.findAllByIds(trips.map { it.driverId })
        matchDriverFromTrip(driversMongo, trips)
        return trips.map { it.toPassengerGenericDTO() } //cambiar
    }

    @GetMapping("/driver") //
    fun getAllByDriverId(request: HttpServletRequest): List<TripDTO> {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        val driver = driverService.getByIdTrip(idToken)
        val trips = tripService.getAllByDriver(driver)
        matchDriverFromTrip(listOf(driver), trips)
        return trips.map { it.toDTO() }

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
        val passenger = passengerService.getByIdTrip(idToken)
        val finishedTrips = tripService.getFinishedTripPassenger(passenger)
        val driversMongoFinished = driverService.findAllByIds(finishedTrips.map { it.driverId })
        matchDriverFromTrip(driversMongoFinished, finishedTrips)
        val pendingTrips = tripService.getPendingTripPassenger(passenger)
        val driverMongoPending = driverService.findAllByIds(pendingTrips.map { it.driverId })
        matchDriverFromTrip(driverMongoPending, pendingTrips)
        return PendingAndFinishedTripsDTO(pendingTrips.map { it.toDTO() }, finishedTrips.map { it.toDTO() })
    }

    @GetMapping("/profile/driver")
    fun getProfileDriver(request: HttpServletRequest): PendingAndFinishedTripsDTO {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        val driver = driverService.getByIdTrip(idToken)
        val finishedTrips = tripService.getFinishedTripDriver(driver)
        matchDriverFromTrip(listOf(driver), finishedTrips)
        val pendingTrips = listOf<TripDTO>()
        return PendingAndFinishedTripsDTO(pendingTrips, finishedTrips.map { it.toDTO() })
    }
}