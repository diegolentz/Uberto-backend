package ar.edu.unsam.phm.uberto.model
//

import ar.edu.unsam.phm.uberto.BalanceAmmountNotValidException

import ar.edu.unsam.phm.uberto.*

import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class UserSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    var usuario: Passenger = PassengerBuilder().build()

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
        describe(name="Puede viajar si puede pagar el viaje") {
            val simpleDriver:SimpleDriver = SimpleDriver()
            simpleDriver.basePrice = 10.0
            val trip:Trip = TripBuilder().duration(10).passenger(usuario).driver(simpleDriver).build()
            it(name="Balance suficiente"){
                val balance:Double = 1000000.0
                usuario.loadBalance(balance)
                usuario.requestTrip(trip)
                usuario.balance shouldBeExactly (balance - trip.price())
            }

            it(name="Balance insuficiente"){
                shouldThrow<InsufficientBalanceException> {
                    usuario.requestTrip(trip)
                }
            }

        }

        describe(name="Puede calificar el viaje") {
            val simpleDriver:SimpleDriver = SimpleDriver()
            simpleDriver.basePrice = 10.0
            val balance:Double = 1000000.0
            usuario.loadBalance(balance)
            it(name="Califica el viaje, si el viaje ya termino"){
                val yesterday:LocalDateTime = LocalDateTime.now().minus(1, ChronoUnit.DAYS)
                val trip:Trip = TripBuilder().duration(10).passenger(usuario).driver(simpleDriver).setDate(yesterday.toString()).build()
                usuario.requestTrip(trip)
                trip.finished() shouldBe true
                usuario.scoreTrip(trip, message = "Score message", scorePoints = 5)

            }

            it(name="No puede calificar un viaje pendiente."){
                val tomorrow:LocalDateTime = LocalDateTime.now().plus(1, ChronoUnit.DAYS)
                val trip:Trip = TripBuilder().duration(10).passenger(usuario).driver(simpleDriver).setDate(tomorrow.toString()).build()
                usuario.requestTrip(trip)
                trip.finished() shouldBe false
                shouldThrow<TripNotFinishedException> {
                    usuario.scoreTrip(trip, message = "Score message", scorePoints = 5)
                }
            }

        }

        describe(name="Puede tener amigos") {
            val usuarioAmigo:Passenger = PassengerBuilder().build()
            describe(name="Agrega amigos"){
                it(name="No son amigos, lo agrega exitosamente"){
                    usuario.isFriendOf(usuarioAmigo) shouldBe false
                    usuario.addFriend(usuarioAmigo)
                    usuario.isFriendOf(usuarioAmigo) shouldBe true
                }

                it(name="Ya son amigos, no lo puede volver a agregar"){
                    usuario.addFriend(usuarioAmigo)
                    usuario.isFriendOf(usuarioAmigo) shouldBe true
                    shouldThrow<FriendAlreadyExistException> {
                        usuario.addFriend(usuarioAmigo)
                    }
                }
            }

            describe(name="Elimina amigos"){
                it(name="Elimina amigo correctamente"){
                    usuario.addFriend(usuarioAmigo)
                    usuario.isFriendOf(usuarioAmigo) shouldBe true
                    usuario.removeFriend(usuarioAmigo)
                    usuario.isFriendOf(usuarioAmigo) shouldBe false
                }

                it(name="Si no son amigos, no puede eliminar nada"){
                    usuario.isFriendOf(usuarioAmigo) shouldBe false
                    shouldThrow<FriendNotExistException> {
                        usuario.removeFriend(usuarioAmigo)
                    }
                }
            }

        }
    }
})