#!/bin/bash
#Este se debe ejecutar en segundo lugar. Posee la config del sharding
# Variables para controlar el tiempo de espera (en segundos)
WAIT_TIME=5

echo "Ejecutando mongosh en el router 01..."
docker compose exec router01 mongosh --port 27017 <<EOF
show dbs
EOF
ROUTER_MONGOSH_STATUS=$?
if [ $ROUTER_MONGOSH_STATUS -ne 0 ]; then
  echo "Error al ejecutar mongosh en el router 01 (código de salida: $ROUTER_MONGOSH_STATUS). Saliendo."
  exit 1
else
  echo "mongosh ejecutado exitosamente en el router 01."
fi
echo "Esperando $WAIT_TIME segundos..."
sleep $WAIT_TIME

echo "Mostrando la información de sh.status() inicial..."
docker compose exec router01 mongosh --port 27017 <<EOF
sh.status()
EOF
SH_STATUS_INITIAL=$?
if [ $SH_STATUS_INITIAL -ne 0 ]; then
  echo "Error al ejecutar sh.status() inicial (código de salida: $SH_STATUS_INITIAL). Saliendo."
  exit 1
else
  echo "Información de sh.status() inicial mostrada."
fi
echo "Esperando $WAIT_TIME segundos..."
sleep $WAIT_TIME

echo "Ejecutando comandos dentro del contenedor router-01..."
docker exec -it router-01 bash <<EOF_BASH
echo "Usando la base de datos uberto..."
use uberto
DATABASE_USE_STATUS=\$?
if [ \$DATABASE_USE_STATUS -ne 0 ]; then
  echo "Error al usar la base de datos uberto (código de salida: \$DATABASE_USE_STATUS). Saliendo."
  exit 1
else
  echo "Cambiado a la base de datos uberto."
fi

echo "Creando el índice hasheado en la colección mongodriver..."
db.mongodriver.createIndex({ id: "hashed" })
CREATE_INDEX_STATUS=\$?
if [ \$CREATE_INDEX_STATUS -ne 0 ]; then
  echo "Error al crear el índice hasheado (código de salida: \$CREATE_INDEX_STATUS). Saliendo."
  exit 1
else
  echo "Índice hasheado creado exitosamente."
fi

echo "Shardeando la colección uberto.mongodriver..."
sh.shardCollection("uberto.mongodriver", { id: "hashed" })
SHARD_COLLECTION_STATUS=\$?
if [ \$SHARD_COLLECTION_STATUS -ne 0 ]; then
  echo "Error al shardear la colección uberto.mongodriver (código de salida: \$SHARD_COLLECTION_STATUS). Saliendo."
  exit 1
else
  echo "Colección uberto.mongodriver shardeada exitosamente."
fi

echo "Saliendo de la shell del contenedor router-01."
exit 0
EOF_BASH
ROUTER_EXEC_STATUS=$?
if [ $ROUTER_EXEC_STATUS -ne 0 ]; then
  echo "Error al ejecutar comandos dentro del contenedor router-01 (código de salida: $ROUTER_EXEC_STATUS). Saliendo."
  exit 1
else
  echo "Comandos dentro del contenedor router-01 ejecutados exitosamente."
fi
echo "Esperando $WAIT_TIME segundos..."
sleep $WAIT_TIME

echo "Mostrando la información de sh.status() final..."
docker compose exec router01 mongosh --port 27017 <<EOF
sh.status()
EOF
SH_STATUS_FINAL=$?
if [ $SH_STATUS_FINAL -ne 0 ]; then
  echo "Error al ejecutar sh.status() final (código de salida: $SH_STATUS_FINAL). Saliendo."
  exit 1
else
  echo "Información de sh.status() final mostrada."
fi

echo "Configuración de Shard Key y Sharding completada."