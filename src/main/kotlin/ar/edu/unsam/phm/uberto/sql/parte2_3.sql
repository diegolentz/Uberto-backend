SELECT
    p.id AS passenger_id,
    p.first_name AS passenger_name,
    COUNT(t.id) AS pending_trips_count
FROM
    passenger p
        JOIN
    trip t ON p.id = t.passenger_id
WHERE
    (t.date + (t.duration * INTERVAL '1 minute')) > NOW()
GROUP BY
    p.id, p.first_name
HAVING
    COUNT(t.id) > 2  --Cantidad de pasajeros
ORDER BY
    pending_trips_count DESC;