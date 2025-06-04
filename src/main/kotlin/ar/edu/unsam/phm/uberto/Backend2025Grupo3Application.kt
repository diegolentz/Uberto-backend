package ar.edu.unsam.phm.uberto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class Backend2025Grupo3Application

fun main(args: Array<String>) {
	runApplication<Backend2025Grupo3Application>(*args)
}