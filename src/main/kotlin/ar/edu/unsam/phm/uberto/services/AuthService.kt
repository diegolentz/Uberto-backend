package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.model.Driver
import ar.edu.unsam.phm.uberto.repository.Repository
import org.springframework.stereotype.Service

@Service
object AuthService {
    val driverRepository: Repository<Driver> = Repository()

    fun validateLoginRequest(loginRequest: LoginRequest): Int {
        TODO("Validar credenciales en el repo")
        TODO("Primero username, si existe valida password")
        TODO("Si valida password, toma los datos y extrae el user o driver")
        TODO("POr ahpra devuelve un entero emulando el futuro JWT y/o id de usuario ")
        return 1
    }

}