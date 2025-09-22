package view;

import controller.UsuarioController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaUsuario {

    private final UsuarioController uc;

    public TelaUsuario(UsuarioController uc) {
        this.uc = uc;
    }

    public void abrir() {
        JFrame frame = new JFrame("Usuários");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        String[] colunas = {"ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        atualizarTabela(modelo);
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Usuário");
        botoes.add(btnAdicionar);
        panel.add(botoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(frame, "Nome completo:");
                if(nome == null || nome.isEmpty()) return;
                String cpf = JOptionPane.showInputDialog(frame, "CPF:");
                String email = JOptionPane.showInputDialog(frame, "E-mail:");
                String cargo = JOptionPane.showInputDialog(frame, "Cargo:");
                String login = JOptionPane.showInputDialog(frame, "Login:");
                String senha = JOptionPane.showInputDialog(frame, "Senha:");

                String[] perfis = {"ADMIN", "GERENTE", "COLABORADOR"};
                String escolha = (String) JOptionPane.showInputDialog(frame, "Escolha o perfil:",
                        "Perfil", JOptionPane.QUESTION_MESSAGE, null, perfis, perfis[2]);

                Usuario u;
                switch(escolha) {
                    case "ADMIN" -> u = new Administrador(nome, cpf, email, cargo, login, senha);
                    case "GERENTE" -> u = new Gerente(nome, cpf, email, cargo, login, senha);
                    default -> u = new Colaborador(nome, cpf, email, cargo, login, senha);
                }

                uc.adicionarUsuario(u);
                atualizarTabela(modelo);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }

    private void atualizarTabela(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Usuario> usuarios = uc.listarUsuarios();
        for(Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getId(), u.getNome(), u.getCpf(), u.getEmail(),
                    u.getCargo(), u.getLogin(), u.getPerfil()
            });
        }
    }
}
