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
        MATCH (yo:PassNeo {passengerId: ${'$'}currentUserId})-[:FRIEND]->(:PassNeo)-[:FRIEND]->(amigoDeAmigo:PassNeo)

        // 2. Asegura que exista al menos un conductor (driver) en común entre 'yo' y 'amigoDeAmigo'
        // Esta línea es la clave para "cuyos drivers coincidan al menos con 1 mio"
        MATCH (yo)-[:TRAVEL]->(driver:DriverNeo)<-[:TRAVEL]-(amigoDeAmigo)

        // 3. Condiciones para filtrar:
        //    - 'amigoDeAmigo' no debe ser un amigo directo de 'yo'
        //    - 'amigoDeAmigo' no debe ser 'yo' mismo
        WHERE NOT (yo)-[:FRIEND]->(amigoDeAmigo) AND yo <> amigoDeAmigo

        // 4. Retorna los amigos de amigos distintos que cumplen todas las condiciones
        RETURN DISTINCT amigoDeAmigo
    """
    )
    fun findSuggestionsById(@Param("currentUserId") userId: Long): List<PassNeo>

    @Query("""
        MATCH (p:PassNeo {passengerId: ${'$'}currentPassengerId})-[:FRIEND]->(friend:PassNeo)
        RETURN friend
    """)
    fun findAllFriendsByPassengerId(@Param("currentPassengerId") passengerId: Long): List<PassNeo>

    @Query("""
        MATCH (p:PassNeo {passengerId: ${'$'}passengerId})
        OPTIONAL MATCH (p)-[r:FRIEND]->(f:PassNeo)
        RETURN p, collect(r), collect(f)
    """)
    fun findByPassengerId(@Param("passengerId") passengerId: Long): PassNeo

    @Query("""
        // 1. Identifica al usuario actual y a todos los demás pasajeros (posibles amigos)
        MATCH (currentUser:PassNeo {passengerId: ${'$'}passengerId})
        MATCH (potentialFriend:PassNeo)

        // 2. Aplica las condiciones de exclusión y el filtro de búsqueda
        WHERE
          // Excluir al propio usuario
          potentialFriend.passengerId <> ${'$'}passengerId
          // Excluir a los amigos que ya existen
          AND NOT (currentUser)-[:FRIEND]->(potentialFriend)
          // Condición de búsqueda:
          // O el patrón está vacío (trae todo) O el nombre/apellido coincide con el patrón
          AND (
            trim(${'$'}pattern) = '' OR 
            toLower(potentialFriend.firstName) CONTAINS toLower(${'$'}pattern) OR
            toLower(potentialFriend.lastName) CONTAINS toLower(${'$'}pattern)
          )

        // 3. Devuelve los posibles amigos que cumplen las condiciones
        RETURN potentialFriend
    """)
    fun findPossibleFriends(@Param("passengerId")passengerId: Long,@Param("pattern") pattern: String) : List<PassNeo>


}