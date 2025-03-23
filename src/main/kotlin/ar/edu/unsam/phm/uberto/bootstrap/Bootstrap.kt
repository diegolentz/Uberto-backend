package ar.edu.unsam.phm.uberto.bootstrap

import ar.edu.unsam.phm.uberto.builder.DriverBuilder
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import ar.edu.unsam.phm.uberto.builder.TripScoreBuilder
import ar.edu.unsam.phm.uberto.model.*
import ar.edu.unsam.phm.uberto.repository.*
import ar.edu.unsam.phm.uberto.services.UserService
import ar.edu.unsam.phm.uberto.services.auth.UserAuthCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.sql.Driver
import java.time.LocalDate
import kotlin.random.Random

@Component
class Bootstrap(
    @Autowired val passengerRepo: PassengerRepository,
    @Autowired val driverRepo: DriverRepository,
    @Autowired val tripRepo: TripsRepository,
    @Autowired val accountsRepo: AuthCredentialsRepository,
    @Autowired val tripScoreRepo: TripScoreRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        createAccounts()
        createPassengers()
        createDrivers()
        createTripScore()
        createTrips()
        driverAddTripMock()
    }

    private fun createAccounts(){
        val account01 = UserAuthCredentials(username="adrian", password="adrian", rol="passenger")
        val account02 = UserAuthCredentials(username="diego", password="diego", rol="passenger")
        val account03 = UserAuthCredentials(username="matias", password="matias", rol="passenger")
        val account04 = UserAuthCredentials(username="pedro", password="pedro", rol="passenger")
        val account05 = UserAuthCredentials(username="valen", password="valen", rol="passenger")
        val account06 = UserAuthCredentials(username="driver1", password="driver1", rol="driver")
        val account07 = UserAuthCredentials(username="driver2", password="driver2", rol="driver")

        val accounts = listOf(account01, account02, account03, account04, account05,account06, account07)

        accounts.forEach { account:UserAuthCredentials ->
            accountsRepo.create(account)
        }
    }
    private fun createPassengers() {
        val users = accountsRepo.instances.filter { it.rol == "passenger" }
        val names = listOf<String>("Adrian", "Diego", "Matias", "Pedro", "Valen")
        val lastNames = listOf<String>("a", "b", "c", "d", "e")
        val ages = listOf<Int>(1,2,3,4,5)
        val balances = listOf<Double>(1.0, 10.0, 100.0, 1000.0, 1000000000.0)
        users.forEachIndexed { index:Int, user:UserAuthCredentials ->
            val passenger = PassengerBuilder()
                .userId(user.id)
                .firstName(names[index])
                .lastName(lastNames[index])
                .age(ages[index])
                .balance(balances[index])
                .build()

            passengerRepo.create(passenger)
        }

    }

    private fun createDrivers() {
        val users = accountsRepo.instances.filter { it.rol == "driver" }
        val names = listOf<String>("Dominic", "Franco")
        val lastNames = listOf<String>("Toretto", "Colapinto")
        val balances = listOf<Double>(200.0, 5000.0)
        val driverType = listOf(PremiumDriver(), SimpleDriver())
        val brand = listOf("Fiat Uno", "Renault 12")
        val serial = listOf("FTG 879", "DEV 666")
        val model = listOf(1980,19999)
        val img = listOf("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSxIABKumHIEfVtFkRWCdlA9qmyHCZyxV6-N7m_c1Xc4MOXv8s61ssMabL5Ny5mdcBpBYG21zMUqikXJ-6K0xK5n8jm58thk8-9MXSGA0w","")

        users.forEachIndexed { index:Int, user:UserAuthCredentials ->
            val driver = DriverBuilder(driverType[index])
                .userId(user.id)
                .firstName(names[index])
                .lastName(lastNames[index])
                .balance(balances[index])
                .brand(brand[index])
                .serial(serial[index])
                .model(model[index])
                .img(img[index])
                .build()

            driverRepo.create(driver)
        }


    }

    private fun createTripScore(){
        val passenger = passengerRepo.searchByUserID(1)
        val score1 = TripScoreBuilder()
            .score(3)
            .date(LocalDate.now())
            .passengerId(passenger!!)
            .message("Excelente viaje")
            .build()

        tripScoreRepo.create(score1)
    }

    private fun createTrips(){
        val trip01 = TripBuilder()
            .driver(driverRepo.searchByUserID(6)!!)
            .passenger(passengerRepo.searchByUserID(1)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-03-21T10:44:10.9267679")
            .build()

        val trip02 = TripBuilder()
            .driver(driverRepo.searchByUserID(7)!!)
            .passenger(passengerRepo.searchByUserID(2)!!)
            .passengerAmmount(1)
            .build()
        val trips = mutableListOf<Trip>(trip01, trip02)
        trips.forEach{trip:Trip ->
            tripRepo.create(trip)
        }
        val score = tripScoreRepo.getByID(1)
        trip01.addScore(score)

    }
    private fun driverAddTripMock(){
        val driver1 = driverRepo.searchByUserID(6)
        val trip1 = tripRepo.getByID(1)
        driver1!!.addTrip(trip1)
    }
}