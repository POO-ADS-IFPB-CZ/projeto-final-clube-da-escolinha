package model.dao;

import model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements GenericDAO<Pedido> {

    private List<Pedido> pedidos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void inserir(Pedido pedido) {
        pedido.setId(proximoId++);
        pedidos.add(pedido);
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public Pedido buscarPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public void atualizar(Pedido pedido) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getId() == pedido.getId()) {
                pedidos.set(i, pedido);
                return;
            }
        }
    }

    @Override
    public void remover(int id) {
        pedidos.removeIf(pedido -> pedido.getId() == id);
    }
}
