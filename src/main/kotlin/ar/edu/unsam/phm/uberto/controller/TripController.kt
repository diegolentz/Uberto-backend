package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.FormTripDTO
import ar.edu.unsam.phm.uberto.dto.PendingAndFinishedTripsDTO
import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/trip")

class TripsController(private val tripService: TripService) {

    @PostMapping("/create")
    fun createTrip(@RequestBody trip: TripDTO): ResponseEntity<String> {
        //Duda, aca paso DTO, como solucionar
        return tripService.createTrip(trip)
    }

    @GetMapping()
    fun getTrips(@RequestParam id: Long, rol: String): List<TripDTO> {
        return tripService.getById(id, rol).map { it.toDTO() }
    }

    @PostMapping("/pending")
    fun getTripsPendingFromDriver(@RequestBody formTripDTO: FormTripDTO): List<TripDTO> {
        //Duda Nico
        //esto es una negrada
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

    @GetMapping("/passenger")
    fun getFromPassenger(@RequestParam id:Long, rol: String): PendingAndFinishedTripsDTO {
        val finishedTrips = tripService.getFinished(id, rol).map { it.toDTO() }
        val pendingTrips = tripService.getPending(id, rol).map { it.toDTO() }
        return PendingAndFinishedTripsDTO(pendingTrips, finishedTrips)
    }
}