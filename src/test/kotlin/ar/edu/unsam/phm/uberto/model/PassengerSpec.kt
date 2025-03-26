package ar.edu.unsam.phm.uberto.model

import ar.edu.unsam.phm.uberto.BalanceAmmountNotValidException
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import exceptions.BusinessException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class PassengerSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    val passenger = PassengerBuilder().build()

    describe("Given a passsenger") {
        val friend = PassengerBuilder().build()

        it("balance starts as 0") {
            passenger.balance shouldBe 0
        }

        it("can add balance to the account") {
            val randomMoney = Random.nextDouble(1.5, 100.0)
            passenger.loadBalance(randomMoney)
            passenger.balance shouldBe randomMoney
        }

        it("cant add $0 to the account") {
            shouldThrow<BalanceAmmountNotValidException> {passenger.loadBalance(0.0) }
        }

        it("can add friends") {
            passenger.addFriend(friend)
            passenger.friends shouldContain friend
        }

        it("can delete friends") {
            passenger.addFriend(friend)
            passenger.removeFriend(friend)
            passenger.friends shouldNotContain friend
        }
    }

    describe("When requesting a trip") {
        val trip = Trip()

        it("passenger can book a trip") {
            passenger.loadBalance(trip.priceTrip() + 1.0)
            passenger.requestTrip(trip)
            passenger.finishedTrips() shouldContain trip
        }

        it("cannot request a trip if it is to expensive") {
            val expensiveTrip = Trip(duration = 15)
            passenger.loadBalance(expensiveTrip.priceTrip() - 1.0)
            shouldThrow<BusinessException> { passenger.requestTrip(expensiveTrip) }
        }

        it("the balance is reduced succesfully") {
            passenger.loadBalance(trip.priceTrip() + 1.0)
            val originalBalance: Double = passenger.balance
            passenger.requestTrip(trip)
            passenger.balance shouldBe (originalBalance - trip.priceTrip())
        }

        it("Can add scores") {
            val scoreMessage = "test"
            passenger.scoreTrip(trip, scoreMessage, Random.nextInt(1,5))
            trip.score!!.message shouldBe scoreMessage
        }
    }

})
