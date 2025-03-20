package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.dto.LoginResponse
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.Repository
import ar.edu.unsam.phm.uberto.repository.UserRepository
import exceptions.BusinessException
import exceptions.NotFoundException
import exceptions.loginErrorMessageMock
import org.springframework.stereotype.Service

@Service
class AuthService(val passeRepo: PassengerRepository, val driverRepo: DriverRepository) {

    fun validateLoginRequest(loginRequest: LoginRequest): Int {
        TODO("Validar credenciales en el repo")
        TODO("Primero username, si existe valida password")
        TODO("Si valida password, toma los datos y extrae el user o driver")
        TODO("POr ahpra devuelve un entero emulando el futuro JWT y/o id de usuario ")
        return 1
    }

    fun validateLogin(loginRequest: LoginRequest): User? {
        val passe = passeRepo.search(loginRequest)
        if(passe == null){
            return  driverRepo.search(loginRequest)
        }else{
            return passe
        }
    }
}