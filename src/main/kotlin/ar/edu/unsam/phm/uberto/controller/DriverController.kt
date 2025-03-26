package ar.edu.unsam.phm.uberto.controller

import ar.edu.unsam.phm.uberto.dto.*
import ar.edu.unsam.phm.uberto.services.DriverService
import org.springframework.http.ResponseEntity
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

    @PostMapping("/avaliable")
    fun getDriversAvailable(@RequestBody date: DateDTO): DriverCardAndTimeDTO {
        //aca tengo que retornar ya tipo DTO por el servicio de tiempos
        return driverService.getDriversAvailable(date)
    }

    @GetMapping("/all") ///PARA TEST DE DESA
    fun get(): List<DriverDTO> {
        return driverService.getAllDrivers().map { it.toDTO() }
    }

    @PostMapping()
    fun changeProfile(@RequestBody driverDTO: DriverDTO): ResponseEntity<String>{
        return driverService.changeProfile(driverDTO)
    }
}