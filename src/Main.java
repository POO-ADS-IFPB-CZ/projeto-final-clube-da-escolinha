import controller.ClienteController;
import controller.JogoController;
import controller.PedidoController;
import model.Cliente;
import model.Jogo;
import model.Pedido;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController();
        JogoController jogoController = new JogoController();
        PedidoController pedidoController = new PedidoController();

        try {
            // ===== Clientes =====
            clienteController.cadastrarCliente(new Cliente(0, "Irineu", "irineu@email.com"));
            clienteController.cadastrarCliente(new Cliente(0, "Leo", "leo@email.com"));

            System.out.println("=== Clientes cadastrados ===");
            clienteController.listarClientes().forEach(System.out::println);

            // Buscar cliente
            System.out.println("\n=== Buscar Cliente ID 1 ===");
            Optional<Cliente> cliente = Optional.ofNullable(clienteController.buscarCliente(1));
            if (cliente.isPresent()) {
                System.out.println(cliente);
            } else {
                System.out.println("Cliente não encontrado.");
            }

            // ===== Jogos =====
            jogoController.cadastrarJogo(new Jogo(0, "Hollow Knight", "Metroidvania", 39.90));
            jogoController.cadastrarJogo(new Jogo(0, "The Witcher 3", "RPG", 99.90));

            System.out.println("\n=== Jogos cadastrados ===");
            jogoController.listarJogos().forEach(System.out::println);

            // Buscar jogo
            System.out.println("\n=== Buscar Jogo ID 2 ===");
            jogoController.buscarJogo(2);
            Jogo jogo = jogoController.buscarJogo(1);
            if (jogo != null) {
                System.out.println(jogo);
            } else {
                System.out.println("Jogo não encontrado.");
            };

            // ===== Pedidos =====
            Pedido pedido1 = pedidoController.criarPedido(1, List.of(2)); // Cliente 1 compra Jogo 2
            Pedido pedido2 = pedidoController.criarPedido(2, List.of(1, 2)); // Cliente 2 compra Jogo 1 e 2

            System.out.println("\n=== Pedidos cadastrados ===");
            pedidoController.listarPedidos().forEach(System.out::println);

            // Buscar pedido
            System.out.println("\n=== Buscar Pedido ID 1 ===");
            Pedido pedido = pedidoController.buscarPedido(1);
            if (pedido != null) {
                System.out.println(pedido);
            } else {
                System.out.println("Pedido não encontrado.");
            }

            // Atualizar pedido
            System.out.println("\n=== Atualizando status do Pedido ID 1 ===");
            Pedido p1 = pedidoController.buscarPedido(1);
            if (p1 != null) {
                p1.setStatus("PAGO");
                pedidoController.atualizarPedido(p1);
                System.out.println(p1);
            } else {
                System.out.println("Pedido não encontrado.");
            }

            // Remover pedido
            System.out.println("\n=== Cancelando Pedido ID 2 ===");
            pedidoController.cancelarPedido(2);

            System.out.println("\n=== Pedidos após cancelamento ===");
            pedidoController.listarPedidos().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
