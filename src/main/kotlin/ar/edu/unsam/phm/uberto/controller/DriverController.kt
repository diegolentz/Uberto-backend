package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.DateDTO
import ar.edu.unsam.phm.uberto.dto.DriverCardDTO
import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.dto.toCardDTO
import ar.edu.unsam.phm.uberto.dto.toDTO
import ar.edu.unsam.phm.uberto.services.DriverService
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/driver")
class DriverController(private val driverService: DriverService) {

    @GetMapping()
    fun getByID(@RequestParam driverId:Int): DriverDTO {
        val driver = driverService.getDriverData(driverId)
        return driver.toDTO()
    }

    @GetMapping("/card")
    fun getCardByID(@RequestParam driverId:Int): DriverCardDTO {
        return driverService.getDriverData(driverId).toCardDTO()
    }

    @PostMapping("/avaliable")
    fun getDriversAvailable(@RequestBody date: DateDTO): List<DriverCardDTO> {
        return driverService.getDriversAvailable(date).map { it.toCardDTO() }
    }

    @GetMapping("/all")
    fun get(): List<DriverDTO> {
        return driverService.getAllDrivers().map { it.toDTO() }
    }
}