package ar.edu.unsam.phm.uberto.model

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class TypeDriverSpec : DescribeSpec( {
    isolationMode = IsolationMode.InstancePerTest
    val simpleDriver = SimpleDriver()
    val premiumDriver = PremiumDrive()
    val motorbikeDriver = MotorbikeDriver()
    describe("Dado un tipo SimpleDriver") {
        it("Cuando el tiempo de viaje es de 10 min y un pasajero") {
            simpleDriver.calculatePlus(10,1) shouldBe 10000.00
        }
    }
    describe("Dado un tipo PremiumDriver") {
        it("Cuando el tiempo de viaje es de 10 min y un pasajero") {
            premiumDriver.calculatePlus(10,1) shouldBe 20000.00
        }
        it("Cuando el tiempo de viaje es de 10 min y 2 pasajeros") {
            premiumDriver.calculatePlus(10,2) shouldBe 15000.00
        }
    }

    describe("Dado un tipo Motorbike") {
        it("Cuando el tiempo de viaje es de 30 min y un pasajero") {
            motorbikeDriver.calculatePlus(30,1) shouldBe 15000.00
        }
        it("Cuando el tiempo de viaje es de 31 min y 2 pasajeros") {
            motorbikeDriver.calculatePlus(31,2) shouldBe 18600.00
        }
    }

})