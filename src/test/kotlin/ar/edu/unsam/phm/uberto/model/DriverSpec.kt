package ar.edu.unsam.phm.uberto.model

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class DriverSpec: DescribeSpec( {
    isolationMode = IsolationMode.InstancePerTest

    describe("Dado un tipo chofer con vehiculo simple") {
        it("para un viaje de 10 min, precio base $100 y un pasajero") {
            driverSimple.travelPrice(viaje10min1pasajero) shouldBe 10100.00
        }
    }
    describe("Dado un tipo chofer con vehiculo premium") {
        it("Cuando el tiempo de viaje es de 10 min y un pasajero") {
            driverPremium.travelPrice(viaje10min1pasajero) shouldBe 20100.00
        }
        it("Cuando el tiempo de viaje es de 10 min y 2 pasajeros") {
            driverPremium.travelPrice(viaje10min2pasajero) shouldBe 15100.00
        }
    }

    describe("Dado un tipo chofer con vehiculo moto") {
        it("Cuando el tiempo de viaje es de 30 min y un pasajero") {
            driverMotor.travelPrice(viaje30min1pasajero) shouldBe 15100.00
        }
        it("Cuando el tiempo de viaje es de 31 min y 2 pasajeros") {
            driverMotor.travelPrice(viaje31min1pasajero) shouldBe 18700.00
        }
    }

})