//package ar.edu.unsam.phm.uberto.repository
//
//import ar.edu.unsam.phm.uberto.bootstrap.Bootstrap
//import ar.edu.unsam.phm.uberto.builder.DriverBuilder
//import ar.edu.unsam.phm.uberto.model.SimpleDriver
//import ar.edu.unsam.phm.uberto.neo4j.DriverNeoRepository
//import ar.edu.unsam.phm.uberto.neo4j.PassNeo4jRepository
//import org.junit.jupiter.api.Assertions.assertTrue
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
//import org.springframework.boot.test.mock.mockito.MockBean
//
//// ¡IMPORTANTE!
//// Solo con @DataMongoTest, sin referenciar a Backend2025Grupo3Application.
//// Así, Spring cargará un contexto reducido solo para Mongo y usará
//// la configuración de src/test/resources/application.yml.
//@DataMongoTest
//class DriverJPATest {
//
//    @Autowired
//    lateinit var driverRepository: MongoDriverRepository
//
//    // Mockeamos los beans que no pertenecen al contexto de Mongo
//    // para que Spring no intente crearlos.
//    @MockBean
//    private lateinit var bootstrap: Bootstrap
//
//    @MockBean
//    private lateinit var authRepository: AuthRepository
//
//    @MockBean
//    private lateinit var passNeo4jRepository: PassNeo4jRepository
//
//    @MockBean
//    private lateinit var driverNeoRepository: DriverNeoRepository
//
//
//    @Test
//    fun `dado un id, obtengo el driver con sus trips`() {
//        // Arrange
//        val driver = DriverBuilder(newDriver = SimpleDriver()).build()
//        driverRepository.save(driver)
//
//        // Act
//        val driverQuery = driverRepository.findById(driver.id!!)
//
//        // Assert
//        assertTrue(driverQuery.isPresent)
//    }
//}