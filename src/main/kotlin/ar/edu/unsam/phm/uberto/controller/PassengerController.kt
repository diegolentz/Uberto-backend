package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
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
    fun updatePassenger(@RequestParam id: Int, @RequestBody updatedInfo: UpdatedPassengerDTO): String {
        return passengerService.updateInfo(id, updatedInfo)
    }

    @PutMapping("/addBalance")
    fun addBalance(@RequestParam id: Int, balance: Double): String {
        return passengerService.addBalance(id, balance)
    }

    @GetMapping("/friends")
    fun getFriends(@RequestParam id: Int): List<FriendDto> {
        return passengerService.getFriends(id)
    }

    @PostMapping("/friends")
    fun addFriend(@RequestParam passengerId: Int, friendId: Int): List<FriendDto> {
        return passengerService.addFriend(passengerId, friendId)
    }

    @DeleteMapping("/friends")
    fun deleteFriend(@RequestParam passengerId: Int, friendId: Int): List<FriendDto> {
        return passengerService.deleteFriend(passengerId, friendId)
    }
}
