package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.InvalidCredentialsException
import ar.edu.unsam.phm.uberto.dto.LoginRequest
import ar.edu.unsam.phm.uberto.repository.AuthRepository
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.model.UserAuthCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authRepository: AuthRepository,
    val passwordEncoder: PasswordEncoder
): UserDetailsService {

    fun validateLogin(loginRequest: LoginRequest): UserAuthCredentials {
        val user: UserAuthCredentials = this.findUserByUsername(username=loginRequest.username)
        validPassword(loginRequest.password)
        return user
    }
    override fun loadUserByUsername(username: String?): UserDetails? {
        if(username == null) throw InvalidCredentialsException()
        val user: UserAuthCredentials = this.findUserByUsername(username=username)
        return user.mapToUserDetails()
    }

    private fun UserAuthCredentials.mapToUserDetails(): UserDetails {
        return User.builder()
            .username(this.username)
            .password(this.password)
            .roles(this.role.name)
            .build()
    }

    private fun findUserByUsername(username: String): UserAuthCredentials {
        return authRepository.findByUsername(username) ?: throw InvalidCredentialsException()
    }

    fun validPassword(password: String){
        if(passwordEncoder.matches(password, password)){
            throw InvalidCredentialsException()
        }
    }
}