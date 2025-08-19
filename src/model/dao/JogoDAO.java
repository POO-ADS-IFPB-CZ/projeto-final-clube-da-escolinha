package model.dao;

import model.Jogo;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO implements GenericDAO<Jogo> {

    private List<Jogo> jogos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void inserir(Jogo jogo) {
        jogo.setId(proximoId++);
        jogos.add(jogo);
    }

    @Override
    public List<Jogo> listarTodos() {
        return new ArrayList<>(jogos);
    }

    @Override
    public Jogo buscarPorId(int id) {
        for (Jogo jogo : jogos) {
            if (jogo.getId() == id) {
                return jogo;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Jogo jogo) {
        for (int i = 0; i < jogos.size(); i++) {
            if (jogos.get(i).getId() == jogo.getId()) {
                jogos.set(i, jogo);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        jogos.removeIf(jogo -> jogo.getId() == id);
    }
}
