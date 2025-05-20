package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import exceptions.BusinessException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/driver")
class DriverController(
    private val driverService: DriverService,
    private val timeTripsService: TravelTimeMockService,
    private val jwtUtil: TokenJwtUtil
) {
    companion object {
        private const val MIN_PASSENGERS = 1
        private const val DEFAULT_SCORE = 0.0
    }

    @GetMapping
    fun getByID(request: HttpServletRequest): DriverDTO =
        jwtUtil.getIdFromTokenString(request)
            .toString()
            .let { driverService.getDriverData(it).toDTO() }

    @GetMapping("/img")
    fun getImg(request: HttpServletRequest): DriverImg =
        jwtUtil.getIdFromTokenString(request)
            .toString()
            .let { driverService.getDriverData(it).toImgDTO() }

    @GetMapping("/available")
    fun getDriversAvailable(
        @RequestParam date: LocalDateTime,
        @RequestParam origin: String,
        @RequestParam destination: String,
        @RequestParam numberpassengers: Int
    ): DriverCardAndTimeDTO {
        val time = timeTripsService.getTime()["time"]
            ?: throw BusinessException("Failure in the time calculation system")

        val availableDrivers = driverService.getDriversAvailable(date, time)
        val availableDriverDTO = availableDrivers.map { driver ->
            driver.driver.toAvailableDTO(
                time = time,
                numberPassenger = numberpassengers.coerceAtLeast(1),
                scores = driver.averageScore ?: 0.0
            )
        }

        return DriverCardAndTimeDTO(
            time = time,
            cardDrivers = availableDriverDTO
        )
    }

    @PostMapping
    fun changeProfile(
        @RequestBody driverDTO: DriverDTO,
        request: HttpServletRequest
    ): ResponseEntity<String> =
        jwtUtil.getIdFromTokenString(request)
            .toString()
            .let { driverService.updateProfile(driverDTO, it) }
}