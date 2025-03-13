package ar.edu.unsam.phm.uberto.bootstrap

import ar.edu.unsam.phm.uberto.builder.DriverBuilder
import ar.edu.unsam.phm.uberto.builder.UserBuilder
import ar.edu.unsam.phm.uberto.model.User
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
object Bootstrap : CommandLineRunner {
    private val serviceUser: UserService = UserService
    private val serviceDriver: DriverService = DriverService
    override fun run(vararg args: String?) {
        createUsers()
        createDrivers()
    }

    private fun createUsers() {
        val user02 = User("Adrian", "Perez", "","",5, 20)


        val user01 = UserBuilder()
            .firstName("Adrian")
            .lastName("Perez")
            .age(28)
            .balance(50000.0)
            .build()

        serviceUser.userRepository.create(user01)
    }

    private fun createDrivers() {
        val driver01 = DriverBuilder()
            .firstName("Adrian")
            .lastName("Perez")
            .age(28)
            .balance(50000.0)
            .build()

        serviceDriver.driverRepository.create(driver01)
    }
}