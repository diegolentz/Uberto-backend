package ar.edu.unsam.phm.uberto

import ar.edu.unsam.phm.uberto.repository.PassengerRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["ar.edu.unsam.phm.uberto.repository"])
@EnableNeo4jRepositories(basePackages = ["ar.edu.unsam.phm.uberto.neo4j"])
class Backend2025Grupo3Application

fun main(args: Array<String>) {
	runApplication<Backend2025Grupo3Application>(*args)
}
