package ar.edu.unsam.phm.uberto.bootstrap

import ar.edu.unsam.phm.uberto.builder.DriverBuilder
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import ar.edu.unsam.phm.uberto.model.*
import ar.edu.unsam.phm.uberto.repository.DriverRepository
import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import ar.edu.unsam.phm.uberto.repository.TripsRepository
import ar.edu.unsam.phm.uberto.repository.UserRepository
import ar.edu.unsam.phm.uberto.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class Bootstrap(
    @Autowired val userRepo: UserRepository,
    @Autowired val passengerRepo: PassengerRepository,
    @Autowired val driverRepo: DriverRepository,
    @Autowired val tripRepo: TripsRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        createUsers()
        createDrivers()
        createTrips()
    }

    private fun createUsers() {
        val user01 = PassengerBuilder()
            .firstName("Adrian")
            .lastName("Perez")
            .balance(50000.0)
            .build()
        val user02 = PassengerBuilder()
            .firstName("Martin")
            .lastName("McFly")
            .age(17)
            .balance(50000.0)
            .username("Martin1985")
            .password("1234")
            .build()

        val user03 = PassengerBuilder()
            .firstName("Emmett")
            .lastName("Brown")
            .username("Doc")
            .password("1234")
            .age(50)
            .balance(50000.0)
            .build()

        val user04 = PassengerBuilder()
            .firstName("Biff")
            .lastName("Tannen")
            .age(18)
            .balance(50000.0)
            .username("HayAlguienAhi?")
            .password("1234")
            .build()
        val users = mutableListOf<Passenger>(user01, user02, user03, user04)
        users.forEach{user:Passenger ->
            passengerRepo.create(user)
//            userRepo.create(user)
        }

    }

    private fun createDrivers() {
        val driver01 = DriverBuilder(SimpleDriver())
            .firstName("Adrian")
            .lastName("Perez")
            .username("mock")
            .password("mock")
            .balance(25000.0)
            .build()

        val driver02 = DriverBuilder(PremiumDriver())
            .firstName("Dominic")
            .lastName("Toretto")
            .username("RapidoYFurioso?")
            .password("1234")
            .balance(50000.0)
            .build()
        val drivers = mutableListOf<Driver>(driver01, driver02)
        drivers.forEach{driver:Driver ->
            driverRepo.create(driver)
//            userRepo.create(driver)
        }
    }

    private fun createTrips(){
        val trip01 = TripBuilder()
            .driver(driverRepo.getByID(1))
            .passenger(passengerRepo.getByID(2))
            .passengerAmmount(1)
            .build()

        val trip02 = TripBuilder()
            .driver(driverRepo.getByID(1))
            .passenger(passengerRepo.getByID(1))
            .passengerAmmount(1)
            .build()
        val trips = mutableListOf<Trip>(trip01, trip02)
        trips.forEach{trip:Trip ->
            tripRepo.create(trip)
        }
    }
}