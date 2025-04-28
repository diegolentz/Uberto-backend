package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DriverService(val driverRepo: DriverRepository) {

    fun getDriverData(userID: Long):Driver{
        val driver = driverRepo.getById(userID).orElseThrow { NotFoundException("Driver with id $userID not found") }
        return driver
    }

    fun getByIdTrip(id: Long): Driver =
        driverRepo.getById(id)
            .orElseThrow { NotFoundException("Driver with id $id not found") }

    @Transactional
    fun updateProfile(dto : DriverDTO) : ResponseEntity<String> {
        try {
            val driver = getDriverData(dto.id)
            val update = driver.update(dto)
            driverRepo.save(update)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated profile")

        } catch (e: NotFoundException) {
            throw BusinessException("Driver not found")
        }
    }

    fun getDriversAvailable(date: LocalDateTime, time: Int): List<Driver> {
        try {
//            find all no retorna nunca null, no funciona orElse
            return  driverRepo.findAll().filter { it.avaliable(date,time) }
        } catch ( e : Exception) {
            throw BusinessException(e.message ?: "Error in the driver search")
        }
    }

}