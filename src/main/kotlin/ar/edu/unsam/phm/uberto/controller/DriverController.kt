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
    fun getByID(@PathVariable id:Long): DriverDTO = driverService.getDriverData(id).toDTO()

    @GetMapping("/img")
    fun getImg(@RequestParam driverid: Long): DriverImg = driverService.getDriverData(driverid).toImgDTO()

    @GetMapping("/available")
    fun getDriversAvailable(@RequestParam date: LocalDateTime,
                            @RequestParam origin: String,
                            @RequestParam destination: String,
                            @RequestParam numberpassengers: Int): DriverCardAndTimeDTO {
        val timeMap = timeTripsService.getTime()
        val time = timeMap["time"] ?: throw BusinessException("Failure in the time calculation system")
        val avaliableDrivers = driverService.getDriversAvailable(date, time)
        val avaliableDriverDTO = avaliableDrivers.map{
            it.driver.toAvailableDTO(time, numberpassengers, it.averageScore)
        }
        return DriverCardAndTimeDTO(time = time, cardDrivers = avaliableDriverDTO)
    }

    @PostMapping()
    fun changeProfile(@RequestBody driverDTO: DriverDTO): ResponseEntity<String> =  driverService.updateProfile(driverDTO)

}