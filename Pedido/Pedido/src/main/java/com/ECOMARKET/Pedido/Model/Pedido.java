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
    private Long clienteId;

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

/*
[
  {
    "nombre": "Manzana Roja",
    "marca": "EcoFrut",
    "descripcion": "Manzanas rojas orgánicas, frescas y crujientes.",
    "categoria": "Frutas",
    "precio": 1.20,
    "stock": 150,
    "imagenUrl": "https://ejemplo.com/img/manzana-roja.jpg"
  },
  {
    "nombre": "Leche Descremada",
    "marca": "Lechera Verde",
    "descripcion": "Leche descremada de vaca, sin conservantes.",
    "categoria": "Lácteos",
    "precio": 0.95,
    "stock": 80,
    "imagenUrl": "https://ejemplo.com/img/leche-descremada.jpg"
  },
  {
    "nombre": "Pan Integral",
    "marca": "Panadería Saludable",
    "descripcion": "Pan integral artesanal, rico en fibra.",
    "categoria": "Panadería",
    "precio": 2.50,
    "stock": 60,
    "imagenUrl": "https://ejemplo.com/img/pan-integral.jpg"
  },
  {
    "nombre": "Aceite de Oliva Extra Virgen",
    "marca": "Olivar del Sur",
    "descripcion": "Aceite de oliva extra virgen, prensado en frío.",
    "categoria": "Aceites",
    "precio": 5.75,
    "stock": 40,
    "imagenUrl": "https://ejemplo.com/img/aceite-oliva.jpg"
  },
  {
    "nombre": "Arroz Integral",
    "marca": "Grano Natural",
    "descripcion": "Arroz integral de grano largo, 100% natural.",
    "categoria": "Cereales",
    "precio": 1.80,
    "stock": 120,
    "imagenUrl": "https://ejemplo.com/img/arroz-integral.jpg"
  },
  {
    "nombre": "Yogur Natural",
    "marca": "Lácteos Andes",
    "descripcion": "Yogur natural sin azúcar añadido.",
    "categoria": "Lácteos",
    "precio": 1.10,
    "stock": 90,
    "imagenUrl": "https://ejemplo.com/img/yogur-natural.jpg"
  },
  {
    "nombre": "Zanahoria",
    "marca": "EcoHuerta",
    "descripcion": "Zanahorias frescas y orgánicas.",
    "categoria": "Verduras",
    "precio": 0.60,
    "stock": 200,
    "imagenUrl": "https://ejemplo.com/img/zanahoria.jpg"
  },
  {
    "nombre": "Jabón Ecológico",
    "marca": "BioClean",
    "descripcion": "Jabón ecológico para manos y cuerpo.",
    "categoria": "Higiene",
    "precio": 3.20,
    "stock": 50,
    "imagenUrl": "https://ejemplo.com/img/jabon-ecologico.jpg"
  },
  {
    "nombre": "Café Orgánico Molido",
    "marca": "Café Sierra",
    "descripcion": "Café orgánico molido, aroma intenso.",
    "categoria": "Bebidas",
    "precio": 4.90,
    "stock": 70,
    "imagenUrl": "https://ejemplo.com/img/cafe-organico.jpg"
  },
  {
    "nombre": "Galletas Integrales",
    "marca": "Dulce Vida",
    "descripcion": "Galletas integrales con semillas de chía.",
    "categoria": "Snacks",
    "precio": 2.30,
    "stock": 110,
    "imagenUrl": "https://ejemplo.com/img/galletas-integrales.jpg"
  }
]
*/