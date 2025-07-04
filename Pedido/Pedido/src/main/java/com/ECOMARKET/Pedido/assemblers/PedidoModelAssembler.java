package com.ECOMARKET.Pedido.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.ECOMARKET.Pedido.Controller.PedidoController;
import com.ECOMARKET.Pedido.Model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).buscar(pedido.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("pedidos"));
    }
}