package ar.edu.unsam.phm.uberto.model

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class VehicleSpec : DescribeSpec( {
    isolationMode = IsolationMode.InstancePerTest

    describe("Dado un auto") {
        it("Cuando es nuevo") {
            (carPremium.typeOf() is PremiumDrive)  shouldBe true
        }
        it("Cuando es viejo") {
            (carSimple.typeOf() is SimpleDriver)  shouldBe true
        }
    }
    describe("Dado una moto") {
        it("Cuando se pregunta el tipo") {
            (moto.typeOf() is MotorbikeDriver)  shouldBe true
        }
    }
})