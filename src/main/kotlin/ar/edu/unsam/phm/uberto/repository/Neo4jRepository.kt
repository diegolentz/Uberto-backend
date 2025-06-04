package ar.edu.unsam.phm.uberto.neo4j

import ar.edu.unsam.phm.uberto.model.Passenger
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query

interface PassengerNeo4jRepository : Neo4jRepository<Passenger, Long> {

//    @Query("MATCH (p:Passenger)-[:FRIEND]->(f:Passenger) WHERE f.id = $driverId RETURN p")
//    fun findPassengersByDriverId(driverId: Long): List<Passenger>
//
//    @Query("MATCH (p:Passenger)-[:HAS_TRIP]->(:Trip) WITH p, COUNT(*) AS trips WHERE trips > 4 RETURN p")
//    fun findPassengersWithMoreThanFourTrips(): List<Passenger>
//
//    @Query("MATCH (p:Passenger)-[:FRIEND]->(:Passenger)-[:HAS_TRIP]->(d:Driver) WHERE p.id = $passengerId RETURN d")
//    fun findSuggestedDrivers(passengerId: Long): List<Passenger>
}