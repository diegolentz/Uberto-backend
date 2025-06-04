package ar.edu.unsam.phm.uberto.neo4j.configBeans

import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager

@Configuration
class Neo4jConfiguration {

    @Bean("neo4jTransactionManager")
    fun neo4jTransactionManager(driver: Driver): Neo4jTransactionManager {
        return Neo4jTransactionManager(driver)
    }
}