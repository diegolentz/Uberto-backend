package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.PassengerProfileDto
import ar.edu.unsam.phm.uberto.dto.FriendDto
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


    @PutMapping("/addBalance")
    fun addBalance(@RequestParam id: Int, balance: Double): String {
        return passengerService.addBalance(id, balance)
    }

    @PutMapping("")
    fun updatePassenger(@RequestParam id: Int, @RequestBody updatedInfo: UpdatedPassengerDTO): String {
        return passengerService.updateInfo(id, updatedInfo)
    }

    @GetMapping("/friends")
    fun getFriends(@RequestParam id: Int): List<FriendDto> {
        return passengerService.getFriends(id)
    }

    @PostMapping("/friends")
    fun addFriend(@RequestParam passengerId: Int, friendId: Int): String {
        return passengerService.addFriend(passengerId, friendId)
    }

    @DeleteMapping("/friends")
    fun deleteFriend(@RequestParam passengerId: Int, friendId: Int): String {
        return passengerService.deleteFriend(passengerId, friendId)
    }

    @GetMapping("/friends/search")
    fun filter(@RequestParam id: Int, filter: String): List<FriendDto> {
        return passengerService.searchFriends(id, filter)
    }

    @GetMapping("/img")
    fun getImg(@RequestParam passengerId: Int): Map<String,String>{
        return mapOf("img" to passengerService.getImg(passengerId))
    }
}
