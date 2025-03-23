package ar.edu.unsam.phm.uberto.bootstrap

import ar.edu.unsam.phm.uberto.builder.DriverBuilder
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import ar.edu.unsam.phm.uberto.builder.TripScoreBuilder
import ar.edu.unsam.phm.uberto.model.*
import ar.edu.unsam.phm.uberto.repository.*
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
        val passenger1 = passengerRepo.searchByUserID(1)
        val passenger2 = passengerRepo.searchByUserID(2)
        val passenger3 = passengerRepo.searchByUserID(3)
        val passenger4 = passengerRepo.searchByUserID(4)
        val score1 = TripScoreBuilder()
            .score(3)
            .date(LocalDate.now())
            .passengerId(passenger1!!)
            .message("Excelente viaje")
            .build()

        val score2 = TripScoreBuilder()
            .score(2)
            .date(LocalDate.now())
            .passengerId(passenger2!!)
            .message("Excelente viaje")
            .build()

        val score3 = TripScoreBuilder()
            .score(1)
            .date(LocalDate.now())
            .passengerId(passenger3!!)
            .message("Excelente viaje")
            .build()

        val score4 = TripScoreBuilder()
            .score(3)
            .date(LocalDate.now())
            .passengerId(passenger4!!)
            .message("Excelente viaje")
            .build()

        val score5 = TripScoreBuilder()
            .score(4)
            .date(LocalDate.now())
            .passengerId(passenger1!!)
            .message("Excelente viaje")
            .build()

        val score6 = TripScoreBuilder()
            .score(5)
            .date(LocalDate.now())
            .passengerId(passenger2!!)
            .message("Excelente viaje")
            .build()

        val score7 = TripScoreBuilder()
            .score(4)
            .date(LocalDate.now())
            .passengerId(passenger3!!)
            .message("Excelente viaje")
            .build()

        val score8 = TripScoreBuilder()
            .score(4)
            .date(LocalDate.now())
            .passengerId(passenger4!!)
            .message("Excelente viaje")
            .build()

        tripScoreRepo.create(score1)
        tripScoreRepo.create(score2)
        tripScoreRepo.create(score3)
        tripScoreRepo.create(score4)
        tripScoreRepo.create(score5)
        tripScoreRepo.create(score6)
        tripScoreRepo.create(score7)
        tripScoreRepo.create(score8)
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

        val trip03 = TripBuilder()
            .driver(driverRepo.searchByUserID(6)!!)
            .passenger(passengerRepo.searchByUserID(3)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-03-21T10:44:10.9267679")
            .build()

        val trip04 = TripBuilder()
            .driver(driverRepo.searchByUserID(6)!!)
            .passenger(passengerRepo.searchByUserID(4)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-03-21T10:44:10.9267679")
            .build()
        val trip05 = TripBuilder()
            .driver(driverRepo.searchByUserID(7)!!)
            .passenger(passengerRepo.searchByUserID(1)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-05-21T10:44:10.9267679")
            .build()
        val trip06 = TripBuilder()
            .driver(driverRepo.searchByUserID(7)!!)
            .passenger(passengerRepo.searchByUserID(2)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-05-21T10:44:10.9267679")
            .build()
        val trip07 = TripBuilder()
            .driver(driverRepo.searchByUserID(7)!!)
            .passenger(passengerRepo.searchByUserID(3)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-05-21T10:44:10.9267679")
            .build()
        val trip08 = TripBuilder()
            .driver(driverRepo.searchByUserID(7)!!)
            .passenger(passengerRepo.searchByUserID(4)!!)
            .passengerAmmount(1)
            .duration(10)
            .setDate("2025-05-21T10:44:10.9267679")
            .build()
        val trips = mutableListOf<Trip>(trip01, trip02, trip03, trip04, trip05,trip06, trip07, trip08)
        trips.forEach{trip:Trip ->
            tripRepo.create(trip)
        }
        val score = tripScoreRepo.getByID(1)
        trip01.addScore(score)

    }
    private fun driverAddTripMock(){
        val driver1 = driverRepo.searchByUserID(6)
        val driver2 = driverRepo.searchByUserID(7)
        val passenger1 = passengerRepo.searchByUserID(1)
        val passenger2 = passengerRepo.searchByUserID(2)
        val passenger3 = passengerRepo.searchByUserID(3)
        val passenger4 = passengerRepo.searchByUserID(4)
        val trip1 = tripRepo.getByID(1)
        val trip2 = tripRepo.getByID(2)
        val trip3 = tripRepo.getByID(3)
        val trip4 = tripRepo.getByID(4)
        val trip5 = tripRepo.getByID(5)
        val trip6 = tripRepo.getByID(6)
        val trip7 = tripRepo.getByID(7)
        val trip8 = tripRepo.getByID(8)

        driver1!!.addTrip(trip1)
        driver2!!.addTrip(trip2)
        driver1!!.addTrip(trip3)
        driver2!!.addTrip(trip4)
        driver1!!.addTrip(trip5)
        driver2!!.addTrip(trip6)
        driver2!!.addTrip(trip7)
        driver1!!.addTrip(trip8)

        passenger1!!.addTrip(trip1)
        passenger2!!.addTrip(trip2)
        passenger3!!.addTrip(trip3)
        passenger4!!.addTrip(trip4)
        passenger1!!.addTrip(trip5)
        passenger2!!.addTrip(trip6)
        passenger3!!.addTrip(trip7)
        passenger4!!.addTrip(trip8)
    }
}