import model.Jogo;
import model.Cliente;
import model.Pedido;
import model.dao.JogoDAO;
import model.dao.ClienteDAO;
import model.dao.PedidoDAO;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        JogoDAO jogoDAO = new JogoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        Jogo j1 = new Jogo(1, "Zelda", "Aventura", 250.0);
        Jogo j2 = new Jogo(2, "God Of War 2018", "Ação-aventura", 300.0);
        jogoDAO.salvar(j1);
        jogoDAO.salvar(j2);

        Cliente c1 = new Cliente(1, "Link", "link@email.com");
        Cliente c2 = new Cliente(2, "Cleitão da Massa", "cleitao@email.com");
        clienteDAO.salvar(c1);
        clienteDAO.salvar(c2);

        Pedido p1 = new Pedido(1, 1, Arrays.asList(1,2), "Aberto");
        pedidoDAO.salvar(p1);

        System.out.println("=== Jogos ===");
        for (Jogo j : jogoDAO.listar()) System.out.println(j.getNome() + " - R$" + j.getPreco());

        System.out.println("=== Clientes ===");
        for (Cliente c : clienteDAO.listar()) System.out.println(c.getNome() + " - " + c.getEmail());

        System.out.println("=== Pedidos ===");
        for (Pedido p : pedidoDAO.listar()) System.out.println("Pedido " + p.getId() + " do cliente " + p.getClienteId() + ", status: " + p.getStatus());

        j1.setPreco(270.0);
        jogoDAO.atualizar(jogoDAO.listar());

        pedidoDAO.deletar(1);
        System.out.println("Pedido deletado, pedidos restantes: " + pedidoDAO.listar().size());
    }
}
