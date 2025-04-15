package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.dto.scoreToDTO
import ar.edu.unsam.phm.uberto.services.TripScoreService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/tripScore")
class TripScoreController(
    private val tripScoreService: TripScoreService,
    private val tripService: TripService
){
//    @GetMapping("/driver")
//    fun get(@RequestParam userId: Int): List<TripScoreDTO>{
//        return tripScoreService.getAllFromDriver(userId).map { it!!.toDTO() }
//    }

    @GetMapping("/passenger")
    fun getScorePassenger(@RequestParam userId: Long): List<TripScoreDTO>{
        val trips = tripService.getAllByPassengerId(userId)
        val tripScore = tripScoreService.getFromPassenger(trips)
        return tripScore.map { it!!.scoreToDTO(userId) }
    }
//
//    @DeleteMapping()
//    fun delete(@RequestParam userId: Int, tripId: Int): ResponseEntity<String>{
//        return tripScoreService.delete(userId, tripId)
//    }
//
//    @PostMapping()
//    fun create(@RequestBody tripScoreDTO: TripScoreDTO): ResponseEntity<String>{
//        return tripScoreService.create(tripScoreDTO)
//    }
}