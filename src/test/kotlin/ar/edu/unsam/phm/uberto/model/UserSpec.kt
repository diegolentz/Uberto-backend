package ar.edu.unsam.phm.uberto.model
//
import ar.edu.unsam.phm.uberto.BusinessException

import ar.edu.unsam.phm.uberto.builder.DriverBuilder
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import ar.edu.unsam.phm.uberto.model.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

class UserSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    var usuario1: Passenger = PassengerBuilder().build()
    val simpleDriver: SimpleDriver = SimpleDriver()
    var trip    : Trip = TripBuilder().driver(simpleDriver).build()

    describe("Dado un usuario"){
        it("Su balance debe comenzar en 0"){
            usuario1.balance shouldBe 0
        }

        it("Si cargo 5000 de saldo el valor deberia ser de 5000 de saldo") {
            usuario1.loadBalance(5000.0)
            usuario1.balance shouldBe 5000
        }
//        describe("Dado un usario que quiere realizar un viaje")
//            usuario1.loadBalance(100000.0)
//            usuario1.requestTrip(trip)
//
//            it("que el viaje tenga asignado al conducto y al viaje"){
//                trip.client shouldBe usuario1
//                trip.driver shouldBe simpleDriver
//                usuario1.trips shouldContain  (trip)
//                simpleDriver.trips shouldContain  (trip)
//            }
//            it("Verificar que el usario tenga el viaje asignado"){
//
//            }
//        it("Su balance no puede estar por debajo de 0"){
//            assertThrows<BusinessException> { usuario1.removeBalance(Random.nextDouble(1.0, 100.0)) }
//        }
//
//        it("El balance se resta correctamente"){
//            val randomMoney = Random.nextDouble(0.0, 100.0)
//            usuario1.addBalance(randomMoney)
//            usuario1.balance shouldBe randomMoney
//        }
//
//        it("Se pueden agregar valoraciones"){
//            val ratingsLength = usuario1.ratings.size
//            usuario1.rateDriver(driverSimple, Random.nextDouble(1.0, 5.0))
//            usuario1.ratings.size shouldBe ratingsLength+1
//        }
    }
})