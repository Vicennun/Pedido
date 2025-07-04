package com.ECOMARKET.Pedido.Service;

import com.ECOMARKET.Pedido.Model.Pedido;
import com.ECOMARKET.Pedido.Repository.PedidoRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void delete(Integer id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public List<Pedido> findByFechaPedido(String fechaPedido) {
        return pedidoRepository.findByFechaPedido(fechaPedido);
    }

    public List<Pedido> findByFechaPedidoBetween(String inicio, String fin) {
        return pedidoRepository.findByFechaPedidoBetween(inicio, fin);
    }

    public long countByClienteId(Long clienteId) {
        return pedidoRepository.countByClienteId(clienteId);
    }

    public List<Pedido> findByClienteIdAndFechaPedido(Long clienteId, String fechaPedido) {
        return pedidoRepository.findByClienteIdAndFechaPedido(clienteId, fechaPedido);
    }

    public List<Pedido> findByEstadoAndClienteId(String estado, Long clienteId) {
        return pedidoRepository.findByEstadoAndClienteId(estado, clienteId);
    }

    public List<Pedido> findByClienteIdAndFechaPedidoBetween(Long clienteId, String inicio, String fin) {
        return pedidoRepository.findByClienteIdAndFechaPedidoBetween(clienteId, inicio, fin);
    }

    public List<Pedido> findByEstadoAndFechaPedidoBetween(String estado, String inicio, String fin) {
        return pedidoRepository.findByEstadoAndFechaPedidoBetween(estado, inicio, fin);
    }

    public long countByEstado(String estado) {
        return pedidoRepository.countByEstado(estado);
    }
}