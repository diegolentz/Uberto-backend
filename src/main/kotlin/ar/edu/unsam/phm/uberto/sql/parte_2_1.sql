CREATE OR REPLACE FUNCTION current_year_trips(p_id INT)
    RETURNS TABLE(trip_date timestamp, trip_id bigint, pasajero_id bigint, duracion integer, destino varchar)
    LANGUAGE plpgsql AS
$$
BEGIN
RETURN QUERY
SELECT date, passenger_id, id, duration, destination
FROM trip
WHERE passenger_id = p_id
  AND EXTRACT(YEAR FROM date) = EXTRACT(YEAR FROM CURRENT_DATE);
END;
$$;

SELECT * FROM current_year_trips(5);

DROP Function current_year_trips(p_id INT)