package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripDTO
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import ar.edu.unsam.phm.uberto.services.TripService
import ar.edu.unsam.phm.uberto.services.UserService
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
    fun createTrip(@RequestBody trip: TripDTO): TripDTO {
        return tripService.createTrip(trip)
    }

    @GetMapping() //TODO de quien?? falta un id
    fun getTrips(): List<Trip> {
        return tripService.getAllTrips()
    }



}