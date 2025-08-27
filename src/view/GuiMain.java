package view;

import controller.ClienteController;
import controller.JogoController;
import controller.PedidoController;

import javax.swing.*;

public class GuiMain {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            ClienteController cc = new ClienteController();
            JogoController jc = new JogoController();
            PedidoController pc = new PedidoController();
            MainFrame frame = new MainFrame(cc, jc, pc);
            frame.setVisible(true);
        });
    }
}
