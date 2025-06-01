package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AnalyticsService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/analytics")
class AnalyticsController(
    private val analyticsService: AnalyticsService,
    private val passengerService: PassengerService,
    private val driverService: DriverService,
    private val jwtUtil: TokenJwtUtil,
) {

    @PostMapping()
    fun logClick(request: HttpServletRequest, @RequestParam driverId: String) {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val passenger = passengerService.getById(idToken)
        val driver = driverService.getDriverData(driverId)
        analyticsService.logClick(passenger, driver)
    }
}