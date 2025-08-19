package model.dao;

import model.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements GenericDAO<Cliente> {
    private static final String FILE_NAME = "clientes.txt";

    private List<Cliente> carregar() {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    lista.add(Cliente.fromString(linha));
                } catch (Exception e) {

                }
            }
        } catch (IOException e) {

        }
        return lista;
    }

    private void gravar(List<Cliente> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Cliente c : lista) out.println(c.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserir(Cliente cliente) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
            out.println(cliente.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
