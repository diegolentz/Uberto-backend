package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.model.Role
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/login")
class LoginController(private val authService: AuthService) {
    @PostMapping()
    fun authLogin(@RequestBody loginRequestBody: LoginRequest): LoginDTO {
        val user = authService.validateLogin(loginRequestBody)

        if (user.role == Role.DRIVER) {
            val driver = authService.driverRepository.findByCredentials_Id(user.id!!)
            return LoginDTO(id = driver.get().id!!, rol = user.role)
        } else {
            val passenger = authService.passengerRepository.findByCredentials_Id(user.id!!)
            return LoginDTO(id = passenger.get().id!!, rol = user.role)
        }
    }
}