package ar.edu.unsam.phm.uberto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@EnableNeo4jRepositories( basePackages = ["ar.edu.unsam.phm.uberto.neo4j"])
@EnableJpaRepositories( basePackages = ["ar.edu.unsam.phm.uberto.repository"])
class Backend2025Grupo3Application

fun main(args: Array<String>) {
	runApplication<Backend2025Grupo3Application>(*args)
}