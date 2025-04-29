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
//
//    @GetMapping("/available")
//    fun getDriversAvailable(@RequestParam date: LocalDateTime,
//                            @RequestParam origin: String,
//                            @RequestParam destination: String,
//                            @RequestParam numberpassengers: Int): List<DriverCardDTO> {
//        val timeMap = timeTripsService.getTime()
//        val time = timeMap["time"] ?: throw BusinessException("Failure in the time calculation system")
//
//        val avaliableDrivers = driverService.getDriversAvailable(date, time)
////        val avgs = driverService.findAverages(avaliableDrivers.map { it.id!! })
////        val driverCardDTO = avaliableDrivers.map{
////            driver: Driver ->  driver.toCardDTO(timeMap["time"]!!,
////            numberpassengers,
////            avgs.first { (it.id == driver.id) })}
//        return driverCardDTO
//    }
//    avgs {
//        id = driverid
//        avg = avg
//    }


    @PostMapping()
    fun changeProfile(@RequestBody driverDTO: DriverDTO): ResponseEntity<String> =  driverService.updateProfile(driverDTO)

}