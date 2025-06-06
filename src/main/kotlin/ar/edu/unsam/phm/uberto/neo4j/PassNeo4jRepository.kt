package ar.edu.unsam.phm.uberto.neo4j

import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("PassNeo4jRepository")
interface PassNeo4jRepository : Neo4jRepository<PassNeo, Long> {

    @Query(
        """
        // 1. Identifica al usuario actual (yo) y a sus amigos de amigos (amigoDeAmigo)
        MATCH (yo:PassNeo {id: ${'$'}currentUserId})-[:FRIEND]->(:PassNeo)-[:FRIEND]->(amigoDeAmigo:PassNeo)

        // 2. Asegura que exista al menos un conductor (driver) en común entre 'yo' y 'amigoDeAmigo'
        // Esta línea es la clave para "cuyos drivers coincidan al menos con 1 mio"
        MATCH (yo)-[:DRIVER]->(driver:DriverNeo)<-[:DRIVER]-(amigoDeAmigo)

        // 3. Condiciones para filtrar:
        //    - 'amigoDeAmigo' no debe ser un amigo directo de 'yo'
        //    - 'amigoDeAmigo' no debe ser 'yo' mismo
        WHERE NOT (yo)-[:FRIEND]->(amigoDeAmigo) AND yo <> amigoDeAmigo

        // 4. Retorna los amigos de amigos distintos que cumplen todas las condiciones
        RETURN DISTINCT amigoDeAmigo
    """
    )
    fun findSuggestionsById(@Param("currentUserId") userId: Long): List<PassNeo>
}