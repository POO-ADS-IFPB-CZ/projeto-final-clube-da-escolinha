package model;

public class Jogo {
    private int id;
    private String nome;
    private String genero;
    private double preco;

    public Jogo(int id, String nome, String genero, double preco) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.preco = preco;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + genero + ";" + preco;
    }

    public static Jogo fromString(String linha) {
        String[] dados = linha.split(";");
        return new Jogo(
                Integer.parseInt(dados[0]),
                dados[1],
                dados[2],
                Double.parseDouble(dados[3])
        );
    }
}
