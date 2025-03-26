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

    fun changeProfile(driverDTO: DriverDTO): ResponseEntity<String> {
        val driver = driverRepo.searchByUserID(driverDTO.id)
        if(driver == null) throw BusinessException("Driver no encontrado")
        mapFieldProfile(driverDTO, driver)
        driverRepo.update(driver)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("Perfil actualizado")
    }

    private fun mapFieldProfile(driverDTO: DriverDTO, driver: Driver){
        driver.firstName = driverDTO.firstName
        driver.lastName = driverDTO.lastName
        driver.serial = driverDTO.serial
        driver.brand = driverDTO.brand
        driver.model = driverDTO.model
        driver.balance = driverDTO.price
    }

    fun getImg(driverId: Int): String {
        val driver = driverRepo.searchByUserID(driverId)
        return driver!!.img
    }
}