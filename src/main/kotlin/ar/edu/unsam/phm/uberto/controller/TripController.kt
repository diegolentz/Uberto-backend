package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
class TripsController(private val travelTimeService: TravelTimeMockService) {
    @GetMapping("/estimate")
    fun getTime() : Map<String, Int>{
        return travelTimeService.getTime()
    }

}