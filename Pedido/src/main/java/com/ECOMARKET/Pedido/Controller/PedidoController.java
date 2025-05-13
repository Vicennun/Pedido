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
            //alternativa 2 -> return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pedidos);
        //alternativa 2 -> return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        Pedido productoMuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoMuevo);
    //    return new ResponseEntity<>(productoMuevo, HttpStatus.ACCEPTED);
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

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            Pedido pac = pedidoService.findById(id);
            pac.setId(id);
            pac.setNombreCliente(pedido.getNombreCliente());
            pac.setDireccionEnvio(pedido.getDireccionEnvio());
            pac.setMetodopago(pedido.getMetodopago());
            pac.setTotal(pedido.getTotal());
            pac.setEstado(pedido.getEstado());
            pac.setFechaPedido(pedido.getFechaPedido());

            pedidoService.save(pac);
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
