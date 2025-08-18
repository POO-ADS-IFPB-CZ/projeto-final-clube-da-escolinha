package model;

import java.util.List;
import java.util.stream.Collectors;

public class Pedido {
    private int id;
    private int clienteId;
    private List<Integer> jogosIds;
    private String status;

    public Pedido(int id, int clienteId, List<Integer> jogosIds, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.jogosIds = jogosIds;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public List<Integer> getJogosIds() { return jogosIds; }
    public void setJogosIds(List<Integer> jogosIds) { this.jogosIds = jogosIds; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        String jogos = jogosIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        return id + ";" + clienteId + ";" + jogos + ";" + status;
    }

    public static Pedido fromString(String linha) {
        String[] dados = linha.split(";");
        List<Integer> jogos = List.of(dados[2].split(",")).stream()
                .map(Integer::parseInt)
                .toList();
        return new Pedido(
                Integer.parseInt(dados[0]),
                Integer.parseInt(dados[1]),
                jogos,
                dados[3]
        );
    }
}
