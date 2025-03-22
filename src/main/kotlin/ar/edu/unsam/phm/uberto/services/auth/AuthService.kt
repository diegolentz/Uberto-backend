package ar.edu.unsam.phm.uberto.services.auth

import ar.edu.unsam.phm.uberto.BusinessException
import ar.edu.unsam.phm.uberto.dto.LoginDTO
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.AuthCredentialsRepository
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.stereotype.Service

@Service
class AuthService(val accountsRepo: AuthCredentialsRepository) {

    fun validateLogin(loginRequest: LoginRequest): UserAuthCredentials? {
        if(
            !accountsRepo.instances.any { account: UserAuthCredentials ->
                account.username == loginRequest.username
            }
        ){
            throw BusinessException("Invalid credentials")
        }
        val user:UserAuthCredentials? = accountsRepo.instances.find { account: UserAuthCredentials ->
            account.username == loginRequest.username && account.password == loginRequest.password
        }
        if(user == null) throw BusinessException("Invalid credentials")

        return user

    }

//    private fun authenticate(loginRequest: LoginRequest, user: User): Boolean{
//        return loginRequest.username == user.username && loginRequest.password == user.password
//    }
}