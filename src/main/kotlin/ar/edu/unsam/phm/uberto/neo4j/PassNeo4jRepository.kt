package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository("PassNeo4jRepository")
interface PassNeo4jRepository : Neo4jRepository <PassNeo, Long>{


}