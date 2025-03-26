package ar.edu.unsam.phm.uberto.model
//

import ar.edu.unsam.phm.uberto.BalanceAmmountNotValidException
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.shouldBe

class UserSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    var usuario: Passenger = PassengerBuilder().build()
    val simpleDriver: SimpleDriver = SimpleDriver()
    var trip    : Trip = TripBuilder().driver(simpleDriver).build()

    describe(name="Dado un usuario"){
        it(name="Su balance inicial es 0"){
            usuario.balance shouldBeExactly 0.0
        }

        describe(name="Puede agregar dinero") {
            it(name="Balance positivo se ve reflejado"){
                usuario.loadBalance(5000.0)
                usuario.balance shouldBe 5000
            }

            it(name="Balance negativo NO se puede"){
                shouldThrow<BalanceAmmountNotValidException> {
                    usuario.loadBalance(-1.0)
                }
            }

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