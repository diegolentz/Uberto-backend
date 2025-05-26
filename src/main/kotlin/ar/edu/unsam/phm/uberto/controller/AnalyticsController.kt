package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.ClickDTO
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ar.edu.unsam.phm.uberto.services.AnalyticsService
import ar.edu.unsam.phm.uberto.services.PassengerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/analytics")
class AnalyticsController(
    private val analyticsService: AnalyticsService,
    private val passengerService: PassengerService,
    private val jwtUtil: TokenJwtUtil,
) {
    @PostMapping()
    fun logClick(request: HttpServletRequest, @RequestParam driver_name: String) {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val passenger = passengerService.getById(idToken)
        val passengerName = passenger.firstName + " " + passenger.lastName
        val clickData = ClickDTO(passengerName, driver_name)
        analyticsService.logClick(clickData)
    }
}