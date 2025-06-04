package ar.edu.unsam.phm.uberto.neo4j

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional("neo4jTransactionManager")
open class PassNeoService(
    private val passNeo4jRepository: PassNeo4jRepository
) {

    fun findAll(): List<Passenger> = passNeo4jRepository.findAll()
    fun findById(id: Long): Passenger? = passNeo4jRepository.findById(id).orElse(null)
    fun save(passNeo: Passenger): Passenger = passNeo4jRepository.save(passNeo)
    fun deleteById(id: Long) = passNeo4jRepository.deleteById(id)
    fun saveAll(passengers: List<Passenger>): List<Passenger> {
        return passNeo4jRepository.saveAll(passengers)
    }
}