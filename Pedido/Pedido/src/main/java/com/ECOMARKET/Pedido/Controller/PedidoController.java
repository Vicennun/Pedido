package com.ECOMARKET.Pedido.Controller;

import com.ECOMARKET.Pedido.Model.Pedido;
import com.ECOMARKET.Pedido.Service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos  = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        
        }
        return ResponseEntity.ok(pedidos);
        
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        Pedido pedidoNuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.findByClienteId(clienteId);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoService.findByEstado(estado);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            Pedido ped = pedidoService.findById(id);
            ped.setId(id);
            ped.setNombreCliente(pedido.getNombreCliente());
            ped.setDireccionEnvio(pedido.getDireccionEnvio());
            ped.setMetodopago(pedido.getMetodopago());
            ped.setTotal(pedido.getTotal());
            ped.setEstado(pedido.getEstado());
            ped.setFechaPedido(pedido.getFechaPedido());

            pedidoService.save(ped);
            return ResponseEntity.ok(pedido);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

        

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }
}