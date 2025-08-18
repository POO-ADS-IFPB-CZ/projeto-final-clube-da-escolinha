package model;

public class Cliente {
    private int id;
    private String nome;
    private String email;

    public Cliente(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + email;
    }

    public static Cliente fromString(String linha) {
        String[] dados = linha.split(";");
        return new Cliente(
                Integer.parseInt(dados[0]),
                dados[1],
                dados[2]
        );
    }
}
