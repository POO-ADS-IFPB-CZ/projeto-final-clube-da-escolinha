package model.dao;

import java.util.List;

public interface GenericDAO<T> {
    void inserir(T obj);
    List<T> listarTodos();
    T buscarPorId(int id);
    void atualizar(T obj);
    void remover(int id);
}
