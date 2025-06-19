package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BalanceAmmountNotValidException
import ar.edu.unsam.phm.uberto.InsufficientBalanceException
import ar.edu.unsam.phm.uberto.TripNotFinishedException
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class PassengerSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    val passenger = PassengerBuilder().build()

    describe(name = "Given a passsenger") {

        describe(name = "Balance") {
            it(name = "Initial balance of 0.0") {
                passenger.balance shouldBeExactly 0.0
            }

            it(name = "Adding balance") {
                val balance: Double = 5000.0
                passenger.loadBalance(balance)
                passenger.balance shouldBeExactly balance
            }

            describe(name = "Invalid balance") {
                it(name = "Adds 0.0 balance") {
                    val balance: Double = 0.0
                    shouldThrow<BalanceAmmountNotValidException> {
                        passenger.loadBalance(balance)
                    }
                }

                it(name = "Adds negative balance") {
                    val balance: Double = -0.5
                    shouldThrow<BalanceAmmountNotValidException> {
                        passenger.loadBalance(balance)
                    }
                }
            }
        }

        describe(name = "Can book trips") {
            val driver: SimpleDriver = SimpleDriver()
            driver.basePrice = 10.0
            val trip = Trip().apply {
                client = passenger
                this.driver = driver
                price = 100.00
            }
            it(name = "Succesfully booked. Expends balance") {
                val balance: Double = 1000000.0
                passenger.loadBalance(balance)
                passenger.requestTrip(trip)
                passenger.balance shouldBeExactly (balance - trip.price)
            }

            it(name = "Insufficient balance") {
                shouldThrow<InsufficientBalanceException> {
                    passenger.requestTrip(trip)
                }
            }

        }

        describe(name = "Can score trips") {
            val driver: SimpleDriver = SimpleDriver()
            driver.basePrice = 10.0
            val balance: Double = 1000000.0
            passenger.loadBalance(balance)
            it(name = "Can score if trip is finished") {
                val yesterday: LocalDateTime = LocalDateTime.now().minus(1, ChronoUnit.DAYS)
                val trip = Trip().apply {
                    duration = 10
                    client = passenger
                    this.driver = driver
                    date = yesterday
                }
                passenger.requestTrip(trip)
                trip.finished() shouldBe true
                passenger.scoreTrip(trip, message = "Score message", scorePoints = 5)
            }

            it(name = "Trip not finished, cannot score") {
                val tomorrow: LocalDateTime = LocalDateTime.now().plus(1, ChronoUnit.DAYS)
                val trip = Trip().apply {
                    duration = 10
                    client = passenger
                    this.driver = driver
                    date = tomorrow
                }
                passenger.requestTrip(trip)
                trip.finished() shouldBe false
                shouldThrow<TripNotFinishedException> {
                    passenger.scoreTrip(trip, message = "Score message", scorePoints = 5)
                }
            }

        }

    }
})
