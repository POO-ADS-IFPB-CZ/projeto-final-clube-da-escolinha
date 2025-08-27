package model.dao;

import model.Pedido;
import model.Cliente;
import model.Jogo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements GenericDAO<Pedido> {
    private static final String FILE_NAME = "pedidos.txt";
    private int proximoId = 1;

    private ClienteDAO clienteDAO;
    private JogoDAO jogoDAO;

    public PedidoDAO(ClienteDAO clienteDAO, JogoDAO jogoDAO) {
        this.clienteDAO = clienteDAO;
        this.jogoDAO = jogoDAO;
    }

    private List<Pedido> carregar() {
        List<Pedido> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    Pedido p = Pedido.fromString(linha);
                    lista.add(p);
                    if (p.getId() >= proximoId) {
                        proximoId = p.getId() + 1;
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao ler linha do arquivo: " + linha);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado, criando novo arquivo: " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Aqui grava a lista inteira de pedidos no arquivo
    private void gravar(List<Pedido> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Pedido p : lista) out.println(p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserir(Pedido pedido) {
        List<Pedido> lista = carregar();

        // Verifica duplicidade de ID
        if (lista.stream().anyMatch(p -> p.getId() == pedido.getId())) {
            System.out.println("Erro: Pedido com ID " + pedido.getId() + " já existe!");
            return;
        }

        // Verifica se cliente existe
        Cliente cliente = clienteDAO.buscarPorId(pedido.getClienteId());
        if (cliente == null) {
            System.out.println("Erro: Cliente com ID " + pedido.getClienteId() + " não existe!");
            return;
        }

        // Verifica se todos os jogos existem
        for (Integer jogoId : pedido.getJogosIds()) {
            Jogo jogo = jogoDAO.buscarPorId(jogoId);
            if (jogo == null) {
                System.out.println("Erro: Jogo com ID " + jogoId + " não existe!");
                return;
            }
        }

        // Define ID automaticamente
        pedido.setId(proximoId++);
        lista.add(pedido);
        gravar(lista);
    }

    @Override
    public List<Pedido> listarTodos() {
        return carregar();
    }

    @Override
    public Pedido buscarPorId(int id) {
        for (Pedido p : carregar()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public void atualizar(Pedido pedido) {
        List<Pedido> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == pedido.getId()) {
                lista.set(i, pedido);
                gravar(lista);
                return;
            }
        }
        System.out.println("Pedido com ID " + pedido.getId() + " não encontrado para atualizar.");
    }

    @Override
    public void remover(int id) {
        List<Pedido> lista = carregar();
        lista.removeIf(p -> p.getId() == id);
        gravar(lista);
    }

    public List<Pedido> listar() { return listarTodos(); }
    public void salvar(Pedido p) { inserir(p); }
    public void deletar(int id) { remover(id); }
}
