package ar.edu.unsam.phm.uberto.neo4j

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
}