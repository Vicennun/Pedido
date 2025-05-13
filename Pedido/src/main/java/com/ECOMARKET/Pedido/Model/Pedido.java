package com.ECOMARKET.Pedido.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String nombreCliente;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String direccionEnvio;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String metodopago;

    @Column(nullable=false)  // Esta columna puede ser nula.
    private Date total;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String estado;
    
    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String fechaPedido;

    @Column(nullable=false)  // Esta columna no puede ser nula.
    private String fechaEntrega;
}


