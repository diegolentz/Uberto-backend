# Aplicación Backend en Kotlin

# Uberto-backend

Este repositorio contiene el código del backend para **Uberto**, una plataforma diseñada para la gestión eficiente de servicios de transporte bajo demanda. El backend se encarga de administrar usuarios, viajes, conductores, pagos y toda la lógica central necesaria para brindar una experiencia fluida, segura y escalable tanto a pasajeros como a conductores.

## Propósito del Proyecto

El objetivo principal de **Uberto-backend** es ofrecer una API robusta y escalable que permita gestionar todos los procesos relacionados con un servicio de transporte: desde la solicitud de viajes y asignación de conductores hasta el procesamiento de pagos y la gestión de historial de usuarios.

---

## Características Principales

1. **Gestión de Usuarios y Autenticación**
   - Registro y autenticación segura de pasajeros y conductores.
   - Manejo de roles y permisos diferenciados.

2. **Administración de Viajes**
   - Creación, seguimiento y finalización de viajes en tiempo real.
   - Asignación automática de conductores y cálculo de rutas.

3. **Gestión de Conductores**
   - Registro, verificación y administración de perfiles de conductores.
   - Monitoreo de estado y ubicación de los conductores activos.

4. **Pagos y Facturación**
   - Procesamiento seguro de pagos y generación de facturas.
   - Soporte para diferentes métodos de pago.

5. **Notificaciones**
   - Envío de notificaciones automáticas sobre el estado de los viajes y actualizaciones relevantes.

6. **API RESTful**
   - Endpoints bien definidos para integración con aplicaciones móviles o web.

---

## 📦 Requisitos Previos

Antes de ejecutar la aplicación, asegurate de tener instalado:

- [JDK 17+](https://adoptium.net/)
- [Gradle](https://gradle.org/) (opcional si usás el wrapper `./gradlew`)
- IDE recomendado: IntelliJ IDEA

## ⚙️ Instalación y Ejecución

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/phm-unsam/backend-2025-grupo3.git

2. **Navegar al directorio del proyecto**:

3. **Construir el proyecto**:
  
   ```bash
   ./gradlew build
   ```
4. **Ejecutar la aplicación**:
   ```bash
   ./gradlew run
   ```
## La aplicación quedará corriendo en http://localhost:8080

🧪 Testing
Podés correr los tests del proyecto con:

   ```bash
   ./gradlew test
   ```

📬 Endpoints principales
Algunos de los endpoints disponibles:

GET /TripScore?userId={id}: Obtener puntuaciones para un usuario.

POST /Trip: Crear un nuevo viaje.

DELETE /TripScore?userId={id}&tripScoreId={id}: Eliminar una recomendación.

GET /Driver/{id}/trips: Obtener viajes asociados a un conductor.

## ⚙️ Docker


Para poder correr el proyecto es necesario levantar los contenedores. Para ello necesitamos 
Correr los scrit bash en la carpeta 
'./scripts/config-init-01.sh' y cuando finalice con exito correr './scripts/config-init-02.sh'
