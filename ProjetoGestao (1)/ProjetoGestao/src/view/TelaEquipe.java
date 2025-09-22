package view;

import controller.EquipeController;
import controller.UsuarioController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaEquipe {

    private final EquipeController ec;
    private final UsuarioController uc;

    public TelaEquipe(EquipeController ec, UsuarioController uc) {
        this.ec = ec;
        this.uc = uc;
    }

    public void abrir() {
        JFrame frame = new JFrame("Equipes");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        String[] colunas = {"ID", "Nome", "Descrição", "Membros"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        atualizarTabela(modelo);
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Equipe");
        JButton btnAdicionarMembros = new JButton("Adicionar Membros");
        botoes.add(btnAdicionar);
        botoes.add(btnAdicionarMembros);
        panel.add(botoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(frame, "Nome da equipe:");
                if(nome == null || nome.isEmpty()) return;
                String desc = JOptionPane.showInputDialog(frame, "Descrição:");
                Equipe eq = new Equipe(nome, desc);
                ec.adicionarEquipe(eq);
                atualizarTabela(modelo);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
            }
        });

        btnAdicionarMembros.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if(linha == -1) { JOptionPane.showMessageDialog(frame, "Selecione uma equipe!"); return; }

            int idEq = (int) tabela.getValueAt(linha, 0);
            Equipe eq = ec.buscarPorId(idEq);

            List<Usuario> usuarios = uc.listarUsuarios();
            if(usuarios.isEmpty()) { JOptionPane.showMessageDialog(frame, "Nenhum usuário cadastrado!"); return; }

            String[] opUsuarios = usuarios.stream().map(u -> u.getId() + " - " + u.getNome()).toArray(String[]::new);
            String escolha = (String) JOptionPane.showInputDialog(frame, "Escolha usuários para adicionar (separados por vírgula):",
                    "Adicionar membros", JOptionPane.QUESTION_MESSAGE, null, opUsuarios, opUsuarios[0]);
            if(escolha != null && !escolha.isEmpty()) {
                try {
                    int id = Integer.parseInt(escolha.split(" - ")[0]);
                    Usuario u = uc.buscarPorId(id);
                    eq.adicionarMembro(u);
                    atualizarTabela(modelo);
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao adicionar membro: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }

    private void atualizarTabela(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Equipe> equipes = ec.listarEquipes();
        for(Equipe e : equipes) {
            String membros = String.join(", ", e.getMembros().stream().map(Usuario::getNome).toList());
            modelo.addRow(new Object[]{e.getId(), e.getNome(), e.getDescricao(), membros});
        }
    }
}
