package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class PassNeo(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var passengerId: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    var img: String = "",

    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING)
    var friends: MutableList<PassNeo> = mutableListOf(),

    @Relationship(type = "TRAVEL", direction = Relationship.Direction.OUTGOING)
    var drivers: MutableList<DriverNeo> = mutableListOf()
) {

}