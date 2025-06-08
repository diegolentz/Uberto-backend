package ar.edu.unsam.phm.uberto.neo4j

import ar.edu.unsam.phm.uberto.FriendAlreadyExistException
import ar.edu.unsam.phm.uberto.FriendNotExistException
import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.GeneratedValue // Renamed import
import org.springframework.data.neo4j.core.schema.Id // Renamed import

@Node
class PassNeo(
    @Id // Using the renamed import
    @GeneratedValue // Using the renamed import
    var id: Long? = null, // Esto es correcto para un ID Long generado por Neo4j
    var passengerId: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    var img: String = "",

    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING )
    var friends: MutableList<PassNeo> = mutableListOf(),

    @Relationship(type = "TRAVEL", direction = Relationship.Direction.OUTGOING)
    var drivers: MutableList<DriverNeo> = mutableListOf()
) {
    fun isFriendOf(passenger: PassNeo) = this.friends.contains(passenger)

    fun addFriend(friend: PassNeo) {
        if (this.isFriendOf(friend)) {
            throw FriendAlreadyExistException()
        }
        friends.add(friend)
    }

    fun removeFriend(friend: PassNeo) {
        if (!this.isFriendOf(friend)) {
            throw FriendNotExistException()
        }
        friends.remove(friend)
    }

}