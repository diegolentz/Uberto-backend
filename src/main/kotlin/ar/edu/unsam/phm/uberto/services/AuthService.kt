package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.stereotype.Service

@Service
class AuthService(val passengerRepo: PassengerRepository, val driverRepo: DriverRepository) {

    fun validateLoginRequest(loginRequest: LoginRequest): Int {
        TODO("Validar credenciales en el repo")
        TODO("Primero username, si existe valida password")
        TODO("Si valida password, toma los datos y extrae el user o driver")
        TODO("POr ahpra devuelve un entero emulando el futuro JWT y/o id de usuario ")
        return 1
    }

    fun validateLogin(loginRequest: LoginRequest): User? {
        val passenger = passengerRepo.search(loginRequest)
        if(passenger == null){
            val driver = driverRepo.search(loginRequest)
            if(driver == null){
                throw BusinessException("credenciales invalidas")
            }
            if(!authenticate(loginRequest, driver)){
                throw BusinessException("credenciales invalidas")
            }
            return driver
        }else{
            if(!authenticate(loginRequest, passenger)){
                throw BusinessException("credenciales invalidas")
            }
            return passenger
        }
    }

    private fun authenticate(loginRequest: LoginRequest, user: User): Boolean{
        return loginRequest.username == user.username && loginRequest.password == user.password
    }
}