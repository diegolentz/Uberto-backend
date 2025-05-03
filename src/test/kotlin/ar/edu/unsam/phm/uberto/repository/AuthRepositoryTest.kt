package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.services.auth.AuthRepository
import ar.edu.unsam.phm.uberto.services.auth.Role
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals

@DataJpaTest
class AuthRepositoryTest {

    @Autowired
    lateinit var authRepository: AuthRepository

    @Test
    fun findByUsernameFailed(){
        val account: UserAuthCredentials? = authRepository.findByUsername(username="invalid_username")
        assertEquals(expected = null, actual=account)
    }

    @Test
    fun findByUsernameSuccess(){
        authRepository.save(UserAuthCredentials().apply {
            username = "adrian"
            password = "adrian"
            role = Role.PASSENGER
        })
        val account: UserAuthCredentials? = authRepository.findByUsername(username="adrian")
        assertEquals(expected = "adrian", actual=account!!.username)
        assertEquals(expected = Role.PASSENGER, actual=account!!.role)
    }

    @Test
    fun findPassengers(){
        authRepository.save(UserAuthCredentials().apply {
            username = "adrian"
            password = "adrian"
            role = Role.PASSENGER
        })
        authRepository.save(UserAuthCredentials().apply {
            username = "adrian2"
            password = "adrian2"
            role = Role.PASSENGER
        })
        val accounts: List<UserAuthCredentials> = authRepository.findByRole(Role.PASSENGER)
        assertEquals(expected = 2, actual=accounts.size)
    }

    @Test
    fun findDrivers(){
        authRepository.save(UserAuthCredentials().apply {
            username = "adrian"
            password = "adrian"
            role = Role.DRIVER
        })
        authRepository.save(UserAuthCredentials().apply {
            username = "adrian2"
            password = "adrian2"
            role = Role.DRIVER
        })
        val accounts: List<UserAuthCredentials> = authRepository.findByRole(Role.DRIVER)
        assertEquals(expected = 2, actual=accounts.size)
    }

}