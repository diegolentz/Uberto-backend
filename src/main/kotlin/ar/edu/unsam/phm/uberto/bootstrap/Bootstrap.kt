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
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
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
//        createTripScore()
    }

    private fun createAccounts(){
        val account01 = UserAuthCredentials(username="adrian", password="adrian", rol="passenger")
        val account02 = UserAuthCredentials(username="diego", password="diegoo", rol="passenger")
        val account03 = UserAuthCredentials(username="matias", password="matias", rol="passenger")
        val account04 = UserAuthCredentials(username="pedro", password="pedroo", rol="passenger")
        val account05 = UserAuthCredentials(username="valen", password="valenn", rol="passenger")
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
        val names = listOf<String>("Adrian", "Diego", "Matias", "Pedro", "Valentin")
        val lastNames = listOf<String>("Perez", "Lentz", "Diaz", "Geragthy", "Pugliese")
        val ages = listOf<Int>(1,2,3,4,5)
        val balances = listOf<Double>(1000000.0, 1000000.0, 1000000.0, 1000000.0, 1000000.0)
        val phones = listOf<Int>(1568568792,1235598763,1556876259,1235468975,1554876255)

        users.forEachIndexed { index:Int, user:UserAuthCredentials ->
            val passenger = PassengerBuilder()
                .userId(user.id)
                .firstName(names[index])
                .lastName(lastNames[index])
                .age(ages[index])
                .cellphone(phones[index])
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

        val drivers:List<Driver> = driverRepo.instances.toList()

        //8 viajes de cada tipo. Premium, simple, biker. 24 viajes
        //Cada usuario tiene que tener minimo 2 viajes.
        val toretto = driverRepo.instances.first{it.lastName == "Toretto"}
        val colapinto = driverRepo.instances.first{it.lastName == "Colapinto"}
        val lauda = driverRepo.instances.first{it.lastName == "Lauda"}

        val adrian = passengerRepo.instances.first{it.firstName == "Adrian"}
        val matias = passengerRepo.instances.first{it.firstName == "Matias"}
        val diego = passengerRepo.instances.first{it.firstName == "Diego"}
        val pedro = passengerRepo.instances.first{it.firstName == "Pedro"}
        val valentin = passengerRepo.instances.first{it.firstName == "Valentin"}

        val lastMonth = LocalDate.now().minus(1, ChronoUnit.MONTHS)
        val pastWeek = LocalDate.now().minus(1, ChronoUnit.WEEKS)
        val yesterday3 = LocalDate.now().minus(3, ChronoUnit.DAYS)
        val yesterday2 = LocalDate.now().minus(2, ChronoUnit.DAYS)
        val yesterday1 = LocalDate.now().minus(1, ChronoUnit.DAYS)
        val today = LocalDate.now()
        val tomorrow1 = LocalDate.now().plus(1, ChronoUnit.DAYS)
        val tomorrow2 = LocalDate.now().plus(1, ChronoUnit.DAYS)
        val tomorrow3 = LocalDate.now().plus(1, ChronoUnit.DAYS)
        val nextWeek = LocalDate.now().plus(1, ChronoUnit.WEEKS)
        val nextMonth = LocalDate.now().plus(1, ChronoUnit.MONTHS)

        val dawn = LocalTime.of(6,0, 0)
        val morning = LocalTime.of(9,0, 0)
        val noon = LocalTime.of(12,0, 0)
        val afternoon = LocalTime.of(15,0, 0)
        val sunset = LocalTime.of(17,0, 0)
        val dusk = LocalTime.of(19,0, 0)
        val night = LocalTime.of(21,0, 0)
        val midnight = LocalTime.of(0,0, 0)


        val tripAdrian01 = TripBuilder().passenger(adrian).driver(toretto)
            .setDate(LocalDateTime.of(lastMonth, morning).toString())
            .duration(50).build()
        val tripAdrian02 = TripBuilder().passenger(adrian).driver(toretto)
            .setDate(LocalDateTime.of(pastWeek, morning).toString())
            .duration(45).build()
        val tripAdrian03 = TripBuilder().passenger(adrian).driver(colapinto)
            .setDate(LocalDateTime.of(today, morning).toString())
            .duration(20).build()
        val tripAdrian04 = TripBuilder().passenger(adrian).driver(lauda)
            .setDate(LocalDateTime.of(tomorrow1, afternoon).toString())
            .duration(70).build()
        val tripAdrian05 = TripBuilder().passenger(adrian).driver(lauda)
            .setDate(LocalDateTime.of(tomorrow1, night).toString())
            .duration(90).build()

        val tripMatias01 = TripBuilder().passenger(matias).driver(toretto)
            .setDate(LocalDateTime.of(pastWeek, morning).toString())
            .duration(15).build()
        val tripMatias02 = TripBuilder().passenger(matias).driver(toretto)
            .setDate(LocalDateTime.of(today, morning).toString())
            .duration(25).build()
        val tripMatias03 = TripBuilder().passenger(matias).driver(toretto)
            .setDate(LocalDateTime.of(today, afternoon).toString())
            .duration(35).build()
        val tripMatias04 = TripBuilder().passenger(matias).driver(toretto)
            .setDate(LocalDateTime.of(today, sunset).toString())
            .duration(45).build()
        val tripMatias05 = TripBuilder().passenger(matias).driver(toretto)
            .setDate(LocalDateTime.of(today, night).toString())
            .duration(55).build()

        val tripDiego01 = TripBuilder().passenger(diego).driver(lauda)
            .setDate(LocalDateTime.of(lastMonth, afternoon).toString())
            .duration(10).build()
        val tripDiego02 = TripBuilder().passenger(diego).driver(lauda)
            .setDate(LocalDateTime.of(lastMonth, afternoon).toString())
            .duration(20).build()
        val tripDiego03 = TripBuilder().passenger(diego).driver(lauda)
            .setDate(LocalDateTime.of(lastMonth, afternoon).toString())
            .duration(30).build()
        val tripDiego04 = TripBuilder().passenger(diego).driver(lauda)
            .setDate(LocalDateTime.of(lastMonth, afternoon).toString())
            .duration(40).build()
        val tripDiego05 = TripBuilder().passenger(diego).driver(lauda)
            .setDate(LocalDateTime.of(tomorrow1, afternoon).toString())
            .duration(50).build()

        val tripPedro01 = TripBuilder().passenger(pedro).driver(colapinto)
            .setDate(LocalDateTime.of(lastMonth,dawn).toString())
            .duration(45).build()
        val tripPedro02 = TripBuilder().passenger(pedro).driver(colapinto)
            .setDate(LocalDateTime.of(lastMonth, morning).toString())
            .duration(45).build()
        val tripPedro03 = TripBuilder().passenger(pedro).driver(colapinto)
            .setDate(LocalDateTime.of(lastMonth, noon).toString())
            .duration(45).build()
        val tripPedro04 = TripBuilder().passenger(pedro).driver(colapinto)
            .setDate(LocalDateTime.of(lastMonth, afternoon).toString())
            .duration(45).build()
        val tripPedro05 = TripBuilder().passenger(pedro).driver(colapinto)
            .setDate(LocalDateTime.of(lastMonth, sunset).toString())
            .duration(55).build()

        val tripValentin01 = TripBuilder().passenger(valentin).driver(toretto)
            .setDate(LocalDateTime.of(tomorrow1, morning).toString())
            .duration(25).build()
        val tripValentin02 = TripBuilder().passenger(valentin).driver(toretto)
            .setDate(LocalDateTime.of(tomorrow1, sunset).toString())
            .duration(25).build()
        val tripValentin03 = TripBuilder().passenger(valentin).driver(toretto)
            .setDate(LocalDateTime.of(tomorrow1, night).toString())
            .duration(45).build()
        val tripValentin04 = TripBuilder().passenger(valentin).driver(toretto)
            .setDate(LocalDateTime.of(tomorrow2, morning).toString())
            .duration(50).build()
        val tripValentin05 = TripBuilder().passenger(valentin).driver(toretto)
            .setDate(LocalDateTime.of(tomorrow3, morning).toString())
            .duration(50).build()

        val allTrips:List<Trip> = listOf(
            tripAdrian01, tripAdrian02, tripAdrian03, tripAdrian04, tripAdrian05,
            tripMatias01, tripMatias02, tripMatias03, tripMatias04, tripMatias05,
            tripDiego01, tripDiego02, tripDiego03, tripDiego04, tripDiego05,
            tripPedro01, tripPedro02, tripPedro03, tripPedro04, tripPedro05,
            tripValentin01, tripValentin02, tripValentin03, tripValentin04, tripValentin05
        )

        allTrips.forEach {
            tripRepo.create(it)
            it.driver.addTrip(it)
            it.client.trips.add(it)
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