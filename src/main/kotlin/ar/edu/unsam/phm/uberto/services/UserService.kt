package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.DTO.LoginRequest
import ar.edu.unsam.phm.uberto.DTO.LoginResponse
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.repository.Repository
import exceptions.BusinessException
import exceptions.NotFoundException
import exceptions.loginErrorMessageMock
import org.springframework.stereotype.Service

@Service
object UserService {
    val userRepository: Repository<User> = Repository()

    fun getAllUsers(): List<User> {
        return userRepository.instances.toMutableList()
    }

    fun validateLogin(loginRequest: LoginRequest): LoginResponse {
        if (loginRequest.password!="root" || loginRequest.username!="root"){
            throw NotFoundException(loginErrorMessageMock)
        }
        return LoginResponse(1)
    }
}