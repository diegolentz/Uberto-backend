package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class DriverNeo {
    @Id
    @GeneratedValue
    var id: Long? = null
    var name: String = ""
    var driverId: String = ""
}