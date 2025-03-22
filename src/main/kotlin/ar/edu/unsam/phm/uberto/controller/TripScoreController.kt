package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.services.TripScoreService
import jakarta.websocket.server.PathParam
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/TripScore")
class TripScoreController(private val tripScoreService: TripScoreService){
    @GetMapping("/driver")
    fun get(@RequestParam userId: Int): List<TripScoreDTO>{
        return tripScoreService.getAllFromDriver(userId).map { it!!.toDTO() }
    }
}
