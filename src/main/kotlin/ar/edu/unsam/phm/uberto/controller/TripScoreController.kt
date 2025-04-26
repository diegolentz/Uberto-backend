package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.dto.scoreToDTO
import ar.edu.unsam.phm.uberto.model.TripScore
import ar.edu.unsam.phm.uberto.repository.TripScoreRepository
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import ar.edu.unsam.phm.uberto.services.TripScoreService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/tripScore")
class TripScoreController(
    private val tripScoreService: TripScoreService,
    private val tripService: TripService,
    private val passengerService: PassengerService,
    private val driverService: DriverService
){

    @GetMapping("/passenger/{id}")
    fun getScorePassenger(@PathVariable id: Long): List<TripScoreDTO>{
        val passenger = passengerService.getByIdTrip(id)
        val trips = tripService.getAllByPassenger(passenger)
        val tripScore = tripScoreService.getFromPassenger(trips)
        return tripScore.map { it!!.scoreToDTO(id) }
    }

    @GetMapping("/driver/{id}")
    fun getScoreDriver(@PathVariable id: Long): List<TripScoreDTO>{
        val driver = driverService.getByIdTrip(id)
        val trips = tripService.getAllByDriver(driver)
        val tripScore = tripScoreService.getFromDriver(trips)
        return tripScore.map { it!!.scoreToDTO(id) }
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