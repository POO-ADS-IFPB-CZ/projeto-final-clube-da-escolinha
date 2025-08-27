package controller;

import model.Jogo;
import model.dao.JogoDAO;

import java.util.List;
import javax.swing.JOptionPane;

public class JogoController {
    private JogoDAO jogoDAO;

    public JogoController() {
        this.jogoDAO = new JogoDAO();
    }

    // Cadastrar jogo
    public Jogo cadastrarJogo(Jogo jogo) {
        if (jogoDAO.existeJogo(jogo.getNome())) {
            JOptionPane.showMessageDialog(null, "Jogo já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;

        }
        if (jogo.getNome() == null || jogo.getNome().isEmpty()) {
            System.out.println("Erro: nome do jogo nao pode ser vazio.");
            return null;
        }
        if (jogo.getGenero() == null || jogo.getGenero().isEmpty()) {
            System.out.println("Erro: genero do jogo nao pode ser vazio.");
            return null;
        }
        if (jogo.getPreco() < 0) {
            System.out.println("Erro: preco do jogo nao pode ser negativo.");
            return null;
        }

        jogoDAO.inserir(jogo);
        System.out.println("Jogo cadastrado com sucesso!");
        return jogo;
    }

    // Listar todos os jogos
    public List<Jogo> listarJogos() {
        return jogoDAO.listarTodos();
    }

    // Buscar jogo por ID
    public Jogo buscarJogo(int id) {
        Jogo jogo = jogoDAO.buscarPorId(id);
        if (jogo == null) {
            System.out.println("Jogo com ID " + id + " nao encontrado.");
        }
        return jogo;
    }

    // Atualizar jogo
    public void atualizarJogo(Jogo jogo) {
        Jogo existente = jogoDAO.buscarPorId(jogo.getId());
        if (existente == null) {
            System.out.println("Nao foi possivel atualizar, jogo nao encontrado.");
            return;
        }

        if (jogo.getNome() == null || jogo.getNome().isEmpty() ||
                jogo.getGenero() == null || jogo.getGenero().isEmpty() ||
                jogo.getPreco() < 0) {
            System.out.println("Erro: dados invalidos para atualizar o jogo.");
            return;
        }

        jogoDAO.atualizar(jogo);
        System.out.println("Jogo atualizado com sucesso!");
    }

    // Remover jogo
    public void removerJogo(int id) {
        Jogo jogo = jogoDAO.buscarPorId(id);
        if (jogo == null) {
            System.out.println("Jogo com ID " + id + " nao existe.");
            return;
        }

        jogoDAO.remover(id);
        System.out.println("Jogo removido com sucesso!");
    }
}
