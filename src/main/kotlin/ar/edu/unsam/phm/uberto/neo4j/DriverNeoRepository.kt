package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.stereotype.Repository

@Repository("DriverNeoRepository")
interface DriverNeoRepository : Neo4jRepository<DriverNeo, Long> {

    fun findByDriverId(driverId: String): DriverNeo


}