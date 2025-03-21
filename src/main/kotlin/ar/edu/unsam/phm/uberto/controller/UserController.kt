package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.services.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    //================ USER class
    @Operation(summary = "Get all users", description = "Returns all users")
    @GetMapping()
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Int): PassengerProfileDto = userService.getProfile(id)

    @GetMapping("/friends")
    fun getFriends(@PathVariable("id") id: Int): List<FriendDTO> {
        return userService.getFriends(id)
    }

    @PutMapping("/friends")
    fun updateFriends(@RequestBody body: UpdatedFriends): List<FriendDTO> = userService.updateFriends(body)



}