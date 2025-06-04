package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Servicio para operaciones relacionadas con PassNeo en Neo4j.
 */
@Service
@Transactional("neo4jTransactionManager")
open class PassNeoService(
    private val passNeo4jRepository: PassNeo4jRepository
) {

    /**
     * Obtiene todos los PassNeo almacenados.
     */
    fun findAll(): List<PassNeo> = passNeo4jRepository.findAll()

    /**
     * Busca un PassNeo por su ID.
     */
    fun findById(id: Long): PassNeo? = passNeo4jRepository.findById(id).orElse(null)

    /**
     * Guarda o actualiza un PassNeo.
     */
    fun save(passNeo: PassNeo): PassNeo = passNeo4jRepository.save(passNeo)

    /**
     * Elimina un PassNeo por su ID.
     */
    fun deleteById(id: Long) = passNeo4jRepository.deleteById(id)

    fun saveAll(passengers: List<PassNeo>): List<PassNeo> {
        return passNeo4jRepository.saveAll(passengers)
    }
}