package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.InvalidCredentialsException
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.repository.AuthRepository
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.model.UserAuthCredentials
import org.springframework.stereotype.Service

@Service
class AuthService(val authRepository: AuthRepository, val driverRepository: DriverRepository, val passengerRepository: PassengerRepository) {


    fun validateLogin(loginRequest: LoginRequest): UserAuthCredentials {
        val user: UserAuthCredentials = authRepository.findByUsername(loginRequest.username) ?: throw InvalidCredentialsException()

        if(invalidPassword(user, loginRequest.password)){
            throw InvalidCredentialsException()
        }
        return user
    }

    private fun invalidPassword(user: UserAuthCredentials, password: String): Boolean{
        return user.password != password
    }
}