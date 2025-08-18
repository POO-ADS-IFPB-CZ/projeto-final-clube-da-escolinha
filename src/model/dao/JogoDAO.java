package model.dao;

import model.Jogo;
import java.io.*;
import java.util.*;

public class JogoDAO {
    private static final String FILE_NAME = "jogos.txt";
    
    public void salvar(Jogo jogo) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, true)))) {
            out.println(jogo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Jogo> listar() {
        List<Jogo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                lista.add(Jogo.fromString(linha));
            }
        } catch (IOException e) {}
        return lista;
    }

    public void atualizar(List<Jogo> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Jogo j : lista) out.println(j.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        List<Jogo> lista = listar();
        lista.removeIf(j -> j.getId() == id);
        atualizar(lista);
    }
}
