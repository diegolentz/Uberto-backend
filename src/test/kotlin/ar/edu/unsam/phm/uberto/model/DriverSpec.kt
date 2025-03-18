package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeExactly

class DriverSpec: DescribeSpec( {
    isolationMode = IsolationMode.InstancePerTest
    val client = PassengerBuilder().build()

    describe("Dado un chofer simple") {
        val simpleDriver:SimpleDriver = SimpleDriver()

        it("Cobra su precio base + 1500 * duracion del viaje") {
            val trip = TripBuilder().driver(simpleDriver).passenger(client).duration(10.0).build()
            simpleDriver.basePrice = 10.0
            simpleDriver.fee(trip) shouldBeExactly (simpleDriver.basePrice + 1000.0 * trip.duration)
        }

    }

    describe("Dado un chofer premium") {
        val premiumDriver:PremiumDriver = PremiumDriver()
        premiumDriver.basePrice = 10.0
        describe("Precio del viaje segun la cantidad de pasajeros") {
            it("Con 1 pasajero cobra: precio base + 2000 * duracion del viaje") {
                val trip = TripBuilder().driver(premiumDriver).passenger(client).passengerAmmount(1).build()
                premiumDriver.fee(trip) shouldBeExactly (premiumDriver.basePrice + 2000.0 * trip.duration)
            }
            it("Varios pasajeros") {
                val trip = TripBuilder().driver(premiumDriver).passenger(client).passengerAmmount(2).build()

                premiumDriver.fee(trip) shouldBeExactly (premiumDriver.basePrice + 1500.0 * trip.duration)
            }
        }
    }

    describe("Dado un chofer de moto") {
        val bikeDriver:BikeDriver = BikeDriver()
        bikeDriver.basePrice = 10.0
        describe("Precio del viaje segun la duracion del viaje") {
            it("Duracion menor a 30 minutos: precio base + 500 * duracion del viaje") {
                val trip = TripBuilder().driver(bikeDriver).passenger(client).duration(29.0).build()
                bikeDriver.fee(trip) shouldBeExactly (bikeDriver.basePrice + 500.0 * trip.duration)
            }
            it("DUracion mayor o igual a 30 minutos: precio base + 600 * duracion del viaje") {
                val trip = TripBuilder().driver(bikeDriver).passenger(client).duration(30.0).build()
                bikeDriver.fee(trip) shouldBeExactly (bikeDriver.basePrice + 600.0 * trip.duration)
            }
        }
    }

})