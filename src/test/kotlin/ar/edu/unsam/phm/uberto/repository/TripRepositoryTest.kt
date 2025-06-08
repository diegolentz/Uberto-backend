package ar.edu.unsam.phm.uberto.repository

import ar.edu.unsam.phm.uberto.builder.DriverBuilder
import ar.edu.unsam.phm.uberto.builder.PassengerBuilder
import ar.edu.unsam.phm.uberto.builder.TripBuilder
import ar.edu.unsam.phm.uberto.factory.TestFactory
import ar.edu.unsam.phm.uberto.model.Passenger
import ar.edu.unsam.phm.uberto.model.PremiumDriver
import ar.edu.unsam.phm.uberto.model.Trip
import ar.edu.unsam.phm.uberto.model.UserAuthCredentials
import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import ar.edu.unsam.phm.uberto.services.AuthService
import ar.edu.unsam.phm.uberto.services.DriverService
import ar.edu.unsam.phm.uberto.services.PassengerService
import de.flapdoodle.embed.mongo.spring.autoconfigure.EmbeddedMongoAutoConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@SpringBootTest(
    classes = [TripRepositoryTest.TestConfig::class],
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@TestPropertySource(properties = ["de.flapdoodle.mongodb.embedded.version=6.0.4"])
class TripRepositoryTest() {

    @Autowired lateinit var tripRepository: TripsRepository
    @Autowired lateinit var passengerRepository: PassengerRepository
    @Autowired lateinit var driverRepository: MongoDriverRepository

    @SpringBootConfiguration
    @EnableJpaRepositories(basePackageClasses = [TripsRepository::class, PassengerRepository::class])
    @EnableMongoRepositories(basePackageClasses = [MongoDriverRepository::class, AnalyticsRepository::class])
    @EntityScan(basePackageClasses = [Trip::class, Passenger::class])
    @Import(
        // Infraestructura para JPA
        DataSourceAutoConfiguration::class,
        HibernateJpaAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        // Infraestructura para MongoDB
        EmbeddedMongoAutoConfiguration::class,
        MongoAutoConfiguration::class
    )
    internal class TestConfig {}

    @Test
    fun `buscar un trip asociado a un passenger`(){
        //Arrange
        val passenger = Passenger()
        passengerRepository.save(passenger)

        val driver = PremiumDriver()

        val trip = Trip().apply {
            this.driver = driver
            client = passenger
            date = LocalDateTime.now()
        }
        tripRepository.save(trip)

        //Act
        val client = tripRepository.findByClient(passenger)

        //Assert
        assertFalse(client.isEmpty())
    }

    @Test
    fun `buscar un trip asociado a un formulario de driver`(){
        //Arrange
        val passenger =  passengerRepository.save(PassengerBuilder().firstName("Mandarina").build())

        val driver = driverRepository.save(DriverBuilder(PremiumDriver()).build())

        val trip = tripRepository.save(
            Trip().apply {
                client= passenger
                this.driver = driver
                date = LocalDateTime.now()
                numberPassengers = 2
            }

        )

        //Act
        val foundTrip = tripRepository.searchByForm(
            origin = "origen",
            destination = "destino",
            numberPassengers = 2,
            name = "Mandarina",
            driverId = driver.id!!
        )

        //Assert
        assertEquals("destino", foundTrip.get(0).destination, "Los destinos no son iguales")
    }

    @Test
    fun `buscar un trip asociado a un formulario de driver con nombres similares`(){
        //Arrange
        val driver = PremiumDriver()

        val passengers = passengerRepository.saveAll<Passenger>(listOf<Passenger>(
            Passenger().apply {
                firstName="Mandarina"
                lastName = "Manzana"
            },
            Passenger().apply {
                firstName="Mandarina"
                lastName="Solution"
            }
        )).toList()


        tripRepository.saveAll(listOf<Trip>(
            Trip().apply {
                destination="destino"
                origin="origen"
                client=passengers[0]
                date=LocalDateTime.now()
                numberPassengers=2
            },
            Trip().apply {
                destination="destino"
                this.driver=driver
                client=passengers[0]
                origin  = "origen"
                date = LocalDateTime.now()
                numberPassengers = 2
            }

        ))

        //Act
        val foundTrip = tripRepository.searchByForm(
            origin = "origen",
            destination = "destino",
            numberPassengers = 2,
            name = "Mandarina",
            driverId = "driverId")

        //Assert
        assertEquals(2, foundTrip.size, "Deberia tener 2 viajes")
    }

}