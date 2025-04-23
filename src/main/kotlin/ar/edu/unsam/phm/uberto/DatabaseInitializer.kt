    package ar.edu.unsam.phm.uberto

    import jakarta.annotation.PostConstruct
    import org.springframework.beans.factory.annotation.Autowired
    import org.springframework.jdbc.core.JdbcTemplate
    import org.springframework.stereotype.Component

    @Component
    class DatabaseInitializer {
        //Esta es la manera que encontre para solucionar problemas en el orden
        //de ejecución, preguntar a Nico/Agus si es la manera correcta
        @Autowired
        private lateinit var jdbcTemplate: JdbcTemplate

        @PostConstruct
        fun init() {
            //Tabla donde se registrarán los cambios del balance
            val createBalanceHistoryTable = """
                DROP TABLE IF EXISTS balance_history CASCADE;
                CREATE TABLE IF NOT EXISTS balance_history (
                    id SERIAL PRIMARY KEY,
                    passenger_id INTEGER REFERENCES passenger(id),
                    modification_date TIMESTAMP DEFAULT now(),
                    old_balance NUMERIC,
                    new_balance NUMERIC
                );
            """.trimIndent()

            // Función que se ejecuta en el trigger
            val createRegistrarBalanceChangeFunction = """
                DROP FUNCTION IF EXISTS record_balance_change();
                CREATE FUNCTION record_balance_change()
                RETURNS TRIGGER AS $$
                BEGIN
                    IF NEW.balance IS DISTINCT FROM OLD.balance THEN
                        INSERT INTO balance_history (passenger_id, old_balance, new_balance)
                        VALUES (OLD.id, OLD.balance, NEW.balance);
                    END IF;
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;
            """.trimIndent()

            val createTriggerBalanceChange = """
                DROP TRIGGER IF EXISTS trigger_cambio_saldo ON passenger;
                CREATE TRIGGER trigger_cambio_saldo
                    BEFORE UPDATE ON passenger
                    FOR EACH ROW
                    EXECUTE FUNCTION record_balance_change();
            """.trimIndent()

            jdbcTemplate.execute(createBalanceHistoryTable)
            jdbcTemplate.execute(createRegistrarBalanceChangeFunction)
            jdbcTemplate.execute(createTriggerBalanceChange)
        }
    }
