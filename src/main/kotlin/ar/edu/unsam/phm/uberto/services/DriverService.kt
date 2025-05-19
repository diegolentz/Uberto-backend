package ar.edu.unsam.phm.uberto.services

//import ar.edu.unsam.phm.uberto.repository.DriverAvgDTO
import ar.edu.unsam.phm.uberto.dto.DriverDTO
import ar.edu.unsam.phm.uberto.model.MongoDriver
import ar.edu.unsam.phm.uberto.repository.MongoDriverRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DriverService(
    val driverRepo: MongoDriverRepository,
    private val tripsRepository: TripsRepository,
    private val mongoDriverRepo: MongoDriverRepository
) {

    fun getDriverData(userID: String):MongoDriver{
        val driver = driverRepo.findById(userID).orElseThrow { NotFoundException("Driver with id $userID not found") }
        return driver
    }

    fun getByIdTrip(id: String): MongoDriver =
        mongoDriverRepo.findById(id)
            .orElseThrow { NotFoundException("Driver with id $id not found") }

    @Transactional
    fun updateProfile(dto : DriverDTO, id: String) : ResponseEntity<String> {
        try {
            val driver = getDriverData(id)
            val update = driver.update(dto)
            driverRepo.save(update)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Updated profile")

        } catch (e: NotFoundException) {
            throw NotFoundException("Driver not found")
        }
    }

//    fun getDriversAvailable(date: LocalDateTime, time: Int): List<DriverAvailableDto> {
//        try {
//            val endTime = date.plus(time.toLong(), ChronoUnit.MINUTES)
//            return tripsRepository.getAvailable(date,endTime)
//        } catch ( e : Exception) {
//            throw BusinessException(e.message ?: "Error in the driver search")
//        }
//    }

    fun getByCredentialsId(id: String): MongoDriver =
        driverRepo.findByCredentialsId(id.toLong())//.orElseThrow{throw NotFoundException("Driver no encontrado")}

    fun findAllByIds(ids: List<String>): List<MongoDriver> {
        return driverRepo.findAllById(ids)
    }


}