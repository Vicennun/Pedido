package com.ECOMARKET.Pedido.Controller;

import com.ECOMARKET.Pedido.Model.Pedido;
import com.ECOMARKET.Pedido.Service.PedidoService;
import com.ECOMARKET.Pedido.assemblers.PedidoModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Listar todos los pedidos",
        description = "Obtiene una lista de todos los pedidos realizados",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getAllPedidos() {
        List<EntityModel<Pedido>> pedidos = pedidoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Obtener pedido por ID",
        description = "Muestra los detalles de un pedido específico",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pedido encontrado y retornado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró un pedido con el ID proporcionado."
            )
        }
    )
    public EntityModel<Pedido> getPedidoById(@PathVariable Integer id) {
        Pedido pedido = pedidoService.findById(id);
        return assembler.toModel(pedido);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Crear un nuevo pedido",
        description = "Guarda un nuevo pedido con sus detalles",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Pedido creado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Datos inválidos para crear el pedido."
            )
        }
    )
    public ResponseEntity<EntityModel<Pedido>> createPedido(@RequestBody Pedido pedido) {
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
            double total = pedido.getDetalles().stream()
                    .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                    .sum();
            pedido.setTotal(total);
        }
        Pedido newPedido = pedidoService.save(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(newPedido.getId())).toUri())
                .body(assembler.toModel(newPedido));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
        summary = "Actualizar pedido",
        description = "Actualiza los detalles de un pedido existente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Pedido actualizado exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró el pedido a actualizar."
            )
        }
    )
    public ResponseEntity<EntityModel<Pedido>> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        pedido.setId(id);
        if (pedido.getDetalles() != null) {
            pedido.getDetalles().forEach(detalle -> detalle.setPedido(pedido));
            double total = pedido.getDetalles().stream()
                    .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                    .sum();
            pedido.setTotal(total);
        }
        Pedido updatedPedido = pedidoService.save(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(updatedPedido.getId())).toUri())
                .body(assembler.toModel(updatedPedido));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar pedido",
        description = "Elimina un pedido existente por su ID",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Pedido eliminado exitosamente."
            ),
            @ApiResponse(
                responseCode = "404",
                description = "No se encontró el pedido a eliminar."
            )
        }
    )
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(
        summary = "Listar pedidos por cliente",
        description = "Obtiene todos los pedidos realizados por un cliente específico",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por cliente obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByCliente(@PathVariable Long clienteId) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByClienteId(clienteId)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByCliente(clienteId)).withSelfRel());
    }

    @GetMapping("/fecha/{fechaPedido}")
    @Operation(
        summary = "Listar pedidos por fecha",
        description = "Obtiene todos los pedidos realizados en una fecha específica",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por fecha obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByFecha(@PathVariable String fechaPedido) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByFechaPedido(fechaPedido)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByFecha(fechaPedido)).withSelfRel());
    }

    @GetMapping("/estado/{estado}")
    @Operation(
        summary = "Listar pedidos por estado",
        description = "Obtiene todos los pedidos con un estado específico",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por estado obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByEstado(@PathVariable String estado) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByEstado(estado)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByEstado(estado)).withSelfRel());
    }

    @GetMapping("/rango-fechas")
    @Operation(
        summary = "Listar pedidos por rango de fechas",
        description = "Obtiene todos los pedidos realizados entre dos fechas",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por rango de fechas obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByRangoFechas(
            @RequestParam String inicio,
            @RequestParam String fin) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByFechaPedidoBetween(inicio, fin)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByRangoFechas(inicio, fin)).withSelfRel());
    }

    @GetMapping("/cliente/{clienteId}/total")
    @Operation(
        summary = "Obtener total de pedidos por cliente",
        description = "Cuenta el número total de pedidos realizados por un cliente específico",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Total de pedidos por cliente obtenido exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class)
                )
            )
        }
    )
    public ResponseEntity<Long> getTotalPedidosByCliente(@PathVariable Long clienteId) {
        long total = pedidoService.countByClienteId(clienteId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/cliente/{clienteId}/fecha/{fechaPedido}")
    @Operation(
        summary = "Listar pedidos por cliente y fecha",
        description = "Obtiene todos los pedidos realizados por un cliente en una fecha específica",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por cliente y fecha obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByClienteAndFecha(
            @PathVariable Long clienteId,
            @PathVariable String fechaPedido) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByClienteIdAndFechaPedido(clienteId, fechaPedido)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByClienteAndFecha(clienteId, fechaPedido)).withSelfRel());
    }

    @GetMapping("/estado/{estado}/cliente/{clienteId}")
    @Operation(
        summary = "Listar pedidos por estado y cliente",
        description = "Obtiene todos los pedidos con un estado específico realizados por un cliente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por estado y cliente obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByEstadoAndCliente(
            @PathVariable String estado,
            @PathVariable Long clienteId) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByEstadoAndClienteId(estado, clienteId)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByEstadoAndCliente(estado, clienteId)).withSelfRel());
    }

    @GetMapping("/cliente/{clienteId}/rango-fechas")
    @Operation(
        summary = "Listar pedidos por cliente y rango de fechas",
        description = "Obtiene todos los pedidos realizados por un cliente entre dos fechas",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por cliente y rango de fechas obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByClienteAndRangoFechas(
            @PathVariable Long clienteId,
            @RequestParam String inicio,
            @RequestParam String fin) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByClienteIdAndFechaPedidoBetween(clienteId, inicio, fin)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByClienteAndRangoFechas(clienteId, inicio, fin)).withSelfRel());
    }

    @GetMapping("/estado/{estado}/rango-fechas")
    @Operation(
        summary = "Listar pedidos por estado y rango de fechas",
        description = "Obtiene todos los pedidos con un estado específico entre dos fechas",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de pedidos filtrada por estado y rango de fechas obtenida exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class)
                )
            )
        }
    )
    public CollectionModel<EntityModel<Pedido>> getPedidosByEstadoAndRangoFechas(
            @PathVariable String estado,
            @RequestParam String inicio,
            @RequestParam String fin) {
        List<EntityModel<Pedido>> pedidos = pedidoService.findByEstadoAndFechaPedidoBetween(estado, inicio, fin)
            .stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
            linkTo(methodOn(PedidoControllerV2.class).getPedidosByEstadoAndRangoFechas(estado, inicio, fin)).withSelfRel());
    }

    @GetMapping("/estado/{estado}/total")
    @Operation(
        summary = "Obtener total de pedidos por estado",
        description = "Cuenta el número total de pedidos con un estado específico",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Total de pedidos por estado obtenido exitosamente.",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Long.class)
                )
            )
        }
    )
    public ResponseEntity<Long> getTotalPedidosByEstado(@PathVariable String estado) {
        long total = pedidoService.countByEstado(estado);
        return ResponseEntity.ok(total);
    }
}