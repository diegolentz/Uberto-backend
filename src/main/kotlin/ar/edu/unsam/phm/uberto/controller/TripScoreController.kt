package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.TripScoreDTO
import ar.edu.unsam.phm.uberto.dto.scoreToDTO
import ar.edu.unsam.phm.uberto.repository.TripScoreRepository
import ar.edu.unsam.phm.uberto.services.TripScoreService
import ar.edu.unsam.phm.uberto.services.TripService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/tripScore")
class TripScoreController(
    private val tripScoreService: TripScoreService,
    private val tripService: TripService,
    private val tripScoreRepository: TripScoreRepository
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

        tripScoreService.create(tripScoreDTO)
        return ResponseEntity.ok().body("Creado con exito")
    //tripScoreService.create(passenger,trip)
    }
    // Traer el viaje serviceTrip.ById(id) EntityGraph
    // Traigo al cliente a partir del viaje
    // Una vez que tengo al cliente ya puntuo a partir del mismo


    //    @DeleteMapping()
//    fun delete(@RequestParam userId: Int, tripId: Int): ResponseEntity<String>{
//        return tripScoreService.delete(userId, tripId)
//    }
//
}