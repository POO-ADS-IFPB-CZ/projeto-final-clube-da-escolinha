package view;

import controller.ClienteController;
import controller.JogoController;
import controller.PedidoController;
import model.Cliente;
import model.Jogo;
import model.Pedido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private final ClienteController clienteController;
    private final JogoController jogoController;
    private final PedidoController pedidoController;

    public MainFrame(ClienteController cc, JogoController jc, PedidoController pc) {
        super("Loja - Interface GUI");
        this.clienteController = cc;
        this.jogoController = jc;
        this.pedidoController = pc;
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Clientes", new ClientesPanel());
        tabs.add("Jogos", new JogosPanel());
        tabs.add("Pedidos", new PedidosPanel());
        setContentPane(tabs);
    }

    // ----- Clientes Panel -----
    private class ClientesPanel extends JPanel {
        private final ClientesTableModel model = new ClientesTableModel();
        private final JTextField fId = new JTextField();
        private final JTextField fNome = new JTextField();
        private final JTextField fEmail = new JTextField();

        ClientesPanel() {
            super(new BorderLayout(10,10));
            JPanel form = new JPanel(new GridLayout(3,2,8,8));
            form.setBorder(new EmptyBorder(12,12,0,12));
            form.add(new JLabel("ID*:"));
            form.add(fId);
            form.add(new JLabel("Nome*:"));
            form.add(fNome);
            form.add(new JLabel("Email*:"));
            form.add(fEmail);

            JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton bAdd = new JButton("Adicionar");
            JButton bUpd = new JButton("Atualizar");
            JButton bDel = new JButton("Remover");
            JButton bClear = new JButton("Limpar");
            btns.add(bAdd); btns.add(bUpd); btns.add(bDel); btns.add(bClear);

            bAdd.addActionListener(e -> addCliente());
            bUpd.addActionListener(e -> updateCliente());
            bDel.addActionListener(e -> deleteCliente());
            bClear.addActionListener(e -> clearForm());

            JTable table = new JTable(model);
            table.setAutoCreateRowSorter(true);
            table.getSelectionModel().addListSelectionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int m = table.convertRowIndexToModel(row);
                    Cliente c = model.getAt(m);
                    fId.setText(String.valueOf(c.getId()));
                    fNome.setText(c.getNome());
                    fEmail.setText(c.getEmail());
                }
            });

            add(form, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
            add(btns, BorderLayout.SOUTH);
        }

        private void addCliente() {
            try {
                String idt = fId.getText().trim();
                String nome = fNome.getText().trim();
                String email = fEmail.getText().trim();
                if (idt.isEmpty() || nome.isEmpty() || email.isEmpty()) throw new IllegalArgumentException("Campos obrigatórios vazios.");
                int id = Integer.parseInt(idt);
                Cliente c = new Cliente(id, nome, email);
                Cliente salvo = clienteController.cadastrarCliente(c);
                if (salvo != null) {
                    model.refresh();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID deve ser inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void updateCliente() {
            try {
                String idt = fId.getText().trim();
                String nome = fNome.getText().trim();
                String email = fEmail.getText().trim();
                if (idt.isEmpty() || nome.isEmpty() || email.isEmpty()) throw new IllegalArgumentException("Campos obrigatórios vazios.");
                int id = Integer.parseInt(idt);
                Cliente c = new Cliente(id, nome, email);
                clienteController.atualizarCliente(c);
                model.refresh();
                JOptionPane.showMessageDialog(this, "Cliente atualizado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID deve ser inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void deleteCliente() {
            try {
                String idt = fId.getText().trim();
                if (idt.isEmpty()) throw new IllegalArgumentException("Informe o ID para remoção.");
                int id = Integer.parseInt(idt);
                int r = JOptionPane.showConfirmDialog(this, "Remover cliente " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    clienteController.removerCliente(id);
                    model.refresh();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Cliente removido.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID deve ser inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void clearForm() { fId.setText(""); fNome.setText(""); fEmail.setText(""); }

        private class ClientesTableModel extends AbstractTableModel {
            private List<Cliente> cache;
            private final String[] cols = {"ID","Nome","Email"};
            ClientesTableModel() { refresh(); }
            void refresh() { cache = clienteController.listarClientes(); fireTableDataChanged(); }
            Cliente getAt(int r){ return cache.get(r); }
            public int getRowCount(){ return cache.size(); }
            public int getColumnCount(){ return cols.length; }
            public String getColumnName(int c){ return cols[c]; }
            public Object getValueAt(int r,int c){ Cliente x = cache.get(r); return c==0? x.getId() : c==1? x.getNome() : x.getEmail(); }
        }
    }

    // ----- Jogos Panel -----
    private class JogosPanel extends JPanel {
        private final JogosTableModel model = new JogosTableModel();
        private final JTextField fId = new JTextField();
        private final JTextField fNome = new JTextField();
        private final JTextField fGenero = new JTextField();
        private final JTextField fPreco = new JTextField();

        JogosPanel() {
            super(new BorderLayout(10,10));
            JPanel form = new JPanel(new GridLayout(4,2,8,8));
            form.setBorder(new EmptyBorder(12,12,0,12));
            form.add(new JLabel("ID*:")); form.add(fId);
            form.add(new JLabel("Nome*:")); form.add(fNome);
            form.add(new JLabel("Gênero*:")); form.add(fGenero);
            form.add(new JLabel("Preço* (ex: 59.90):")); form.add(fPreco);

            JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton bAdd = new JButton("Adicionar"); JButton bUpd = new JButton("Atualizar");
            JButton bDel = new JButton("Remover"); JButton bClear = new JButton("Limpar");
            btns.add(bAdd); btns.add(bUpd); btns.add(bDel); btns.add(bClear);

            bAdd.addActionListener(e -> addJogo());
            bUpd.addActionListener(e -> updateJogo());
            bDel.addActionListener(e -> deleteJogo());
            bClear.addActionListener(e -> clearForm());

            JTable table = new JTable(model);
            table.setAutoCreateRowSorter(true);
            table.getSelectionModel().addListSelectionListener(e -> {
                int row = table.getSelectedRow();
                if (row>=0) {
                    int m = table.convertRowIndexToModel(row);
                    Jogo j = model.getAt(m);
                    fId.setText(String.valueOf(j.getId()));
                    fNome.setText(j.getNome());
                    fGenero.setText(j.getGenero());
                    fPreco.setText(String.valueOf(j.getPreco()));
                }
            });

            add(form, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
            add(btns, BorderLayout.SOUTH);
        }

        private void addJogo() {
            try {
                String idt = fId.getText().trim();
                String nome = fNome.getText().trim();
                String genero = fGenero.getText().trim();
                String precoS = fPreco.getText().trim();
                if (idt.isEmpty() || nome.isEmpty() || genero.isEmpty() || precoS.isEmpty()) throw new IllegalArgumentException("Campos obrigatórios vazios.");
                int id = Integer.parseInt(idt);
                double preco = Double.parseDouble(precoS);
                if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo.");
                Jogo j = new Jogo(id, nome, genero, preco);
                Jogo salvo = jogoController.cadastrarJogo(j);
                if (salvo != null) {
                    model.refresh();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Jogo adicionado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID e Preço devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void updateJogo() {
            try {
                String idt = fId.getText().trim();
                String nome = fNome.getText().trim();
                String genero = fGenero.getText().trim();
                String precoS = fPreco.getText().trim();
                if (idt.isEmpty() || nome.isEmpty() || genero.isEmpty() || precoS.isEmpty()) throw new IllegalArgumentException("Campos obrigatórios vazios.");
                int id = Integer.parseInt(idt);
                double preco = Double.parseDouble(precoS);
                if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo.");
                Jogo j = new Jogo(id, nome, genero, preco);
                jogoController.atualizarJogo(j);
                model.refresh();
                JOptionPane.showMessageDialog(this, "Jogo atualizado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID e Preço devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void deleteJogo() {
            try {
                String idt = fId.getText().trim();
                if (idt.isEmpty()) throw new IllegalArgumentException("Informe o ID para remoção.");
                int id = Integer.parseInt(idt);
                int r = JOptionPane.showConfirmDialog(this, "Remover jogo " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    jogoController.removerJogo(id);
                    model.refresh();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Jogo removido.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID deve ser inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void clearForm() { fId.setText(""); fNome.setText(""); fGenero.setText(""); fPreco.setText(""); }

        private class JogosTableModel extends AbstractTableModel {
            private List<Jogo> cache;
            private final String[] cols = {"ID","Nome","Gênero","Preço"};
            JogosTableModel(){ refresh(); }
            void refresh(){ cache = jogoController.listarJogos(); fireTableDataChanged(); }
            Jogo getAt(int r){ return cache.get(r); }
            public int getRowCount(){ return cache.size(); }
            public int getColumnCount(){ return cols.length; }
            public String getColumnName(int c){ return cols[c]; }
            public Object getValueAt(int r,int c){ Jogo x = cache.get(r); return c==0? x.getId() : c==1? x.getNome() : c==2? x.getGenero() : x.getPreco(); }
        }
    }

    // ----- Pedidos Panel -----
    private class PedidosPanel extends JPanel {
        private final PedidosTableModel model = new PedidosTableModel();
        private final JTextField fId = new JTextField();
        private final JTextField fClienteId = new JTextField();
        private final JTextField fJogosIds = new JTextField();
        private final JComboBox<String> fStatus = new JComboBox<>(new String[]{"PENDENTE","PAGO","ENVIADO","CANCELADO"});

        PedidosPanel() {
            super(new BorderLayout(10,10));
            JPanel form = new JPanel(new GridLayout(4,2,8,8));
            form.setBorder(new EmptyBorder(12,12,0,12));
            form.add(new JLabel("ID*:")); form.add(fId);
            form.add(new JLabel("Cliente ID*:")); form.add(fClienteId);
            form.add(new JLabel("Jogos IDs* (ex: 1,2):")); form.add(fJogosIds);
            form.add(new JLabel("Status*:")); form.add(fStatus);

            JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton bAdd = new JButton("Adicionar"); JButton bUpd = new JButton("Atualizar");
            JButton bDel = new JButton("Remover"); JButton bClear = new JButton("Limpar");
            btns.add(bAdd); btns.add(bUpd); btns.add(bDel); btns.add(bClear);

            bAdd.addActionListener(e -> addPedido());
            bUpd.addActionListener(e -> updatePedido());
            bDel.addActionListener(e -> deletePedido());
            bClear.addActionListener(e -> clearForm());

            JTable table = new JTable(model);
            table.setAutoCreateRowSorter(true);
            table.getSelectionModel().addListSelectionListener(e -> {
                int row = table.getSelectedRow();
                if (row>=0) {
                    int m = table.convertRowIndexToModel(row);
                    Pedido p = model.getAt(m);
                    fId.setText(String.valueOf(p.getId()));
                    fClienteId.setText(String.valueOf(p.getClienteId()));
                    fJogosIds.setText(p.getJogosIds().stream().map(Object::toString).collect(Collectors.joining(",")));
                    fStatus.setSelectedItem(p.getStatus());
                }
            });

            add(form, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
            add(btns, BorderLayout.SOUTH);
        }

        private void addPedido() {
            try {
                String idt = fId.getText().trim();
                String clienteS = fClienteId.getText().trim();
                String jogosS = fJogosIds.getText().trim();
                String status = (String) fStatus.getSelectedItem();
                if (idt.isEmpty() || clienteS.isEmpty() || jogosS.isEmpty() || status==null) throw new IllegalArgumentException("Campos obrigatórios vazios.");
                int id = Integer.parseInt(idt);
                int clienteId = Integer.parseInt(clienteS);
                List<Integer> jogos = List.of(jogosS.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
                // valida status
                if (!List.of("PENDENTE","PAGO","ENVIADO","CANCELADO").contains(status)) throw new IllegalArgumentException("Status inválido.");
                Pedido p = pedidoController.criarPedido(clienteId, jogos);
                // Note: controller cria ID internally; attempt to save with provided id if controller supports
                // If controller ignores provided id, still refresh
                model.refresh();
                clearForm();
                JOptionPane.showMessageDialog(this, "Pedido criado/atualizado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs devem ser inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void updatePedido() {
            try {
                String idt = fId.getText().trim();
                String clienteS = fClienteId.getText().trim();
                String jogosS = fJogosIds.getText().trim();
                String status = (String) fStatus.getSelectedItem();
                if (idt.isEmpty() || clienteS.isEmpty() || jogosS.isEmpty() || status==null) throw new IllegalArgumentException("Campos obrigatórios vazios.");
                int id = Integer.parseInt(idt);
                int clienteId = Integer.parseInt(clienteS);
                List<Integer> jogos = List.of(jogosS.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
                Pedido p = new Pedido(id, clienteId, jogos, status);
                pedidoController.atualizarPedido(p);
                model.refresh();
                JOptionPane.showMessageDialog(this, "Pedido atualizado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "IDs devem ser inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void deletePedido() {
            try {
                String idt = fId.getText().trim();
                if (idt.isEmpty()) throw new IllegalArgumentException("Informe o ID para remoção.");
                int id = Integer.parseInt(idt);
                int r = JOptionPane.showConfirmDialog(this, "Remover pedido " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    pedidoController.cancelarPedido(id);
                    model.refresh();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Pedido removido.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID deve ser inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void clearForm() { fId.setText(""); fClienteId.setText(""); fJogosIds.setText(""); fStatus.setSelectedIndex(0); }

        private class PedidosTableModel extends AbstractTableModel {
            private java.util.List<Pedido> cache;
            private final String[] cols = {"ID","ClienteId","Jogos","Status"};
            PedidosTableModel(){ refresh(); }
            void refresh(){ cache = pedidoController.listarPedidos(); fireTableDataChanged(); }
            Pedido getAt(int r){ return cache.get(r); }
            public int getRowCount(){ return cache.size(); }
            public int getColumnCount(){ return cols.length; }
            public String getColumnName(int c){ return cols[c]; }
            public Object getValueAt(int r,int c){ Pedido x = cache.get(r); return c==0? x.getId() : c==1? x.getClienteId() : c==2? x.getJogosIds().toString() : x.getStatus(); }
        }
    }
}
