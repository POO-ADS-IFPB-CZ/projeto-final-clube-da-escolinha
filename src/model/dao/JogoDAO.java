package model.dao;

import model.Jogo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO implements GenericDAO<Jogo> {
    private static final String FILE_NAME = "jogos.txt";
    private int proximoId = 1;

    // Carrega todos os jogos contidos no arquivo
    private List<Jogo> carregar() {
        List<Jogo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                try {
                    Jogo jogo = Jogo.fromString(linha);
                    lista.add(jogo);

                    if (jogo.getId() >= proximoId) {
                        proximoId = jogo.getId() + 1;
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

    // Gravação da lista inteira de jogos no arquivo
    private void gravar(List<Jogo> lista) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Jogo j : lista) out.println(j.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inserir(Jogo jogo) {
        List<Jogo> lista = carregar();

        // Verifica duplicidade de nome
        if (lista.stream().anyMatch(j -> j.getNome().equalsIgnoreCase(jogo.getNome()))) {
            System.out.println("Erro: Jogo com nome '" + jogo.getNome() + "' já existe!");
            return;
        }

        // Aqui define ID automaticamente
        jogo.setId(proximoId++);
        lista.add(jogo);
        gravar(lista);
    }

    @Override
    public List<Jogo> listarTodos() {
        return carregar();
    }

    @Override
    public Jogo buscarPorId(int id) {
        for (Jogo j : carregar()) {
            if (j.getId() == id) return j;
        }
        return null;
    }

    @Override
    public void atualizar(Jogo jogo) {
        List<Jogo> lista = carregar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == jogo.getId()) {
                lista.set(i, jogo);
                gravar(lista);
                return;
            }
        }
        System.out.println("Jogo com ID " + jogo.getId() + " não encontrado para atualizar.");
    }

    @Override
    public void remover(int id) {
        List<Jogo> lista = carregar();
        lista.removeIf(j -> j.getId() == id);
        gravar(lista);
    }

    public List<Jogo> listar() { return listarTodos(); }
    public void salvar(Jogo j) { inserir(j); }
    public void deletar(int id) { remover(id); }
}
