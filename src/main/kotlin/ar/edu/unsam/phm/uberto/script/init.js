db = db.getSiblingDB('nombre_de_tu_db'); // Cambia 'nombre_de_tu_db' por el nombre de tu base de datos
db.dropDatabase(); // Elimina la base de datos si existe
db.createCollection('coleccion_inicial'); // Crea una colección inicial
print('Base de datos inicializada con éxito');