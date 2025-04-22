package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.dto.scoreToDTO
import ar.edu.unsam.phm.uberto.model.TripScore
import ar.edu.unsam.phm.uberto.repository.TripScoreRepository
import ar.edu.unsam.phm.uberto.services.PassengerService
import ar.edu.unsam.phm.uberto.services.TripScoreService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/tripScore")
class TripScoreController(
    private val tripScoreService: TripScoreService,
    private val tripService: TripService,
    private val passengerService: PassengerService
){

    @GetMapping("/passenger")
    fun getScorePassenger(@RequestParam userId: Long): List<TripScoreDTO>{
        val trips = tripService.getAllByPassengerId(userId)
        val tripScore = tripScoreService.getFromPassenger(trips)
        return tripScore.map { it!!.scoreToDTO(userId) }
    }

    @GetMapping("/driver")
    fun getScoreDriver(@RequestParam userId: Long): List<TripScoreDTO>{
        val trips = tripService.getAllByDriverId(userId)
        val tripScore = tripScoreService.getFromDriver(trips)
        return tripScore.map { it!!.scoreToDTO(userId) }
    }

    @PostMapping()
    fun create(@RequestBody tripScoreDTO: TripScoreDTO): ResponseEntity<String> {
        val trip = tripService.getById(tripScoreDTO.tripId)
        val score = TripScore()
        score.message = tripScoreDTO.message
        score.scorePoints = tripScoreDTO.scorePoints
        return tripScoreService.create(trip,score)

    }

    @DeleteMapping()
    fun delete(@RequestParam userId: Long , @RequestParam tripId: Long): ResponseEntity<String>{
        val trip = tripService.getById(tripId)
        val passenger = passengerService.getById(userId)
        return tripScoreService.delete(passenger,trip)
    }

}