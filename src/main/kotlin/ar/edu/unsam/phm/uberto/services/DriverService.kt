package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.DateDTO
import ar.edu.unsam.phm.uberto.dto.DriverCardAndTimeDTO
import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.dto.toCardDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import exceptions.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DriverService(val driverRepo: DriverRepository, val timeTripsService: TravelTimeMockService) {
    ///TODO Puede ser que haya metodos como searchById (que buscan la FK) que fueron MAL reemplazados por findById

//    fun getDriversAvailable(date: LocalDateTime, time: Int): List<Driver>{
//        return driverRepo.avaliable(date, time)
//    }

    fun getDriverData(userID: Long):Driver{
        val driver = driverRepo.findById(userID).get() ?: throw BusinessException("Driver not found")
        return driver
    }

    fun changeProfile(driverDTO: DriverDTO, driver: Driver): ResponseEntity<String> {
        driver.firstName = driverDTO.firstName
        driver.lastName = driverDTO.lastName
        driver.serial = driverDTO.serial
        driver.brand = driverDTO.brand
        driver.model = driverDTO.model
        driver.balance = driverDTO.price
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Updated profile")
    }

    fun update(driver: Driver): ResponseEntity<String>{
        //muchas dudas con esto, quiero saber si realmente se lleva a cabo o no
        driverRepo.save(driver) //revisar el save
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Updated")
    }

}