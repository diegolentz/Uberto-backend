package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DriverSpec : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest
    val client = PassengerBuilder().build()
    val trip = Trip()

    describe("Dado un chofer simple") {
        val simpleDriver: SimpleDriver = SimpleDriver()
        trip.apply {
            driver = simpleDriver
            this.client = client
        }
        simpleDriver.basePrice = 10.0
        it("Cobra su precio base + 1500 * duracion del viaje") {
            simpleDriver.fee(
                trip.duration,
                trip.numberPassengers
            ) shouldBeExactly (simpleDriver.basePrice + 1000.0 * trip.duration)
        }
    }

    describe("Dado un chofer premium") {
        val premiumDriver: PremiumDriver = PremiumDriver()
        trip.apply {
            driver = premiumDriver
            this.client = client
            numberPassengers = 1
        }
        premiumDriver.basePrice = 10.0
        describe("Precio del viaje segun la cantidad de pasajeros") {
            it("Con 1 pasajero cobra: precio base + 2000 * duracion del viaje") {
                premiumDriver.fee(
                    trip.duration,
                    trip.numberPassengers
                ) shouldBeExactly (premiumDriver.basePrice + 2000.0 * trip.duration)
            }
            it("Varios pasajeros") {
                trip.apply {
                    numberPassengers = 2
                }
                premiumDriver.fee(
                    trip.duration,
                    trip.numberPassengers
                ) shouldBeExactly (premiumDriver.basePrice + 1500.0 * trip.duration)
            }
        }
    }

    describe("Dado un chofer de moto") {
        val bikeDriver: BikeDriver = BikeDriver()
        bikeDriver.basePrice = 10.0
        trip.driver = bikeDriver
        describe("Precio del viaje segun la duracion del viaje") {
            it("Duracion menor a 30 minutos: precio base + 500 * duracion del viaje") {
                trip.duration = 29
                bikeDriver.fee(
                    trip.duration,
                    trip.numberPassengers
                ) shouldBeExactly (bikeDriver.basePrice + 500.0 * trip.duration)
            }
            it("DUracion mayor o igual a 30 minutos: precio base + 600 * duracion del viaje") {
                trip.duration = 30
                bikeDriver.fee(
                    trip.duration,
                    trip.numberPassengers
                ) shouldBeExactly (bikeDriver.basePrice + 600.0 * trip.duration)
            }
        }
    }

    describe("Dado un chofer cualquiera") {
        val driver: SimpleDriver = SimpleDriver()
        driver.basePrice = 10.0
        it("Monto inicial de 0") {
            driver.balance shouldBeExactly 0.0
        }

        it("Realiza un viaje y aumenta su monto recaudado") {
            val tomorrow = LocalDateTime.now().plus(1, ChronoUnit.DAYS)
            trip.apply {
                duration = 10
                this.client = client
                this.driver = driver
                date = tomorrow
            }
            driver.balance shouldBeExactly 0.0
            trip.calculatePrePersit()
            driver.responseTrip(trip, trip.duration)
            driver.balance shouldBeExactly driver.fee(trip.duration, trip.numberPassengers)
        }

        describe("Driver solo acepta un viaje dentro de una franja horaria") {
            val tomorrow = LocalDateTime.now().plus(1, ChronoUnit.DAYS)
            trip.apply {
                this.driver = driver
                this.client = client
                date = tomorrow
                duration = 10
            }
            trip.calculatePrePersit()
            it("Si esta disponible, acepta el viaje, y lo guarda en su coleccion") {
                driver.responseTrip(trip, trip.duration)
                driver.trips shouldContain trip
            }
        }

        describe("Su calificacion es el promedio de calificaciones de los viajes realiszados") {
            val yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS)
            val trip = Trip().apply {
                this.driver = driver
                this.client = client
                date = yesterday
                duration = 10
            }
            it("Si NO tiene viajes, su score es 0") {
                driver.scoreAVG() shouldBeExactly 0.0
            }
            it("Si NO tiene calificaciones, su score es 0 ") {
                driver.trips.add(trip) //Se hardcodea un viaje ya finalizado
                trip.score shouldBe null
                driver.scoreAVG() shouldBeExactly 0.0
            }

            describe(name = "Varias calificaciones") {
                val trip2 = Trip().apply {
                    this.driver = driver
                    this.client = client
                    date = yesterday
                    duration = 10
                }
                val trip3 = Trip().apply {
                    this.driver = driver
                    this.client = client
                    date = yesterday
                    duration = 10
                }
                driver.trips.addAll(listOf(trip, trip2, trip3)) //Se hardcodea un viaje ya finalizado
                client.scoreTrip(trip = trip, message = "Maso", scorePoints = 2)
                client.scoreTrip(trip = trip2, message = "Maso", scorePoints = 2)
                client.scoreTrip(trip = trip3, message = "Buenaso", scorePoints = 5)
                it("3 calificaciones, con 2,2 y 5 puntos. Score promedio 3") {
                    driver.scoreAVG() shouldBeExactly (2.0 + 2.0 + 5.0) / 3
                }
                it("Las calificaciones del chofer son las recibidas en los viajes realizados") {
                    driver.getScores() shouldContainAll listOf(trip.score, trip2.score, trip3.score)
                }
            }

        }
    }

})

