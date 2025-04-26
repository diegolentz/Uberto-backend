package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DriverService(val driverRepo: DriverRepository) {

    fun getDriverData(userID: Long):Driver{
        val driver = driverRepo.findById(userID)
            .orElseThrow { NotFoundException("Driver with id $userID not found") }
        return driver
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
        val driver: Driver
        try{
            driver = driverRepo.getByIdTrip(id)
        }catch (error: DataAccessException){
            throw NotFoundException("Driver with id ${id} not found")
        }
        return driver
    }

}