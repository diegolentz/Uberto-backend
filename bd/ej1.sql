CREATE OR REPLACE FUNCTION booked_trips(p_id INT)
    RETURNS TABLE(trip_date timestamp, trip_id bigint, pasajero_id bigint)
    LANGUAGE plpgsql AS
$$
BEGIN
RETURN QUERY
SELECT date, id, passenger_id
FROM trip
WHERE passenger_id = p_id
  AND EXTRACT(YEAR FROM date) = EXTRACT(YEAR FROM CURRENT_DATE);
END;
$$;

SELECT * FROM booked_trips(5);
