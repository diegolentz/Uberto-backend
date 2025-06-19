package ar.edu.unsam.phm.uberto.services

import ar.edu.unsam.phm.uberto.neo4j.PassNeo
import ar.edu.unsam.phm.uberto.neo4j.PassNeo4jRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional("neo4jTransactionManager")
open class PassNeoService(
    private val passNeo4jRepository: PassNeo4jRepository
) {

    fun findSuggestionsById(id: Long): List<PassNeo> {
        return passNeo4jRepository.findSuggestionsById(id)
    }

    fun getFriends(idToken: Long): List<PassNeo> {
        return passNeo4jRepository.findAllFriendsByPassengerId(idToken)
    }

    @Transactional
    fun addFriend(currentPassengerId: Long, friendId: Long): ResponseEntity<String> {
        passNeo4jRepository.addFriendRelationship(currentPassengerId, friendId)
        return ResponseEntity
            .status(HttpStatus.OK).body("You have a new friend!")
    }

    @Transactional
    fun deleteFriend(currentPassengerId: Long, friendId: Long): ResponseEntity<String> {
        passNeo4jRepository.deleteFriendRelationship(currentPassengerId, friendId)
        return ResponseEntity
            .status(HttpStatus.OK).body("Friend succesfully removed")
    }

    fun searchNonFriends(idToken: Long, filter: String): List<PassNeo> {
        return passNeo4jRepository.findPossibleFriends(idToken, filter.lowercase())
    }

}