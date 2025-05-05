package ar.edu.unsam.phm.uberto.security


import ar.edu.unsam.phm.uberto.repository.AuthRepository
import ar.edu.unsam.phm.uberto.services.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
class ApplicationSecutityConfiguration {

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors{ it.disable() }
            .csrf{ it.disable() }
            .authorizeHttpRequests{authorizeHttpRequests ->
                authorizeHttpRequests.requestMatchers("/login").permitAll()
                authorizeHttpRequests.requestMatchers("/error").permitAll()
                authorizeHttpRequests.requestMatchers(HttpMethod.OPTIONS).permitAll()
                authorizeHttpRequests.requestMatchers(HttpMethod.GET).permitAll()
            }
            .sessionManagement { session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(Customizer.withDefaults())
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun authenticationProvider(authRepo: AuthRepository): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(this.passwordEncoder())
        provider.setUserDetailsService(this.userDetailService(authRepo, passwordEncoder()))
        return provider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder(128, 255, 1, 1024, 1)
    }

    @Bean
    fun userDetailService(authRepo: AuthRepository, passwordEncoder: PasswordEncoder): UserDetailsService {
        return AuthService(authRepo, passwordEncoder)
    }
}