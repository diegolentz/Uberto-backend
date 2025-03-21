package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DriverService(val driverRepo: DriverRepository) {
    fun getDriversAvailable(date: LocalDateTime): List<Driver>{
        return driverRepo.instances.filter { it.avaliable(date) }
//        return driverRepo.getDriversAvailable(date)
    }

    fun getAllDrivers(): List<Driver> {
        return driverRepo.instances.toMutableList()
    }
}