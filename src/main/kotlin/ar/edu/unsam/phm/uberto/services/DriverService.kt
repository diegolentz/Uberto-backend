package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import exceptions.BusinessException
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DriverService(val driverRepo: DriverRepository) {

    fun getDriverData(userID: Long):Driver{
        try{
            val driver = driverRepo.findById(userID).orElseThrow { BusinessException("Driver not found") }
            return driver
        }catch (e : Exception){
            throw BusinessException("Driver not found")
        }
    }

    @Transactional
    fun updateProfile(dto : DriverDTO) : ResponseEntity<String> {
        try {
            val driver = getDriverData(dto.id)
            val update = driver.update(dto)
            driverRepo.save(update)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated profile")

        } catch (e: BusinessException) {
            throw BusinessException("Driver not found")
        }
    }

    fun getDriversAvailable(date: LocalDateTime, time: Int): List<Driver> {
        try {
            return  driverRepo.findAll().filter { it.avaliable(date,time) }
        } catch ( e : Exception) {
            throw BusinessException(e.message ?: "Error in the driver search")
        }
    }

    fun getByIdTrip(id: Long): Driver{
        return driverRepo.getByIdTrip(id)
    }

}