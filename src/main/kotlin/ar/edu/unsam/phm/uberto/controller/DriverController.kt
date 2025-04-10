package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.TravelTimeMockService
import exceptions.BusinessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/driver")
class DriverController(private val driverService: DriverService, val timeTripsService: TravelTimeMockService) {

    @GetMapping("/{id}")
    fun getByID(@PathVariable id:Long): DriverDTO {
        return driverService.getDriverData(id).toDTO()
    }

    @GetMapping("/avaliable")
    fun getDriversAvailable(@RequestParam date: LocalDateTime,
                            @RequestParam origin: String,
                            @RequestParam destination: String,
                            @RequestParam numberpassengers: Int): List<DriverCardDTO> {
        val timeMap = timeTripsService.getTime()
        val time = timeMap["time"] ?: throw BusinessException("Failure in the time calculation system")

        val avaliableDrivers = driverService.getDriversAvailable(date, time)

        val driverCardDTO = avaliableDrivers.map{it.toCardDTO(timeMap["time"]!!, numberpassengers)}
        return driverCardDTO
    }

    @PostMapping()
    fun changeProfile(@RequestBody driverDTO: DriverDTO): ResponseEntity<String>{
        return driverService.updateProfile(driverDTO)
    }

    @GetMapping("/img")
    fun getImg(@RequestParam driverid: Long): Map<String, String> {
        val driver = driverService.getDriverData(driverid)
        return mapOf("img" to driver.img)
    }
}