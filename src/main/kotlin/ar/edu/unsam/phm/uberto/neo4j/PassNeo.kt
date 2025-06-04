package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Id as Neo4jId
import org.springframework.data.neo4j.core.schema.GeneratedValue as Neo4jGeneratedValue

@Node
class PassNeo(
    @Neo4jId @Neo4jGeneratedValue var id: Long? = null,
    @Property("firstName") var firstName: String = "",
    @Property("lastName") var lastName: String = "",
    @Property("friends") var friends: List<String> = emptyList(),
    @Property("drivers") var drivers: List<String> = emptyList()
) {
    fun addFriend(friend: String) {
        friends = friends + friend
    }

    fun addDriver(driver: String) {
        drivers = drivers + driver
    }
}