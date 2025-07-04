package com.ECOMARKET.Pedido.Controller;

import com.ECOMARKET.Pedido.Model.Pedido;
import com.ECOMARKET.Pedido.Service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista de todos los pedidos realizados")
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos  = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        
        }
        return ResponseEntity.ok(pedidos);
        
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Guarda un nuevo pedido con sus detalles")
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        // Asignar el pedido a cada detalle antes de guardar y calcular el total
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
            double total = pedido.getDetalles().stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
            pedido.setTotal(total);
        }
        Pedido pedidoNuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Muestra los detalles de un pedido específico")
    public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar pedidos por cliente", description = "Obtiene una lista de pedidos realizados por un cliente específico")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.findByClienteId(clienteId);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar pedidos por estado", description = "Obtiene una lista de pedidos filtrados por su estado")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) {
        List<Pedido> pedidos = pedidoService.findByEstado(estado);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza los detalles de un pedido existente")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            Pedido ped = pedidoService.findById(id);
            ped.setNombreCliente(pedido.getNombreCliente());
            ped.setDireccionEnvio(pedido.getDireccionEnvio());
            ped.setMetodopago(pedido.getMetodopago());
            ped.setEstado(pedido.getEstado());
            ped.setFechaPedido(pedido.getFechaPedido());
            ped.setFechaEntrega(pedido.getFechaEntrega());
            // Actualizar detalles si vienen en el request
            if (pedido.getDetalles() != null) {
                pedido.getDetalles().forEach(detalle -> detalle.setPedido(ped));
                ped.setDetalles(pedido.getDetalles());
                double total = pedido.getDetalles().stream()
                    .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                    .sum();
                ped.setTotal(total);
            }
            pedidoService.save(ped);
            return ResponseEntity.ok(ped);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente por su ID")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}