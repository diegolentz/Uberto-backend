package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.services.TripScoreService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/TripScore")
class TripScoreController(private val tripScoreService: TripScoreService){

}
