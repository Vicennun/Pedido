# EcoMarketSPA Pedido API

API RESTful para la gestión de pedidos en EcoMarketSPA. Permite crear, listar, actualizar, buscar y eliminar pedidos asociados a clientes.

## Descripción del Modelo de Datos

- **Usuario** tiene un **Carrito** (1 a 1).
- **Usuario** puede tener muchos **Pedidos** (1 a muchos).
- **Pedido** puede tener muchos **DetallePedido** (1 a muchos).
- **Carrito** puede tener muchos **CarritoItem** (1 a muchos).
- **Producto** puede estar en muchos **CarritoItem** y **DetallePedido** (1 a muchos).

## Modelo:

- **Pedido**
  - `id`: Identificador único del pedido.
  - `clienteId`: Identificador del cliente que realiza el pedido.
  - `nombreCliente`: Nombre del cliente que realiza el pedido.
  - `direccionEnvio`: Dirección de envío del pedido.
  - `metodopago`: Método de pago utilizado.
  - `total`: Total del pedido.
  - `estado`: Estado actual del pedido (ej: PENDIENTE, ENVIADO, ENTREGADO).
  - `fechaPedido`: Fecha en que se realizó el pedido.
  - `fechaEntrega`: Fecha estimada o real de entrega del pedido.
## Endpoints

Base URL: `/api/v1/pedidos`

| Método | Endpoint                    | Descripción                                 | Body (JSON)                  |
|--------|-----------------------------|---------------------------------------------|------------------------------|
| GET    | `/`                         | Listar todos los pedidos                    | -                            |
| GET    | `/{id}`                     | Obtener pedido por ID                       | -                            |
| GET    | `/cliente/{clienteId}`      | Listar pedidos por ID de cliente            | -                            |
| GET    | `/estado/{estado}`          | Listar pedidos por estado                   | -                            |
| POST   | `/`                         | Crear nuevo pedido                          | `{ ...campos del pedido... }`|
| PUT    | `/{id}`                     | Actualizar pedido existente                 | `{ ...campos del pedido... }`|
| DELETE | `/{id}`                     | Eliminar pedido por ID                      | -                            |

### Ejemplo de JSON para POST/PUT

```json
{
  "nombreCliente": "Juan Pérez",
  "direccionEnvio": "Av. Principal 123",
  "metodopago": "Tarjeta de crédito",
  "total": 150.75,
  "estado": "PENDIENTE",
  "fechaPedido": "2025-05-29"
}
```

## Ejemplo de Respuesta

```json
{
  "id": 1,
  "nombreCliente": "Juan Pérez",
  "direccionEnvio": "Av. Principal 123",
  "metodopago": "Tarjeta de crédito",
  "total": 150.75,
  "estado": "PENDIENTE",
  "fechaPedido": "2025-05-29"
}
```

## Requisitos

- Java 17+
- Maven
- MySQL (configurado en AWS)

## Configuración

Configura la conexión a MySQL en `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://<host>:<port>/<database>
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
spring.jpa.hibernate.ddl-auto=update
```

## Ejecución

1. Clona el repositorio.
2. Configura el archivo `application.properties`.
3. Ejecuta la aplicación: `PedidoServiceApplication`

La API estará disponible en `http://localhost:8080/api/v1/pedidos`.

---

> Proyecto de aprendizaje desarrollado con Spring Boot.