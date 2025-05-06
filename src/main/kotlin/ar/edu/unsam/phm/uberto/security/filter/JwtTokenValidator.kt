package ar.edu.unsam.phm.uberto.security.filter

import ar.edu.unsam.phm.uberto.security.TokenJwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtTokenValidator(
    @Autowired val jwtUtils: TokenJwtUtil
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            val jwtToken = authHeader.substring(7)
            val decodedJWT = jwtUtils.validateToken(jwtToken)
            val username = jwtUtils.extractUsername(decodedJWT)
            val roles = jwtUtils.getSpecificClaim(decodedJWT, "rol")
                .asList(String::class.java)
                .map { SimpleGrantedAuthority(it) }
            val context :  SecurityContext = SecurityContextHolder.getContext()
            val authentication : Authentication = UsernamePasswordAuthenticationToken(username, null, roles)
            context.authentication = authentication
            SecurityContextHolder.setContext(context)
        }

        filterChain.doFilter(request, response)
    }

}