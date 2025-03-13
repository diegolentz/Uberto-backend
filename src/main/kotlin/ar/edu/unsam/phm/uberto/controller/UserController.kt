package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.DTO.*
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.UserService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
class UserController(private val userService: UserService,private val driverService: DriverService) {

    @GetMapping("/users")
    fun getAllUsers() : List<User>{
        return userService.getAllUsers()
    }

    @GetMapping("/drivers")
    fun getAllDrivers() : List<Driver>{
        return driverService.getAllDrivers()
    }

    @PostMapping("/login")
    fun authLogin(@RequestBody loginRequestBody: LoginRequest) : LoginResponse {
        return userService.validateLogin(loginRequestBody)
    }
}