package com.ECOMARKET.Pedido.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    @Column(nullable = false)
    private Integer productoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double precioUnitario;
    
    public double getSubtotal() {
        return this.cantidad * this.precioUnitario;
    }
}

/*
[
  {
    "pedidoId": 1,
    "productoId": 1,
    "cantidad": 2,
    "precioUnitario": 1.20,
    "subtotal": 2.40
  },
  {
    "pedidoId": 1,
    "productoId": 3,
    "cantidad": 1,
    "precioUnitario": 2.50,
    "subtotal": 2.50
  },
  {
    "pedidoId": 2,
    "productoId": 2,
    "cantidad": 4,
    "precioUnitario": 0.95,
    "subtotal": 3.80
  },
  {
    "pedidoId": 2,
    "productoId": 5,
    "cantidad": 2,
    "precioUnitario": 1.80,
    "subtotal": 3.60
  },
  {
    "pedidoId": 3,
    "productoId": 4,
    "cantidad": 1,
    "precioUnitario": 5.75,
    "subtotal": 5.75
  },
  {
    "pedidoId": 3,
    "productoId": 6,
    "cantidad": 3,
    "precioUnitario": 1.10,
    "subtotal": 3.30
  },
  {
    "pedidoId": 4,
    "productoId": 7,
    "cantidad": 5,
    "precioUnitario": 0.60,
    "subtotal": 3.00
  },
  {
    "pedidoId": 5,
    "productoId": 8,
    "cantidad": 2,
    "precioUnitario": 3.20,
    "subtotal": 6.40
  },
  {
    "pedidoId": 6,
    "productoId": 9,
    "cantidad": 1,
    "precioUnitario": 4.90,
    "subtotal": 4.90
  },
  {
    "pedidoId": 7,
    "productoId": 10,
    "cantidad": 4,
    "precioUnitario": 2.30,
    "subtotal": 9.20
  }
]
*/