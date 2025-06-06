package ar.edu.unsam.phm.uberto.neo4j

import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = ["http://localhost:8080", "http://localhost:5173"])
@RestController
@RequestMapping("/suggestion")
class SuggestionController(
    private val jwtUtil: TokenJwtUtil,
    private val passNeoService: PassNeoService
) {

    @GetMapping()
    fun getSuggestions(request: HttpServletRequest): List<PassNeo> {
        val idToken = jwtUtil.getIdFromTokenString(request)
        val suggestions = passNeoService.findSuggestionsById(idToken)

        return suggestions
    }

}