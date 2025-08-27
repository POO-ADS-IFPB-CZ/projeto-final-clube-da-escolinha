package model.dao;

import model.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements GenericDAO<Cliente> {
    private static final String FILE_NAME = "clientes.txt";

    // Carrega todos os clientes do arquivo
    private List<Cliente> carregar() {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    lista.add(Cliente.fromString(linha));
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

    // Gravação da Lista em um arquivo
    private void gravar(List<Cliente> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Cliente c : lista) out.println(c.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserir(Cliente cliente) {
        List<Cliente> lista = carregar();

        // Verificão de ID Duplicado
        if (lista.stream().anyMatch(c -> c.getId() == cliente.getId())) {
            System.out.println("Erro: Cliente com ID " + cliente.getId() + " já existe!");
            return; // ou lançar exceção
        }

        lista.add(cliente);
        gravar(lista);
    }

    @Override
    public List<Cliente> listarTodos() {
        return carregar();
    }

    @Override
    public Cliente buscarPorId(int id) {
        for (Cliente c : carregar()) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    @Override
    public void atualizar(Cliente cliente) {
        List<Cliente> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == cliente.getId()) {
                lista.set(i, cliente);
                gravar(lista);
                return;
            }
        }
        System.out.println("Cliente com ID " + cliente.getId() + " não encontrado para atualizar.");
    }

    @Override
    public void remover(int id) {
        List<Cliente> lista = carregar();
        lista.removeIf(c -> c.getId() == id);
        gravar(lista);
    }

    public List<Cliente> listar() { return listarTodos(); }
    public void salvar(Cliente c) { inserir(c); }
    public void deletar(int id) { remover(id); }
    // Buscar clientes por nome
    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> todos = carregar();
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : todos) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }


    // Verifica se já existe cliente com mesmo nome ou email
    public boolean existeCliente(String nome, String email) {
        List<Cliente> lista = listar();
        for (Cliente c : lista) {
            if (c.getNome().equalsIgnoreCase(nome) || c.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    
}