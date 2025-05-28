#!/bin/bash
# Configura el sharding ejecutando comandos directamente en el router (mongos)
WAIT_TIME=5

echo "Ejecutando mongosh en el router01..."
docker compose exec router01 mongosh --port 27017 <<EOF
show dbs
EOF

if [ $? -ne 0 ]; then
  echo "Error al ejecutar mongosh en router01. Saliendo."
  exit 1
fi

echo "Esperando $WAIT_TIME segundos..."
sleep $WAIT_TIME

echo "Mostrando sh.status() inicial..."
docker compose exec router01 mongosh --port 27017 <<EOF
sh.status()
EOF
sleep $WAIT_TIME

echo "Configurando sharding de la colección uberto.mongodriver..."
docker compose exec router01 mongosh --port 27017 <<EOF
use uberto
db.mongodriver.createIndex({ id: "hashed" })
sh.shardCollection("uberto.mongodriver", { id: "hashed" })
EOF

if [ $? -ne 0 ]; then
  echo "Error durante la configuración del sharding. Saliendo."
  exit 1
fi

echo "Esperando $WAIT_TIME segundos..."
sleep $WAIT_TIME

echo "Mostrando sh.status() final..."
docker compose exec router01 mongosh --port 27017 <<EOF
sh.status()
EOF

echo "✅ Configuración de Shard Key y Sharding completada."
