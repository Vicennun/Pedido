package com.ECOMARKET.Pedido;

import com.ECOMARKET.Pedido.Model.Pedido;
import com.ECOMARKET.Pedido.Model.DetallePedido;
import com.ECOMARKET.Pedido.Repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpiar la tabla pedido antes de insertar nuevos datos (opcional)
        pedidoRepository.deleteAll();

        Faker faker = new Faker();
        Random random = new Random();
        Set<Long> clientesGenerados = new HashSet<>(); // Para evitar duplicados de clienteId

        for (int i = 0; i < 10; i++) {
            long clienteId;
            do {
                clienteId = faker.number().numberBetween(1L, 100L);
            } while (clientesGenerados.contains(clienteId));
            clientesGenerados.add(clienteId);

            Pedido pedido = new Pedido();
            pedido.setClienteId(clienteId);
            pedido.setNombreCliente(faker.name().fullName());
            pedido.setDireccionEnvio(faker.address().fullAddress());
            pedido.setMetodopago(faker.options().option("Tarjeta de Crédito", "PayPal", "Efectivo", "Transferencia Bancaria", "Tarjeta de Débito"));
            pedido.setEstado(faker.options().option("Pendiente", "Enviado", "Entregado", "Cancelado"));
            pedido.setFechaPedido(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toString());
            pedido.setFechaEntrega(faker.date().future(10, java.util.concurrent.TimeUnit.DAYS).toString());

            // Generar detalles para el pedido
            List<DetallePedido> detalles = new ArrayList<>();
            int numeroDeDetalles = random.nextInt(5) + 1; // Entre 1 y 5 productos por pedido
            for (int j = 0; j < numeroDeDetalles; j++) {
                DetallePedido detalle = new DetallePedido();
                detalle.setProductoId(faker.number().numberBetween(1, 100));
                detalle.setCantidad(faker.number().numberBetween(1, 10));
                detalle.setPrecioUnitario(
                        BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))
                                .setScale(2, RoundingMode.HALF_UP)
                                .doubleValue()
                );
                detalle.setPedido(pedido); // Relación bidireccional
                detalles.add(detalle);
            }

            pedido.setDetalles(detalles);

            // Calcular el total del pedido
            double total = detalles.stream()
                    .mapToDouble(det -> det.getCantidad() * det.getPrecioUnitario())
                    .sum();
            pedido.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue());

            // Guardar el pedido (y sus detalles gracias a CascadeType.ALL)
            pedidoRepository.save(pedido);
        }
    }
}