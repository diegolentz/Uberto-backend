package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.DateDTO
import ar.edu.unsam.phm.uberto.dto.DriverCardAndTimeDTO
import ar.edu.unsam.phm.uberto.dto.toCardDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import exceptions.BusinessException
import org.springframework.stereotype.Service

@Service
class DriverService(val driverRepo: DriverRepository, val timeTripsService: TravelTimeMockService) {
    fun getDriversAvailable(date: DateDTO): DriverCardAndTimeDTO{
        val timeMap = timeTripsService.getTime() //service time
        if(timeMap["time"] == null){
            throw Exception("Error en el servicio de consulta de tiempo")
        }
        val cardDriverDTO = driverRepo.avaliable(date.date, timeMap["time"]!!).map{it.toCardDTO(timeMap["time"]!!, date.numberPassengers)}
        return DriverCardAndTimeDTO(timeMap["time"]!!, cardDriverDTO) //o un MAP
    }

    fun getDriverData(userID:Int):Driver{
        val driver = driverRepo.searchByUserID(userID) ?: throw BusinessException("Driver not found")
        return driver
    }

    fun getAllDrivers(): List<Driver> {
        return driverRepo.instances.toMutableList()
    }
}