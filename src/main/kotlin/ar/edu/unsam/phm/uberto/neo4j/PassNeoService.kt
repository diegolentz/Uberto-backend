package ar.edu.unsam.phm.uberto.neo4j

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    fun getFriends(idToken: Long): List<PassNeo> {
        return passNeo4jRepository.findAllFriendsByPassengerId(idToken)
    }

    @Transactional
    fun addFriend(idToken: Long, friendId: Long): ResponseEntity<String> {
        val currentPassenger = passNeo4jRepository.findByPassengerId(idToken)
        val friend = passNeo4jRepository.findByPassengerId(friendId)
        currentPassenger.friends.add(friend)
        friend.friends.add(currentPassenger)
        passNeo4jRepository.save(currentPassenger)
        passNeo4jRepository.save(friend)
        return ResponseEntity
            .status(HttpStatus.OK).body("You have a new friend!")
    }

    @Transactional
    fun deleteFriend(idToken: Long, friendId: Long): ResponseEntity<String> {

        val currentPassenger = passNeo4jRepository.findByPassengerId(idToken)
        val friend = passNeo4jRepository.findByPassengerId(friendId)
        currentPassenger.removeFriend(friend)
        friend.removeFriend(currentPassenger)
        passNeo4jRepository.save(currentPassenger)
        passNeo4jRepository.save(friend)
        return ResponseEntity
            .status(HttpStatus.OK).body("Friend succesfully removed")

    }

    fun searchNonFriends(idToken: Long, filter: String): List<PassNeo> {
        return passNeo4jRepository.findPossibleFriends(idToken, filter.lowercase())
    }

}