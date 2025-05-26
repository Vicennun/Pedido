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
    private PedidoRepository PedidoRepository;

    public List<Pedido> findAll() {
        return PedidoRepository.findAll();
    }

    public Pedido findById(long id) {
        return PedidoRepository.findById(id).get();
    }

    public Pedido save(Pedido pedido) {
        return PedidoRepository.save(pedido);
    }

    public void delete(Long id) {
        PedidoRepository.deleteById(id);
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return PedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findByEstado(String estado) {
        return PedidoRepository.findByEstado(estado);
    }       
}