package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.HomeSearch
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.HomeService
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
    private val jwtUtil: TokenJwtUtil,
    private val homeService: HomeService
) {

    @GetMapping()
    fun getByID(request: HttpServletRequest): DriverDTO {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        return driverService.getDriverData(idToken).toDTO()
    }

    @GetMapping("/img")
    fun getImg(request: HttpServletRequest): DriverImg {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        return driverService.getDriverData(idToken).toImgDTO()
    }
    @GetMapping("/available")
    fun getDriversAvailable(
        @RequestParam date: LocalDateTime,
        @RequestParam origin: String,
        @RequestParam destination: String,
        @RequestParam numberpassengers: Int,
        request: HttpServletRequest
    ): DriverCardAndTimeDTO {

        val time = timeTripsService.getTime()["time"] 
            ?: throw BusinessException("Failure in the time calculation system")
        val drivers = driverService.getAvailableDrivers(date, time)
        val availableDriverDTO = drivers.map {
                it.driver.toAvailableDTO(time, numberpassengers, it.averageScore)
            }
        val passengerId = jwtUtil.getIdFromTokenString(request)
        homeService.saveHome(
        HomeSearch(
            numberPassengers = numberpassengers,
            date = date,
            origin = origin,
            destination = destination,
            passengerId = passengerId
        ))
        return  DriverCardAndTimeDTO(time = time, cardDrivers = availableDriverDTO)
    }

    @PostMapping()
    fun changeProfile(@RequestBody driverDTO: DriverDTO, request: HttpServletRequest): ResponseEntity<String> {
        val idToken = jwtUtil.getIdDriverFromTokenString(request)
        return driverService.updateProfile(driverDTO, idToken)
    }
}

