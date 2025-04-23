-- Tabla para el historial de cambios de saldo
CREATE TABLE IF NOT EXISTS balance_history (
    id SERIAL PRIMARY KEY,
    passenger_id INTEGER REFERENCES usuarios(id),
    fecha_modificacion TIMESTAMP DEFAULT now(),
    saldo_anterior NUMERIC,
    saldo_nuevo NUMERIC
    );

-- Función para registrar los cambios de saldo
CREATE FUNCTION registrar_cambio_saldo()
RETURNS TRIGGER AS $$
BEGIN
    -- Solo registrar si el saldo cambió
    IF NEW.saldo IS DISTINCT FROM OLD.saldo THEN
        INSERT INTO balance_history (passenger_id, saldo_anterior, saldo_nuevo)
        VALUES (OLD.id, OLD.saldo, NEW.saldo);
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger que llama a la función antes de cada UPDATE en usuarios
DROP TRIGGER IF EXISTS trigger_cambio_saldo ON Passenger;

CREATE TRIGGER trigger_cambio_saldo
    BEFORE UPDATE ON Passenger
    FOR EACH ROW
    EXECUTE FUNCTION registrar_cambio_saldo();
