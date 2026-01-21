# Power Rocket — Backend E-commerce (Java 21 + Spring Boot)

Este es el backend oficial de la plataforma **Power Rocket**, diseñado para gestionar toda la lógica de negocio, transacciones y datos de un e-commerce moderno enfocado en skateboards. Actúa como servidor API REST, procesando peticiones del frontend, gestionando la autenticación segura y persistiendo datos en PostgreSQL.

---

## 🚀 Project Quick Facts

| Característica | Detalle |
| :--- | :--- |
| **Java Version** | 21 |
| **Framework** | Spring Boot 3.2.1 |
| **Build Tool** | Maven |
| **Database** | PostgreSQL (Producción/Dev) / H2 (Testing) |
| **Puerto** | 8080 (Default) |
| **Documentación** | Swagger UI (`/swagger-ui/index.html`) |
| **Autenticación** | JWT (JSON Web Tokens) |
| **Entorno** | Local / Cloud (Railway/Neon) |

---

## 🛠️ Tecnologías y Herramientas

#### Core
*   **Java 21:** Lenguaje base, utilizando las últimas características de rendimiento y sintaxis.
*   **Spring Boot 3.2.1:** Framework principal para configuración rápida y convención sobre configuración.

#### Data
*   **Spring Data JPA:** Abstracción para el acceso a datos.
*   **Hibernate:** Implementación ORM para mapeo Objeto-Relacional.
*   **PostgreSQL Driver:** Conector para base de datos productiva.
*   **H2 Database:** Base de datos en memoria para tests rápidos y aislados.

#### Validation
*   **Bean Validation (Jakarta Validation):** Para validar entradas en DTOs (`@NotNull`, `@Size`, `@Email`...).

#### Security
*   **Spring Security:** Framework robusto de autenticación y autorización.
*   **JWT (jjwt):** Implementación de tokens para sesiones *stateless*.
*   **BCrypt:** Hashing seguro de contraseñas.

#### Docs
*   **SpringDoc OpenAPI (Swagger):** Generación automática de documentación interactiva de la API.

#### Testing
*   **JUnit 5:** Framework estándar de testing.
*   **MockMvc:** Para pruebas de integración de controladores web.
*   **Spring Boot Test:** Contexto de pruebas integrado.

#### Tools
*   **Lombok:** Reducción de código repetitivo (getters, setters, builders).
*   **Dotenv (dotenv-java):** Carga segura de variables de entorno desde archivo `.env`.

---

## ✨ Funcionalidades

*   **Autenticación y Autorización:**
    *   Registro de usuarios (clientes).
    *   Login seguro (devuelve JWT).
    *   Protección de rutas por roles (`ADMIN`, `USUARIO`).
*   **Gestión de Usuarios:**
    *   Perfil de usuario (`/me`).
    *   Listado de usuarios (solo Admin).
*   **Catálogo de Productos:**
    *   Gestión de Categorías (CRUD).
    *   Gestión de Productos con stock, precio, imágenes y destacados.
    *   Filtrado por categoría.
*   **Direcciones:**
    *   Gestión de múltiples direcciones de envío por usuario.
*   **Ordenes de Compra:**
    *   Creación de pedidos validando stock.
    *   Historial de pedidos por usuario.
*   **Contacto:**
    *   Envío de mensajes de soporte/contacto.

---

## 📋 Requisitos Previos

1.  **Java 21** instalado y configurado en el PATH.
2.  **Maven** (o usar el wrapper `mvnw` incluido).
3.  **PostgreSQL** (opcional si usas una instancia en la nube como Neon, pero recomendado para desarrollo local full).

---

## ⚙️ Configuración del Proyecto

El proyecto utiliza un archivo **`.env`** en la raíz para las variables sensibles.

### Variables Requeridas

| KEY | Ejemplo | Descripción | Requerido |
| :--- | :--- | :--- | :--- |
| `DB_URL` | `jdbc:postgresql://localhost:5432/power` | URL JDBC de conexión | **Sí** |
| `DB_USER` | `postgres` | Usuario de la base de datos | **Sí** |
| `DB_PASSWORD` | `secret` | Contraseña de la base de datos | **Sí** |
| `JWT_SECRET` | `404E635...` (Hex o Base64 largo) | Clave firma de tokens (min 256 bits) | **Sí** |
| `JWT_EXPIRATION_MS` | `86400000` | Expiración token (ms). Ej: 1 día. | No (Default: 1 día) |

