package ar.edu.unsam.phm.uberto.services

//import ar.edu.unsam.phm.uberto.repository.DriverAvgDTO
import ar.edu.unsam.phm.uberto.dto.DriverAvailableDto
import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class DriverService(
    private val mongoDriverRepo: MongoDriverRepository,
    private val tripsRepository: TripsRepository
) {

    fun getDriverData(userID: String):Driver{
        val driver = mongoDriverRepo.findById(userID).orElseThrow { NotFoundException("Driver with id $userID not found") }
        return driver
    }

    fun getByIdTrip(id: String): Driver =
        mongoDriverRepo.findById(id)
            .orElseThrow { NotFoundException("Driver with id $id not found") }

    @Transactional
    fun updateProfile(dto : DriverDTO, id: String) : ResponseEntity<String> {
        try {
            val driver = getDriverData(id)
            val update = driver.update(dto)
            mongoDriverRepo.save(update)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated profile")

        } catch (e: NotFoundException) {
            throw NotFoundException("Driver not found")
        }
    }

    fun getDriversAvailable(date: LocalDateTime, time: Int): List<Driver> {
        try {
            val endTime = date.plus(time.toLong(), ChronoUnit.MINUTES)
            return listOf() //TODO comento porque rompe, asi estaba en dev. Yo no toque nada
            //return mongoDriverRepo.getAvailable(date,endTime)
        } catch ( e : Exception) {
            throw BusinessException(e.message ?: "Error in the driver search")
        }
    }

    fun getByCredentialsId(id: String): Driver =
        mongoDriverRepo.findByCredentialsId(id.toLong())

    fun findAllByIds(ids: List<String>): List<Driver> {
        return mongoDriverRepo.findAllById(ids)
    }


}