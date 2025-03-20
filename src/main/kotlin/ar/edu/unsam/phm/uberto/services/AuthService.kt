package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.dto.LoginResponse
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.Repository
import ar.edu.unsam.phm.uberto.repository.UserRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import exceptions.loginErrorMessageMock
import org.springframework.stereotype.Service

@Service
class AuthService(val userRepo: UserRepository) {

    fun validateLoginRequest(loginRequest: LoginRequest): Int {
        TODO("Validar credenciales en el repo")
        TODO("Primero username, si existe valida password")
        TODO("Si valida password, toma los datos y extrae el user o driver")
        TODO("POr ahpra devuelve un entero emulando el futuro JWT y/o id de usuario ")
        return 1
    }

    fun validateLogin(loginRequest: LoginRequest): User? {
        val user = userRepo.search(loginRequest.username)
        if(!validateCredential(user!!, loginRequest)){ //Preguntar como validar aca para no poner !!
            throw BusinessException("Contraseña o Usuario incorrecto")
        }
        return user
    }

    fun validateCredential(user: User, loginRequest: LoginRequest): Boolean  {
        return user.username == loginRequest.username && user.password == loginRequest.password
    }
}