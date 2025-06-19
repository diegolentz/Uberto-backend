package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import exceptions.NotFoundException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PassengerService(val passengerRepository: PassengerRepository) {
    @Transactional(readOnly = true)
    fun getById(passengerId: Long): Passenger {
        return passengerRepository.findById(passengerId)
            .orElseThrow { NotFoundException("Passenger $passengerId Not Found") }
    }

    @Transactional(readOnly = true)
    fun getByIdTrip(passengerId: Long): Passenger {
        val passenger: Passenger
        try {
            passenger = passengerRepository.getByIdTrip(passengerId)
        } catch (error: DataAccessException) {
            throw NotFoundException("Passenger with id ${passengerId} not found")
        }
        return passenger
    }


    fun addBalance(passenger: Passenger, balance: Double): ResponseEntity<String> {
        passenger.loadBalance(balance)
        passengerRepository.save(passenger)
        return ResponseEntity
            .status(HttpStatus.OK).body("Balance succesfully updated")
    }

    fun updateInfo(
        passenger: Passenger,
        firstName: String?,
        lastName: String?,
        cellphone: Int?
    ): ResponseEntity<String> {
        firstName?.let { passenger.firstName = it }
        lastName?.let { passenger.lastName = it }
        cellphone?.let { passenger.cellphone = it }
        passengerRepository.save(passenger)
        return ResponseEntity
            .status(HttpStatus.OK).body("Profile succesfully updated")
    }


    fun getByCredentialsId(id: Long): Passenger =
        passengerRepository.findByCredentials_Id(id).orElseThrow { throw NotFoundException("Pasajero no encontrado") }
}