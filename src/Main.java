import model.Cliente;
import model.Jogo;
import model.dao.ClienteDAO;
import model.dao.JogoDAO;

public class Main {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();

        clienteDAO.inserir(new Cliente(1, "Luan", "luan@email.com"));
        clienteDAO.inserir(new Cliente(2, "Maria", "maria@email.com"));

        System.out.println("=== Clientes cadastrados ===");
        for (Cliente c : clienteDAO.listarTodos()) {
            System.out.println(c);
        }

        System.out.println("\n=== Buscar Cliente ID 1 ===");
        System.out.println(clienteDAO.buscarPorId(1));

        JogoDAO jogoDAO = new JogoDAO();

        jogoDAO.inserir(new Jogo(1, "Hollow Knight", "Metroidvania", 39.90));
        jogoDAO.inserir(new Jogo(2, "The Witcher 3", "RPG", 99.90));

        System.out.println("\n=== Jogos cadastrados ===");
        for (Jogo j : jogoDAO.listarTodos()) {
            System.out.println(j);
        }

        System.out.println("\n=== Buscar Jogo ID 2 ===");
        System.out.println(jogoDAO.buscarPorId(2));
    }
}
