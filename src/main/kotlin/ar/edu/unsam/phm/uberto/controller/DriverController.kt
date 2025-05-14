package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.dto.DriverImg
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.dto.toImgDTO
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/driver")
class DriverController(
    private val driverService: DriverService,
    val timeTripsService: TravelTimeMockService,
    private val jwtUtil: TokenJwtUtil
) {

    @GetMapping()
    fun getByID(request: HttpServletRequest): DriverDTO {
        val idToken = jwtUtil.getIdFromTokenString(request)
        return driverService.getDriverData(idToken.toString()).toDTO()
    }

    @GetMapping("/img")
    fun getImg(request: HttpServletRequest): DriverImg {
        val idToken = jwtUtil.getIdFromTokenString(request)
        return driverService.getDriverData(idToken.toString()).toImgDTO()
    }

//    @GetMapping("/available")
//    fun getDriversAvailable(@RequestParam date: LocalDateTime,
//                            @RequestParam origin: String,
//                            @RequestParam destination: String,
//                            @RequestParam numberpassengers: Int): DriverCardAndTimeDTO {
//        val timeMap = timeTripsService.getTime()
//        val time = timeMap["time"] ?: throw BusinessException("Failure in the time calculation system")
//        val avaliableDrivers = driverService.getDriversAvailable(date, time)
//        val avaliableDriverDTO = avaliableDrivers.map{
//            it.driver.toAvailableDTO(time, numberpassengers, it.averageScore)
//        }
//        return DriverCardAndTimeDTO(time = time, cardDrivers = avaliableDriverDTO)
//    }

    @PostMapping()
    fun changeProfile(@RequestBody driverDTO: DriverDTO, request: HttpServletRequest): ResponseEntity<String> {
        val idToken = jwtUtil.getIdFromTokenString(request)
        return driverService.updateProfile(driverDTO, idToken.toString())
    }

}