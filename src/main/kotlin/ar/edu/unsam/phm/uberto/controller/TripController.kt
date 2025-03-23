package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/trip")
class TripsController(private val travelTimeService: TravelTimeMockService, private val tripService: TripService) {
    @GetMapping("/estimate") //este creo que no se usa REVISAR
    fun getTime() : Map<String, Int>{
        return travelTimeService.getTime()
    }

    @PostMapping("/create")
    fun createTrip(@RequestBody trip: TripDTO): ResponseEntity<String> {
        return tripService.createTrip(trip)
    }

    @GetMapping()
    fun getTrips(@RequestParam id:Int,rol: String): List<TripDTO> {
        return tripService.getById(id, rol).map { it.toDTO() }
    }

    @GetMapping("/pending")
    fun getTripsPending(@RequestParam id:Int,rol: String): List<TripDTO> {
        return tripService.getPending(id, rol).map { it.toDTO() }
    }

}