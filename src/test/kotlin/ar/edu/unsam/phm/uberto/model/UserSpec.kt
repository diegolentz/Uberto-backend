//package ar.edu.unsam.phm.uberto.model
//
//import ar.edu.unsam.phm.uberto.BusinessException
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

class UserSpec: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

//    describe("Dado un usuario"){
//        it("Su balance debe comenzar en 0"){
//            usuario1.balance shouldBe 0
//        }
//
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
//    }
})