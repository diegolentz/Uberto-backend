package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.dto.LoginResponse
import ar.edu.unsam.phm.uberto.services.AuthService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
class LoginController(private val authService: AuthService) {
    @PostMapping("/login")
    fun authLogin(@RequestBody loginRequestBody: LoginRequest): LoginResponse {
        return authService.validateLogin(loginRequestBody)
    }
}