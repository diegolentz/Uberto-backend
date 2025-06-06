package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional("neo4jTransactionManager")
open class PassNeoService(
    private val passNeo4jRepository: PassNeo4jRepository
) {

    fun findSuggestionsById(id : Long): List<PassNeo> {
        return passNeo4jRepository.findSuggestionsById(id)
    }
}