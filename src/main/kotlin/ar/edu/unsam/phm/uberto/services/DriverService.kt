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
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

@Service
class DriverService(
    private val mongoDriverRepo: MongoDriverRepository,
    private val tripsRepository: TripsRepository
) {
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

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

    // En service:
    fun getAvailableDrivers(start: LocalDateTime, end: Int): List<DriverAvailableDto> {
      var endDate = start.plusHours(end.toLong())
        return mongoDriverRepo.getAvailable(start, endDate)
    }




    fun getByCredentialsId(id: String): Driver =
        mongoDriverRepo.findByCredentialsId(id.toLong())//.orElseThrow{throw NotFoundException("Driver no encontrado")}

    fun findAllByIds(ids: List<String>): List<Driver> {
        return mongoDriverRepo.findAllById(ids)
    }


}