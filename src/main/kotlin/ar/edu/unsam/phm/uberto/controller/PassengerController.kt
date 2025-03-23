package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.DTO.BalanceDTO
import ar.edu.unsam.phm.uberto.DTO.PassengerProfileDto
import ar.edu.unsam.phm.uberto.DTO.UpdatedFriends
import ar.edu.unsam.phm.uberto.DTO.UpdatedPassengerDTO
import ar.edu.unsam.phm.uberto.services.PassengerService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/passenger/{id}")
class PassengerController(private val passengerService: PassengerService) {
    @GetMapping("")
    fun getById(@PathVariable id: Int): PassengerProfileDto {
        return passengerService.getPassenger(id)
    }

    @PutMapping("")
    fun updatePassenger(@PathVariable id: Int, @RequestBody updatedInfo: UpdatedPassengerDTO): PassengerProfileDto {
        return passengerService.updateInfo(id, updatedInfo)
    }

    @PutMapping("/addBalance")
    fun addBalance(@PathVariable id: Int, balance: Double): BalanceDTO {
        return passengerService.addBalance(id, balance)
    }

    @GetMapping("/friends")
    fun getFriends(@PathVariable id: Int): List<PassengerProfileDto> {
        return passengerService.getFriends(id)
    }

    @PutMapping("/friends")
    fun updateFriends(@PathVariable id: Int, @RequestBody updatedFriends: UpdatedFriends): List<PassengerProfileDto> {
        return passengerService.updateFriends(id, updatedFriends)
    }

}
