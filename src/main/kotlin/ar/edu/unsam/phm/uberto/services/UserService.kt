package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.DTO.*
//import ar.edu.unsam.phm.uberto.DTO.UserProfileDto
//import ar.edu.unsam.phm.uberto.DTO.toDTOProfile
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.Repository
import exceptions.NotFoundException
import exceptions.loginErrorMessageMock
import org.springframework.stereotype.Service

@Service
object UserService {
    val passengerRepo: Repository<Passenger> = Repository()
    val tripRepo: Repository<Trip> = Repository()
    val driverRepo: Repository<Driver> = Repository()

    fun getAllUsers(): List<Passenger> {
        return passengerRepo.instances.toMutableList()
    }

    fun getProfile(userId: Int): PassengerProfileDto = passengerRepo.getByID(userId).toDTOProfile()

    fun getAllDrivers(): List<Driver> {
        return driverRepo.instances.toMutableList()
    }

    fun getAllTrips(): List<Trip> {
        return tripRepo.instances.toMutableList()
    }

    fun validateLogin(loginRequest: LoginRequest): LoginResponse {
        if (loginRequest.password != "rooot" || loginRequest.username != "rooot") {
            throw NotFoundException(loginErrorMessageMock)
        }
        return LoginResponse(1)
    }

    fun getFriends(userId: Int): List<FriendDTO> {
        val userFriends = passengerRepo.getByID(userId).friends
        return userFriends.map { it.toFriendDTO() }
    }

    fun updateFriends(friendsList: UpdatedFriends): List<FriendDTO> {
        val friendsIds = friendsList.friends.map { friendId -> passengerRepo.getByID(friendId) }
        val user = passengerRepo.getByID(friendsList.userId)

        if (friendsList.addFriends) friendsIds.map { friendId -> user.addFriend(friendId) }
        else friendsIds.map { friendId -> user.removeFriend(friendId) }

        return getFriends(user.id)
    }

    fun createTrip(trip: TripDTO): TripDTO {

        //"Extraer Cliente"
        //"Extraer Chofer"
        //"Instanciar un viaje(con chofer y cliente)"
        //"Validacion  el chofer esta disponible"
        //"chofer.responseTrip"
        //"agrgar el viaje al repo"

        val client = passengerRepo.getByID(trip.userId)
        val driver = driverRepo.getByID(trip.driverDTO.driverID)

        val newTrip =
            Trip(
                trip.duration,
                trip.numberPassengers,
                trip.date,
                trip.origin,
                trip.destination
            )

        client.requestTrip(newTrip)
        driver.responseTrip(newTrip)// Este metodo tiene que validar si el chofer esta disponible

        tripRepo.create(newTrip)

        return newTrip.toDTO()

    }

//    fun getByIdRaw(userId: Int): User = userRepository.getByID(userId)

//    fun getProfile(userId: Int): UserProfileDto = getByIdRaw(userId).toDTOProfile()
}