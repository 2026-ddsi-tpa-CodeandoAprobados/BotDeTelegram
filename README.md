# Telegram Bot - DonaTrack

Este repositorio contiene el código fuente del Bot de Telegram desarrollado para la materia Diseño de Sistemas. El bot actúa como la interfaz de usuario principal, permitiendo a los usuarios interactuar de forma sencilla con el sistema de DonaTrack.

## Arquitectura y Tecnologías

El proyecto está construido con:

* **Java 21**
* **Spring Boot 3.3.0**
* **Telegram Long Polling Bots API**
* **Maven** para la gestión de dependencias.
* **Docker** para su contenedorización y despliegue.


## Variables de Entorno

Para ejecutar este bot localmente o en un contenedor, es necesario configurar las siguientes variables de entorno. Estas variables conectan al bot con Telegram y con los microservicios correspondientes:

| Variable | Descripción |
| ----- | ----- | 
| `TOKEN_BOT` | Token de acceso provisto por el BotFather de Telegram. | 
| `NAME_BOT` | El username de tu bot en Telegram. |
| `DONADORESYENTIDADES_API_URL` | URL del microservicio de Donadores y Entidades.
| `DONACIONES_API_URL` | URL del microservicio de Donaciones. | 
| `LOGISTICA_API_URL` | URL del microservicio de Logística. |  
| `INCENTIVOS_API_URL` | URL del microservicio de Incentivos. | 

## Comandos Disponibles

El bot cuenta con comandos organizados por menús (`/start`, `/donadores`, `/donaciones`, `/logistica`, `/incentivos` y `/Admin`) y acciones específicas divididas por dominio. A continuación se detallan todas las operaciones soportadas:

### Donadores y Entidades

| Comando | Descripción | Parámetros Esperados |
| :--- | :--- | :--- |
| `/registrar_donador` | Registra un nuevo donador en el sistema. | Nombre, Apellido, Edad, Email, DNI, Domicilio |
| `/mis_estadisticas` | Consulta las estadísticas de un donador. | `[ID]` |
| `/consultar_donadores` | Lista todos los donadores registrados. | - |
| `/consultar_donador_id`| Busca los datos de un donador específico. | `[ID]` |
| `/crear_entidad` | Da de alta una nueva entidad benéfica. | Razón social, Domicilio, Teléfono, Correo |
| `/editar_entidad` | Edita la razón social de una entidad. | `[ID]`, Razón social |
| `/consultar_entidades` | Lista todas las entidades benéficas. | - |
| `/consultar_entidad_id`| Busca los datos de una entidad específica. | `[ID]` |
| `/alta_necesidad` | Registra una nueva necesidad material. | EntidadID, Urgencia, Descripcion, Cantidad objetivo, ProductoID, Tipo |
| `/modificar_necesidad` | Modifica la cantidad objetivo de una necesidad. | `[ID]`, Cantidad objetivo |
| `/borrar_necesidad` | Elimina una necesidad del sistema. | `[ID]` |
| `/consultar_necesidades`| Muestra todas las necesidades registradas. | - |
| `/consultar_necesidad` | Consulta los detalles de una necesidad. | `[ID]` |

### Donaciones

| Comando | Descripción | Parámetros Esperados |
| :--- | :--- | :--- |
| `/registrar_donacion` | Ingresa una nueva donación. | DonadorID, DepositoID, Descripcion, ProductoID, Cantantidad, Estado |
| `/modificar_estado` | Cambia el estado de una donación. | `[ID]`, Estado |
| `/consultar_donaciones`| Lista todas las donaciones. | - |
| `/consultar_donacion_id`| Busca una donación por su identificador. | `[ID]` |
| `/crear_categoria` | Crea una nueva categoría de productos. | Nombre, Descripción, SubcategoriaID |
| `/borrar_categoria` | Elimina una categoría. | `[ID]` |
| `/consultar_categorias`| Lista todas las categorías. | - |
| `/crear_identificador` | Crea un identificador (QR, Código de Barras). | Tipo, Descripción |
| `/borrar_identificador`| Elimina un identificador. | `[ID]` |
| `/consultar_identificadores`| Lista todos los identificadores. | - |

### Logística

| Comando | Descripción | Parámetros Esperados |
| :--- | :--- | :--- |
| `/crear_deposito` | Crea un nuevo depósito físico. | Nombre, Dirección, Capacidad Maxima |
| `/modificar_deposito` | Actualiza datos de un depósito existente. | `[ID]`, Nombre, Dirección, Capacidad |
| `/modificar_algoritmo` | Cambia el algoritmo asignado a un depósito. | `[ID]`, Algoritmo |
| `/borrar_deposito` | Elimina un depósito. | `[ID]` |
| `/consultar_depositos` | Lista todos los depósitos. | - |
| `/consultar_deposito_id`| Busca un depósito específico. | `[ID]` |
| `/consultar_stock` | Consulta el stock disponible de un producto. | `[ID]` del producto |
| `/consultar_paquetes` | Visualiza todos los paquetes gestionados. | - |
| `/consultar_asignaciones`| Muestra todas las asignaciones logísticas. | - |
| `/consultar_asignacion_id`| Busca el detalle de una asignación. | `[ID]` |

### Incentivos

| Comando | Descripción | Parámetros Esperados |
| :--- | :--- | :--- |
| `/crear_mision` | Diseña una nueva misión para donadores. | Nombre, InsigniaID, Cantidad Inicio, Cantidad Fin, Tipo |
| `/borrar_mision` | Elimina una misión. | `[ID]` |
| `/consultar_misiones` | Lista todas las misiones configuradas. | - |
| `/consultar_mision_id` | Busca el detalle de una misión. | `[ID]` |
| `/crear_insignia` | Genera una nueva insignia/logro. | Nombre, Descripción |
| `/borrar_insignia` | Elimina una insignia. | `[ID]` |
| `/consultar_insignias` | Lista todas las insignias disponibles. | - |
| `/consultar_insignia_id`| Busca los datos de una insignia específica. | `[ID]` |

