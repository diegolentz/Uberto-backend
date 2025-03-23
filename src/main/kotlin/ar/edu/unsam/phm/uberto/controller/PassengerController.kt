package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.BalanceDTO
import ar.edu.unsam.phm.uberto.dto.PassengerProfileDto
import ar.edu.unsam.phm.uberto.dto.UpdatedFriends
import ar.edu.unsam.phm.uberto.dto.UpdatedPassengerDTO
import ar.edu.unsam.phm.uberto.services.PassengerService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/passenger")
class PassengerController(private val passengerService: PassengerService) {
    @GetMapping("")
    fun getById(@RequestParam id: Int): PassengerProfileDto {
        return passengerService.getPassenger(id)
    }

    @PutMapping("")
    fun updatePassenger(@RequestParam id: Int, @RequestBody updatedInfo: UpdatedPassengerDTO): PassengerProfileDto {
        return passengerService.updateInfo(id, updatedInfo)
    }

    @PutMapping("/addBalance")
    fun addBalance(@RequestParam id: Int, balance: Double): BalanceDTO {
        return passengerService.addBalance(id, balance)
    }

    @GetMapping("/friends")
    fun getFriends(@RequestParam id: Int): List<PassengerProfileDto> {
        return passengerService.getFriends(id)
    }

    @PutMapping("/friends")
    fun updateFriends(@RequestParam id: Int, @RequestBody updatedFriends: UpdatedFriends): List<PassengerProfileDto> {
        return passengerService.updateFriends(id, updatedFriends)
    }

}
