package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.services.DriverService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/driver")
class DriverController(private val driverService: DriverService) {

    @GetMapping()
    fun getAllDrivers(): List<Driver> {
        return driverService.getAllDrivers()
    }

    @PostMapping("/available")
    fun getDriversAvailable(@RequestBody date: LocalDateTime): List<Driver> {
        return driverService.getDriversAvailable(date) //esto para que no rompa despues viene por path
    }
}