### Ejemplo de archivo `.env`:
```properties
DB_URL=jdbc:postgresql://ep-mute-king-abcdef.neon.tech/neondb
DB_USER=neondb_owner
DB_PASSWORD=npg_SecretPass123
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION_MS=86400000
```

---

## 🏃‍♂️ Ejecución Local

### 1. Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/power-backend.git
cd power-backend
```

### 2. Configurar Variables
Crea un archivo `.env` en la raíz del proyecto y añade las variables mencionadas arriba con tus credenciales reales (locales o nube).

### 3. Compilar e Instalar dependencias
```bash
mvn clean install
```

### 4. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

### 5. Verificación
Una vez levante (verás logs de Spring Boot), abre tu navegador en:
*   **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
*   **Health Check (simple):** [http://localhost:8080/api/productos](http://localhost:8080/api/productos) (debería devolver JSON vacío o lista).

---

## 🗄️ Base de Datos

*   **Motor:** PostgreSQL.
*   **Esquema:** Gestionado por Hibernate (`ddl-auto`).
    *   **Desarrollo (`local`):** `update` (Actualiza tablas automáticamente).
    *   **Producción (`prod`):** `validate` (Solo valida, no modifica).
    *   **Test:** H2 en memoria (se crea y destruye al volar).
*   **Entidades Principales:** `Usuario`, `Producto`, `Categoria`, `Orden`, `DetalleOrden`, `Direccion`, `MensajeContacto`.

---

## 📚 Documentación de API

La documentación viva está en **Swagger**. Si no puedes acceder, aquí tienes un resumen de los endpoints principales:

| Método | Ruta | Descripción | Auth | body ejemplo |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/auth/register` | Registro usuario | Pública | `{"email": "...", "password": "..."}` |
| **POST** | `/auth/login` | Iniciar sesión | Pública | `{"email": "...", "password": "..."}` |
| **GET** | `/api/tegorias` | Listar categorías | Pública | - |
| **POST** | `/api/productos` | Crear producto | **Admin** | `{"nombre": "...", "precio": 100}` |
| **GET** | `/api/productos` | Listar productos | Pública | - |
| **POST** | `/api/ordenes` | Crear compra | **User** | `{"idProducto": 1, "cantidad": 2}` |

---

## 🔒 Seguridad

*   **Arquitectura:** Stateless con JWT.
*   **Flujo:**
    1.  Cliente envía credenciales a `/auth/login`.
    2.  Servidor valida y retorna `access_token`.
    3.  Cliente envía el token en el header `Authorization: Bearer <token>` en cada petición subsiguiente.
*   **Roles:**
    *   `ROLE_ADMIN`: Acceso total (crear productos, ver todos los usuarios).
    *   `ROLE_USUARIO`: Acceso a sus propios datos (pedidos, perfil, direcciones).

---

## 🧪 Pruebas

El proyecto cuenta con una suite de **tests de integración** completa para todos los módulos, utilizando base de datos en memoria (H2).

### Ejecutar todos los tests
```bash
mvn test
```

### Tipos de tests
*   **Integration Tests (`*IntegrationTest.java`):** Levantan el contexto completo de Spring Boot, base de datos H2 y simulan peticiones HTTP reales con `MockMvc` para validar el flujo de extremo a extremo (Controller -> Service -> Repository -> DB).

---

## 📂 Estructura del Proyecto

La arquitectura sigue un enfoque modular por "dominio" (Feature-based packaging), lo que facilita la escalabilidad.

```text
src/main/java/com/power/backend
├── BackendApplication.java       # Main entry point + Config carga .env
├── exception                     # Manejo global de errores (GlobalExceptionHandler)
├── security                      # Configuración Seguridad y JWT
│   ├── config                    # SecurityFilterChain
│   ├── controller                # AuthController (Login/Register)
│   ├── jwt                       # Lógica de generación/validación JWT
│   └── service                   # AuthService
└── modules                       # Módulos de negocio
    ├── categoria                 # Dominio Categorías (Controller, Service, Repository, Model, DTO)
    ├── direccion                 # Dominio Direcciones
    ├── mensajecontacto           # Dominio Soporte
    ├── orden                     # Dominio Pedidos
    ├── producto                  # Dominio Productos
    └── usuario                   # Dominio Usuarios
```

---

## 👥 Equipo / Autores

*   **Gabriel Lillo** 
*   **Naomi Núñez** 
*   **Jonathan Fernandez** 
*   **Martin Caviedes** 

