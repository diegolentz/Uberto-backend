package ar.edu.unsam.phm.uberto.services.auth


import ar.edu.unsam.phm.uberto.InvalidCredentialsException
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import org.springframework.stereotype.Service

@Service
class AuthService(val authRepository: AuthRepository) {


    fun validateLogin(loginRequest: LoginRequest): UserAuthCredentials {
        val user:UserAuthCredentials = authRepository.findByUsername(loginRequest.username) ?: throw InvalidCredentialsException()
        if(invalidPassword(user, loginRequest.password)){
            throw InvalidCredentialsException()
        }
        return user
    }

    private fun invalidPassword(user:UserAuthCredentials, password: String): Boolean{
        return user.password != password
    }
}