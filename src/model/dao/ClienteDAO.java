package model.dao;

import model.Cliente;
import java.io.*;
import java.util.*;

public class ClienteDAO {
    private static final String FILE_NAME = "clientes.txt";

    public void salvar(Cliente cliente) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
            out.println(cliente.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                lista.add(Cliente.fromString(linha));
            }
        } catch (IOException e) {}
        return lista;
    }

    public void atualizar(List<Cliente> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Cliente c : lista) out.println(c.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        List<Cliente> lista = listar();
        lista.removeIf(c -> c.getId() == id);
        atualizar(lista);
    }
}
