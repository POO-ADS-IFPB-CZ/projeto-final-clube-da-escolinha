import model.Cliente;
import model.Jogo;
import model.Pedido;
import model.dao.ClienteDAO;
import model.dao.JogoDAO;
import model.dao.PedidoDAO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.inserir(new Cliente(1, "Luan", "luan@email.com"));
        clienteDAO.inserir(new Cliente(2, "Maria", "maria@email.com"));

        System.out.println("=== Clientes cadastrados ===");
        for (Cliente c : clienteDAO.listarTodos()) {
            System.out.println(c);
        }

        System.out.println("\n=== Buscar Cliente ID 1 ===");
        Cliente cliente1 = clienteDAO.buscarPorId(1);
        System.out.println(cliente1);

        JogoDAO jogoDAO = new JogoDAO();

        jogoDAO.inserir(new Jogo(1, "Hollow Knight", "Metroidvania", 39.90));
        jogoDAO.inserir(new Jogo(2, "The Witcher 3", "RPG", 99.90));

        System.out.println("\n=== Jogos cadastrados ===");
        for (Jogo j : jogoDAO.listarTodos()) {
            System.out.println(j);
        }

        System.out.println("\n=== Buscar Jogo ID 2 ===");
        Jogo jogo2 = jogoDAO.buscarPorId(2);
        System.out.println(jogo2);

        PedidoDAO pedidoDAO = new PedidoDAO();

        Pedido pedido1 = new Pedido(1, cliente1.getId(), List.of(jogo2.getId()), "PENDENTE");
        Pedido pedido2 = new Pedido(2, 2, List.of(1, 2), "PENDENTE");

        pedidoDAO.inserir(pedido1);
        pedidoDAO.inserir(pedido2);

        System.out.println("\n=== Pedidos cadastrados ===");
        for (Pedido p : pedidoDAO.listarTodos()) {
            System.out.println(p);
        }

        System.out.println("\n=== Buscar Pedido ID 1 ===");
        Pedido p1 = pedidoDAO.buscarPorId(1);
        System.out.println(p1);

        p1.setStatus("PAGO");
        pedidoDAO.atualizar(p1);
        System.out.println("\n=== Pedido ID 1 atualizado ===");
        System.out.println(pedidoDAO.buscarPorId(1));

        pedidoDAO.remover(2);
        System.out.println("\n=== Pedidos após remoção do ID 2 ===");
        for (Pedido p : pedidoDAO.listarTodos()) {
            System.out.println(p);
        }
    }
}
