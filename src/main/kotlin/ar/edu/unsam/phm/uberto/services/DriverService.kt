package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.DTO.DateDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import exceptions.BusinessException
import org.springframework.stereotype.Service

@Service
class DriverService(val driverRepo: DriverRepository, val timeTripsService: TravelTimeMockService) {
    fun getDriversAvailable(date: DateDTO): List<Driver>{
        val timeMap = timeTripsService.getTime()
        if(timeMap["time"] == null){
            throw Exception("Error en el servicio de consulta de tiempo")
        }
        return driverRepo.instances.filter { it.avaliable(date.date, timeMap["time"]!!) }
    }

    fun getDriverData(userID:Int):Driver{
        val driver = driverRepo.searchByUserID(userID) ?: throw BusinessException("Driver not found")
        return driver
    }

    fun getAllDrivers(): List<Driver> {
        return driverRepo.instances.toMutableList()
    }
}