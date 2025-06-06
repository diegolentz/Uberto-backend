package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.GeneratedValue as Neo4jGeneratedValue
import org.springframework.data.neo4j.core.schema.Id as Neo4jId

@Node
class PassNeo(
    @Neo4jId
    @Neo4jGeneratedValue
    var id: Long? = null,

    var firstName: String = "",
    var lastName: String = "",

    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING )
    var friends: MutableList<PassNeo> = mutableListOf(),

    @Relationship(type = "DRIVER", direction = Relationship.Direction.OUTGOING)
    var drivers: MutableList<DriverNeo> = mutableListOf()
) {
}