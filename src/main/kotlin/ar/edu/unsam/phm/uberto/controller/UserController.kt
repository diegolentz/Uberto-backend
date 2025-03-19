package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.DTO.*
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.services.UserService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

//    @GetMapping("/users/{id}")
//    fun getUserById(@PathVariable("id") id: Int): UserProfileDto = userService.getProfile(id)

    @GetMapping("/drivers")
    fun getAllDrivers(): List<Driver> {
        return userService.getAllDrivers()
    }

    @PostMapping("/login")
    fun authLogin(@RequestBody loginRequestBody: LoginRequest): LoginResponse {
        return userService.validateLogin(loginRequestBody)
    }

    @GetMapping("/trips")
    fun getTrips() : List<Trip>{
        return userService.getAllTrips()
    }

    @PostMapping("/createTrip")
    fun createTrip(@RequestBody trip : TripDTO): TripDTO {
        return userService.createTrip(trip)
    }
}