import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.core.DatabaseSelectionProvider
import org.springframework.data.neo4j.core.Neo4jClient
import org.springframework.data.neo4j.core.Neo4jTemplate
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@Configuration
@EnableNeo4jRepositories(basePackages = ["ar.edu.unsam.phm.uberto.neo4j"])
class Neo4jConfig(
    private val driver: Driver,
    private val databaseSelectionProvider: DatabaseSelectionProvider
) {

    @Bean
    fun neo4jClient(): Neo4jClient = Neo4jClient.create(driver)

    @Bean
    fun transactionManager(): Neo4jTransactionManager =
        Neo4jTransactionManager(driver, databaseSelectionProvider)

    @Bean
    fun neo4jTemplate(): Neo4jTemplate =
        Neo4jTemplate(neo4jClient(), databaseSelectionProvider, transactionManager())
}
