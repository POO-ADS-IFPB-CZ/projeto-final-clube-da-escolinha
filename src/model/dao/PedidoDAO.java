package model.dao;

import model.Pedido;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PedidoDAO {
    private static final String FILE_NAME = "pedidos.txt";

    public void salvar(Pedido pedido) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
            out.println(pedido.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                lista.add(Pedido.fromString(linha));
            }
        } catch (IOException e) {}
        return lista;
    }

    public void atualizar(List<Pedido> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Pedido p : lista) out.println(p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        List<Pedido> lista = listar();
        lista.removeIf(p -> p.getId() == id);
        atualizar(lista);
    }
}
