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
        createTrips()
        createTripScore()
    }

    private fun createAccounts(){
        val account01 = UserAuthCredentials(username="adrian", password="adrian", rol="passenger")
        val account02 = UserAuthCredentials(username="diego", password="diego", rol="passenger")
        val account03 = UserAuthCredentials(username="matias", password="matias", rol="passenger")
        val account04 = UserAuthCredentials(username="pedro", password="pedro", rol="passenger")
        val account05 = UserAuthCredentials(username="valen", password="valen", rol="passenger")
        val account06 = UserAuthCredentials(username="premium", password="premium", rol="driver")
        val account07 = UserAuthCredentials(username="simple", password="simple", rol="driver")
        val account08 = UserAuthCredentials(username="biker", password="biker", rol="driver")

        val accounts = listOf(account01, account02, account03, account04, account05,account06, account07, account08)

        accounts.forEach { account:UserAuthCredentials ->
            accountsRepo.create(account)
        }
    }
    private fun createPassengers() {
        val users = accountsRepo.instances.filter { it.rol == "passenger" }
        val names = listOf<String>("Adrian", "Diego", "Matias", "Pedro", "Valen")
        val lastNames = listOf<String>("a", "b", "c", "d", "e")
        val ages = listOf<Int>(1,2,3,4,5)
        val balances = listOf<Double>(1000000000.0, 1000000000.0, 1000000000.0, 1000000000.0, 1000000000.0)
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
        val names = listOf<String>("Dominic", "Franco", "Nicky")
        val lastNames = listOf<String>("Toretto", "Colapinto", "Lauda")
        val balances = listOf<Double>(200.0, 5000.0, 10000.0)
        val driverType = listOf(PremiumDriver(), SimpleDriver(), BikeDriver())
        val brand = listOf("Fiat Uno", "Renault 12", "Gilera")
        val serial = listOf("FTG 879", "DEV 666", "AAA 123")
        val model = listOf(1980,1999, 2003)
        val img = listOf(
            "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSxIABKumHIEfVtFkRWCdlA9qmyHCZyxV6-N7m_c1Xc4MOXv8s61ssMabL5Ny5mdcBpBYG21zMUqikXJ-6K0xK5n8jm58thk8-9MXSGA0w",
            "",
            ""
        )

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

    private fun createTrips(){
        var passengers:List<Passenger> = passengerRepo.instances.toList()
        val passengersAmmounts:List<Int> = listOf(1, 1, 2, 3, 4)
        val durations:List<Int> = listOf(10, 25, 40, 15, 20)
        val origin: List<String> = listOf(
            "calleOrigen1",
            "calleOrigen2",
            "calleOrigen3",
            "calleOrigen4",
            "calleOrigen5"
        )
        val destination: List<String> = listOf(
            "calleDestino1",
            "calleDestino2",
            "calleDestino3",
            "calleDestino4",
            "calleDestino5"
        )
        val dates:List<String> = listOf(
            "2025-03-21T10:44:10.9267679",
            "2025-05-21T10:44:10.9267679",
            "2025-03-21T10:44:10.9267679",
            "2025-05-21T10:44:10.9267679",
            "2025-03-21T10:44:10.9267679"
        )

        val drivers:List<Driver> = driverRepo.instances.toList()


        for(j in drivers.indices){
            for (i in passengers.indices){
                val trip = TripBuilder()
                    .driver(drivers[j])
                    .passenger(passengers[i])
                    .passengerAmmount(passengersAmmounts[i])
                    .duration(durations[i])
                    .setDate(dates[i])
                    .origin(origin[i])
                    .destination(destination[i])
                    .build()

                tripRepo.create(trip)
                drivers[j].addTrip(trip)
                passengers[i].requestTrip(trip)
            }
        }

    }


    private fun createTripScore(){
        var passengers:List<Passenger> = passengerRepo.instances.toList()
        val scoresPoints:List<Int> = listOf(3, 2, 1, 3, 4)
        val scoresMessages:List<String> = listOf("Excelente", "Bueno", "Malo", "Malisimo", "Nefasto")
        passengers.forEachIndexed { index:Int, passenger:Passenger ->
            val score = TripScoreBuilder()
                .score(scoresPoints[index])
                .date(LocalDate.now())
                .message(scoresMessages[index])
                .build()

            tripScoreRepo.create(score)
            passenger.trips[0].score = score
        }
    }
}