package com.ECOMARKET.Pedido.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity  // Marca esta clase como una entidad JPA.
@Table(name= "pedido")  // Especifica el nombre de la tabla en la base de datos.
@Data  // Genera automáticamente getters, setters, equals, hashCode y toString.
@NoArgsConstructor  // Genera un constructor sin argumentos.
@AllArgsConstructor  // Genera un constructor con un argumento por cada campo en la clase.
public class Pedido {

    @Id  // Especifica el identificador primario.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // El valor del ID se generará automáticamente.
    private Integer id;

    @Column(nullable=false)  // Define las restricciones para la columna en la tabla.
    private Long clienteId;

    @Column(nullable=false)  // Define las restricciones para la columna en la tabla.
    private String nombreCliente;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String direccionEnvio;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String metodopago;

    @Column(nullable=false)  // Esta columna puede ser nula.
    private double total;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String estado;
    
    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String fechaPedido;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String fechaEntrega;
}

/*  [
  {
    "id": 1,
    "clienteId": 12345,
    "nombreCliente": "Juan Pérez",
    "direccionEnvio": "Calle Falsa 123, Ciudad",
    "metodopago": "Tarjeta de Crédito",
    "total": 194400,
    "estado": "Pendiente",
    "fechaPedido": "2025-05-29",
    "fechaEntrega": "2025-06-01"
  },
  {
    "id": 2,
    "clienteId": 67890,
    "nombreCliente": "María López",
    "direccionEnvio": "Avenida Siempre Viva 742, Ciudad",
    "metodopago": "PayPal",
    "total": 50000,
    "estado": "Enviado",
    "fechaPedido": "2025-05-28",
    "fechaEntrega": "2025-06-02"
  },
  {
    "id": 3,
    "clienteId": 11223,
    "nombreCliente": "Carlos García",
    "direccionEnvio": "Calle Luna 456, Ciudad",
    "metodopago": "Efectivo",
    "total": 750000,
    "estado": "Entregado",
    "fechaPedido": "2025-05-27",
    "fechaEntrega": "2025-05-30"
  },
  {
    "id": 4,
    "clienteId": 44556,
    "nombreCliente": "Ana Torres",
    "direccionEnvio": "Calle Sol 789, Ciudad",
    "metodopago": "Transferencia Bancaria",
    "total": 300000,
    "estado": "Cancelado",
    "fechaPedido": "2025-05-26",
    "fechaEntrega": "2025-06-03"
  },
  {
    "id": 5,
    "clienteId": 77889,
    "nombreCliente": "Luis Martínez",
    "direccionEnvio": "Calle Estrella 321, Ciudad",
    "metodopago": "Tarjeta de Débito",
    "total": 1000000,
    "estado": "Pendiente",
    "fechaPedido": "2025-05-25",
    "fechaEntrega": "2025-06-04"
  },
  {
    "id": 6,
    "clienteId": 99001,
    "nombreCliente": "Sofía Ramírez",
    "direccionEnvio": "Calle Cometa 654, Ciudad",
    "metodopago": "Tarjeta de Crédito",
    "total": 450000,
    "estado": "Enviado",
    "fechaPedido": "2025-05-24",
    "fechaEntrega": "2025-06-05"
  },
  {
    "id": 7,
    "clienteId": 22334,
    "nombreCliente": "Pedro Sánchez",
    "direccionEnvio": "Calle Viento 987, Ciudad",
    "metodopago": "PayPal",
    "total": 120000,
    "estado": "Entregado",
    "fechaPedido": "2025-05-23",
    "fechaEntrega": "2025-05-29"
  },
  {
    "id": 8,
    "clienteId": 55667,
    "nombreCliente": "Laura Fernández",
    "direccionEnvio": "Calle Mar 123, Ciudad",
    "metodopago": "Efectivo",
    "total": 800000,
    "estado": "Cancelado",
    "fechaPedido": "2025-05-22",
    "fechaEntrega": "2025-06-06"
  },
  {
    "id": 9,
    "clienteId": 88990,
    "nombreCliente": "Diego Herrera",
    "direccionEnvio": "Calle Río 456, Ciudad",
    "metodopago": "Transferencia Bancaria",
    "total": 600000,
    "estado": "Pendiente",
    "fechaPedido": "2025-05-21",
    "fechaEntrega": "2025-06-07"
  },
  {
    "id": 10,
    "clienteId": 11234,
    "nombreCliente": "Elena Gómez",
    "direccionEnvio": "Calle Montaña 789, Ciudad",
    "metodopago": "Tarjeta de Débito",0
    "total": 250000,
    "estado": "Enviado",
    "fechaPedido": "2025-05-20",
    "fechaEntrega": "2025-06-08"
  }
] */
