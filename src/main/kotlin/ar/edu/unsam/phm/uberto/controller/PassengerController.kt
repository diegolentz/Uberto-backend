package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.services.PassengerService
import jakarta.websocket.server.PathParam
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/passenger")
class PassengerController(private val passengerService: PassengerService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): PassengerProfileDto {
        val passenger = passengerService.getPassenger(id)
        return passenger.toDTOProfile()
    }


    @PutMapping("/addBalance")
    fun addBalance(@RequestParam id: Int, balance: Double): String {
        return passengerService.addBalance(id, balance)
    }

    @PutMapping("")
    fun updatePassenger(@RequestParam id: Int, @RequestBody updatedInfo: UpdatedPassengerDTO): String {
        return passengerService.updateInfo(id, updatedInfo)
    }

    @GetMapping("/{id}/friends")
    fun getFriends(@PathVariable id: Int): List<FriendDto> {
        val friends:List<Passenger> = passengerService.getFriends(id)
        return friends.map { friend:Passenger ->
            friend.toDTOFriend()
        }
    }

    @PostMapping("/friends")
    fun addFriend(@RequestParam passengerId: Int, friendId: Int): String {
        return passengerService.addFriend(passengerId, friendId)
    }

    @DeleteMapping("/friends")
    fun deleteFriend(@RequestParam passengerId: Int, friendId: Int): String {
        return passengerService.deleteFriend(passengerId, friendId)
    }

    @GetMapping("/{id}/friends/search")
    fun filter(@PathVariable id: Int, @RequestParam filter: String): List<FriendDto> {
        val nonFriendsPassengers:List<Passenger> = passengerService.searchNonFriends(id, filter)
        return nonFriendsPassengers.map { friend:Passenger ->
            friend.toDTOFriend()
        }
    }

    @GetMapping("/img")
    fun getImg(@RequestParam passengerId: Int): Map<String,String>{
        return mapOf("img" to passengerService.getImg(passengerId))
    }
}
