package controller;

import model.Pedido;
import model.Cliente;
import model.Jogo;
import model.dao.PedidoDAO;
import model.dao.ClienteDAO;
import model.dao.JogoDAO;

import java.util.List;

public class PedidoController {
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private JogoDAO jogoDAO;

    public PedidoController() {
        this.clienteDAO = new ClienteDAO();
        this.jogoDAO = new JogoDAO();
        this.pedidoDAO = new PedidoDAO();
    }

    // Novo método: criar pedido a partir de clienteId e ids de jogos
    public Pedido criarPedido(int clienteId, List<Integer> jogosIds) {
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Erro: cliente com ID " + clienteId + " nao existe.");
            return null;
        }

        for (Integer jogoId : jogosIds) {
            Jogo jogo = jogoDAO.buscarPorId(jogoId);
            if (jogo == null) {
                System.out.println("Erro: jogo com ID " + jogoId + " nao existe.");
                return null;
            }
        }

        Pedido pedido = new Pedido(0, clienteId, jogosIds, "PENDENTE");
        pedidoDAO.inserir(pedido);
        System.out.println("Pedido criado com sucesso!");
        return pedido;
    }

    // Métodos antigos (para compatibilidade)
    public void criarPedido(Pedido pedido) {
        pedidoDAO.inserir(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.listarTodos();
    }

    public Pedido buscarPedido(int id) {
        Pedido pedido = pedidoDAO.buscarPorId(id);
        if (pedido == null) {
            System.out.println("Pedido com ID " + id + " nao encontrado.");
        }
        return pedido;
    }

    public void atualizarPedido(Pedido pedido) {
        pedidoDAO.atualizar(pedido);
    }

    public void cancelarPedido(int id) {
        pedidoDAO.remover(id);
    }
}
