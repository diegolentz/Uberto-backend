package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional("neo4jTransactionManager")
open class PassNeoService(
    private val passNeo4jRepository: PassNeo4jRepository
) {

    fun findAll(): List<PassNeo> = passNeo4jRepository.findAll()
    fun findById(id: Long): PassNeo? = passNeo4jRepository.findById(id).orElse(null)
    fun save(passNeo: PassNeo): PassNeo = passNeo4jRepository.save(passNeo)
    fun deleteById(id: Long) = passNeo4jRepository.deleteById(id)
    fun saveAll(passengers: List<PassNeo>): List<PassNeo> {
        return passNeo4jRepository.saveAll(passengers)
    }
}