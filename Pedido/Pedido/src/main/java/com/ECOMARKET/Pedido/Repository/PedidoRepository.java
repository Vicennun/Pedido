package com.ECOMARKET.Pedido.Repository;

import com.ECOMARKET.Pedido.Model.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByFechaPedido(String fechaPedido);
    List<Pedido> findByEstado(String estado);
    List<Pedido> findByFechaPedidoBetween(String fechaInicio, String fechaFin);
    long countByClienteId(Long clienteId);

    // Ejercicios adicionales
    List<Pedido> findByClienteIdAndFechaPedido(Long clienteId, String fechaPedido);
    List<Pedido> findByEstadoAndClienteId(String estado, Long clienteId);
    List<Pedido> findByClienteIdAndFechaPedidoBetween(Long clienteId, String inicio, String fin);
    List<Pedido> findByEstadoAndFechaPedidoBetween(String estado, String inicio, String fin);
    long countByEstado(String estado);
}
