package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.DTO.*
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.stereotype.Service

@Service
class PassengerService(val passengerRepository: PassengerRepository) {

    fun getCurrentPassenger(passengerId: Int) = passengerRepository.getByID(passengerId)

    fun getPassenger(passengerId: Int): PassengerProfileDto {
        return getCurrentPassenger(passengerId).toDTOProfile()
    }

    fun getFriends(passengerId: Int): List<PassengerProfileDto> {
        val friends: List<Passenger> = passengerRepository.getByID(passengerId).friends
        return friends.map { it.toDTOProfile() }
    }

    fun updateFriends(passengerId: Int, modifiedFriends: UpdatedFriends): List<PassengerProfileDto> {
        val modifiedFriendsList = modifiedFriends.friends.map { passengerRepository.getByID(it) }
        val passenger = getCurrentPassenger(passengerId)

        if (modifiedFriends.addFriends) passenger.addFriends(modifiedFriendsList)
        else passenger.removeFriends(modifiedFriendsList)

        return getFriends(passengerId)
    }

    fun addBalance(passengerId: Int, balance: Double): BalanceDTO {
        val currentPassenger = getCurrentPassenger(passengerId)
        currentPassenger.loadBalance(balance)
        return currentPassenger.balanceDTO()
    }

    fun updateInfo(passengerId: Int, updatedInfo: UpdatedPassengerDTO): PassengerProfileDto {
        val passenger = getCurrentPassenger(passengerId)
        updatedInfo.firstName?.let { passenger.firstName = it }
        updatedInfo.lastName?.let { passenger.lastName = it }
        updatedInfo.cellphone?.let { passenger.cellphone = it }
        passengerRepository.update(passenger)
        return passenger.toDTOProfile()
    }
}
