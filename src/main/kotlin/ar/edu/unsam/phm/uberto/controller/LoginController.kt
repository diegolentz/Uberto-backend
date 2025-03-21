package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.dto.LoginResponse
import ar.edu.unsam.phm.uberto.dto.toLoginDTO
import ar.edu.unsam.phm.uberto.services.AuthService
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/login")
class LoginController(private val authService: AuthService) {
    @PostMapping()
    fun authLogin(@RequestBody loginRequestBody: LoginRequest): LoginDTO {
        return authService.validateLogin(loginRequestBody)!!.toLoginDTO()
    }
}