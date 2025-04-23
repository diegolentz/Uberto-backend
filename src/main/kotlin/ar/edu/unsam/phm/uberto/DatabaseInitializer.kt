    package ar.edu.unsam.phm.uberto

    import jakarta.annotation.PostConstruct
    import org.springframework.beans.factory.annotation.Autowired
    import org.springframework.context.annotation.Profile
    import org.springframework.jdbc.core.JdbcTemplate
    import org.springframework.stereotype.Component
    import ar.edu.unsam.phm.uberto.sql.CONSTRAINT_BASE_PRICE// Archivo: kotlin/ar/edu/unsam/phm/uberto/sql/SqlConstants.kt
    import ar.edu.unsam.phm.uberto.sql.CREATE_BALANCE_HISTORY_TABLE
    import ar.edu.unsam.phm.uberto.sql.CREATE_REGISTRAR_BALANCE_CHANGE_FUNCTION
    import ar.edu.unsam.phm.uberto.sql.CREATE_TRIGGER_BALANCE_CHANGE


    @Profile("!test")
    @Component
    class DatabaseInitializer {
        //Esta es la manera que encontre para solucionar problemas en el orden
        //de ejecución, preguntar a Nico/Agus si es la manera correcta
        @Autowired
        private lateinit var jdbcTemplate: JdbcTemplate

        @PostConstruct
        fun init() {
            // se ejecuta codigo SQL
            jdbcTemplate.execute(CONSTRAINT_BASE_PRICE.trimIndent())
            jdbcTemplate.execute(CREATE_BALANCE_HISTORY_TABLE.trimIndent())
            jdbcTemplate.execute(CREATE_REGISTRAR_BALANCE_CHANGE_FUNCTION.trimIndent())
            jdbcTemplate.execute(CREATE_TRIGGER_BALANCE_CHANGE.trimIndent())
        }
    }
