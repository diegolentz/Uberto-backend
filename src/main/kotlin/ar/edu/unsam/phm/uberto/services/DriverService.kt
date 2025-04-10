package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.DateDTO
import ar.edu.unsam.phm.uberto.dto.DriverCardAndTimeDTO
import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.dto.toCardDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.services.auth.AuthRepository
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import exceptions.BusinessException
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DriverService(val driverRepo: DriverRepository, val timeTripsService: TravelTimeMockService, val tripsRepo : TripsRepository) {
    ///TODO Puede ser que haya metodos como searchById (que buscan la FK) que fueron MAL reemplazados por findById

    fun getDriverData(userID: Long):Driver{
        try{
            val driver = driverRepo.findDriverByCredentials_Id(userID)
            return driver
        }catch (e : Exception){
            throw BusinessException("Driver not found")
        }
    }

    fun findById(id: Long): Driver {
        return driverRepo.findById(id).orElseThrow { BusinessException("Driver not found") }
    }

    @Transactional
    fun updateProfile(dto : DriverDTO) : ResponseEntity<String> {
        try {
            val driver = getDriverData(dto.id)
            val changed = changeProfile(dto, driver)
            driverRepo.save(changed)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated profile")

        } catch (e: BusinessException) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Driver cant be updated")
        }

    }

    fun changeProfile(driverDTO: DriverDTO, driver: Driver): Driver {
        driver.firstName = driverDTO.firstName
        driver.lastName = driverDTO.lastName
        driver.serial = driverDTO.serial
        driver.brand = driverDTO.brand
        driver.model = driverDTO.model
        driver.balance = driverDTO.price
        return driver
    }

    fun getDriversAvailable(date: LocalDateTime, time: Int): List<Driver> {
//        val driversIds = tripsRepo.findAvailableDrivers(date, time)
        val drivers = driverRepo.findAll()
        return drivers.filter { it.avaliable(date, time) }
    }



}