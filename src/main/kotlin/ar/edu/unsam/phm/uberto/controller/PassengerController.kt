package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.PassengerProfileDto
import ar.edu.unsam.phm.uberto.dto.UpdatedFriends
import ar.edu.unsam.phm.uberto.services.PassengerService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/passenger")
class PassengerController(private val passengerService: PassengerService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): PassengerProfileDto {
        return passengerService.getPassenger(id)
    }

    @GetMapping("/{id}/friends")
    fun getFriends(@PathVariable id: Int): List<PassengerProfileDto> {
        return passengerService.getFriends(id)
    }

    @PutMapping("/{id}/friends")
    fun updateFriends(@PathVariable id: Int, @RequestBody updatedFriends: UpdatedFriends): List<PassengerProfileDto> {
       return passengerService.updateFriends(id, updatedFriends)
    }


}