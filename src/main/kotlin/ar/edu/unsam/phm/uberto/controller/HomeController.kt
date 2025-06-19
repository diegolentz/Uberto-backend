package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.model.HomeSearch
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.HomeService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/home")
class HomeController(
    private val homeService: HomeService,
    private val jwtUtil: TokenJwtUtil
) {

    @GetMapping()
    fun getDataHome(request: HttpServletRequest): HomeSearch {
        val passengerId = jwtUtil.getIdFromTokenString(request)
        return homeService.getHomeByPassengerId(passengerId)
    }
}