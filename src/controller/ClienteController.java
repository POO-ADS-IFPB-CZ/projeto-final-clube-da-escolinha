package controller;

import model.Cliente;
import model.dao.ClienteDAO;
import javax.swing.JOptionPane;

import java.util.List;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // Cadastrar cliente
    public Cliente cadastrarCliente(Cliente cliente) {
        if (clienteDAO.existeCliente(cliente.getNome(), cliente.getEmail())) {
            JOptionPane.showMessageDialog(null, "Cliente já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;

        }
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            System.out.println("Erro: nome do cliente nao pode ser vazio.");
            return null;
        }
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            System.out.println("Erro: email do cliente nao pode ser vazio.");
            return null;
        }

        clienteDAO.inserir(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
        return cliente;
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        return clienteDAO.listarTodos();
    }

    // Buscar cliente por ID
    public Cliente buscarCliente(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente com ID " + id + " nao encontrado.");
        }
        return cliente;
    }

    // Atualizar cliente
    public void atualizarCliente(Cliente cliente) {
        Cliente existente = clienteDAO.buscarPorId(cliente.getId());
        if (existente == null) {
            System.out.println("Nao foi possivel atualizar, cliente nao encontrado.");
            return;
        }

        if (cliente.getNome() == null || cliente.getNome().isEmpty() ||
                cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            System.out.println("Erro: nome ou email nao podem ser vazios.");
            return;
        }

        clienteDAO.atualizar(cliente);
        System.out.println("Cliente atualizado com sucesso!");
    }

    // Remover cliente
    public void removerCliente(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente com ID " + id + " nao existe.");
            return;
        }

        clienteDAO.remover(id);
        System.out.println("Cliente removido com sucesso!");
    }
}
