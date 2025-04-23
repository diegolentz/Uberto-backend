package ar.edu.unsam.phm.uberto

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @PostConstruct
    fun init() {
        // Crear la tabla balance_history
        val createBalanceHistoryTable = """
            CREATE TABLE IF NOT EXISTS balance_history (
                id SERIAL PRIMARY KEY,
                passenger_id INTEGER REFERENCES passenger(id),
                fecha_modificacion TIMESTAMP DEFAULT now(),
                saldo_anterior NUMERIC,
                saldo_nuevo NUMERIC
            );
        """.trimIndent()

        // Crear la función registrar_cambio_saldo
        val createRegistrarCambioSaldoFunction = """
            CREATE FUNCTION registrar_cambio_saldo()
            RETURNS TRIGGER AS $$
            BEGIN
                -- Solo registrar si el saldo cambió
                IF NEW.saldo IS DISTINCT FROM OLD.saldo THEN
                    INSERT INTO balance_history (passenger_id, saldo_anterior, saldo_nuevo)
                    VALUES (OLD.id, OLD.balance, NEW.balance);
                END IF;
                RETURN NEW;
            END;
            $$ LANGUAGE plpgsql;
        """.trimIndent()

        // Crear el trigger trigger_cambio_saldo
        val createTriggerCambioSaldo = """
            DROP TRIGGER IF EXISTS trigger_cambio_saldo ON passenger;

            CREATE TRIGGER trigger_cambio_saldo
                BEFORE UPDATE ON passenger
                FOR EACH ROW
                EXECUTE FUNCTION registrar_cambio_saldo();
        """.trimIndent()

        // Ejecutar los scripts SQL
        jdbcTemplate.execute(createBalanceHistoryTable)
        jdbcTemplate.execute(createRegistrarCambioSaldoFunction)
        jdbcTemplate.execute(createTriggerCambioSaldo)
    }
}
