package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.services.PassengerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/passenger")
class PassengerController(private val passengerService: PassengerService) {

    ///TODO Puede ser que haya metodos como searchById (que buscan la FK) que fueron MAL reemplazados por findById
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): PassengerProfileDto {
        val passenger = passengerService.getPassenger(id)
        return passenger.toDTOProfile()
    }


    @PutMapping("/addBalance")
    fun addBalance(@RequestParam id: Long, balance: Double): ResponseEntity<String> {
        val currentPassenger = passengerService.getPassenger(id)
        return passengerService.addBalance(currentPassenger, balance)
    }

    @PutMapping("")
    fun updatePassenger(
        @RequestParam id: Long,
        @RequestBody updatedInfo: UpdatedPassengerDTO
    ): ResponseEntity<String> {
        //TODO Preguntar!!!
        val currentPassenger = passengerService.getPassenger(id)
        return passengerService.updateInfo(
            currentPassenger,
            updatedInfo.firstName, updatedInfo.lastName, updatedInfo.phone
        )
    }

    @GetMapping("/{id}/friends")
    fun getFriends(@PathVariable id: Long): List<FriendDto> {
        return passengerService.getFriends(id)
    }

    @PostMapping("/friends")
    fun addFriend(@RequestParam passengerId: Long, friendId: Long): ResponseEntity<String> {
        return passengerService.addFriend(passengerId, friendId)
    }

    @DeleteMapping("/friends")
    fun deleteFriend(@RequestParam passengerId: Long, friendId: Long): ResponseEntity<String> {
        return passengerService.deleteFriend(passengerId, friendId)
    }

    @GetMapping("/{id}/friends/search")
    fun filter(@PathVariable id: Long, @RequestParam filter: String): List<FriendDto> {
        val nonFriendsPassengers: List<Passenger> = passengerService.searchNonFriends(id, filter)
        return nonFriendsPassengers.map { friend: Passenger ->
            friend.toDTOFriend()
        }
    }

    @GetMapping("/img")
    fun getImg(@RequestParam passengerId: Long): Map<String, String> {
        val currentPassenger = passengerService.getPassenger(passengerId)
        return mapOf("img" to currentPassenger.img)
    }
}