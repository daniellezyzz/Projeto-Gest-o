package view;

import controller.UsuarioController;
import controller.ProjetoController;
import controller.EquipeController;

import javax.swing.*;

public class TelaPrincipal {

    public static void main(String[] args) {
        // Cria os controllers compartilhados
        UsuarioController uc = new UsuarioController();
        EquipeController ec = new EquipeController();
        ProjetoController pc = new ProjetoController();

        // Cria a tela principal
        JFrame frame = new JFrame("Sistema de Gestão de Projetos");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("Bem-vindo!");
        label.setBounds(150, 20, 100, 25);
        panel.add(label);

        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        btnUsuarios.setBounds(100, 70, 200, 30);
        panel.add(btnUsuarios);

        JButton btnProjetos = new JButton("Gerenciar Projetos");
        btnProjetos.setBounds(100, 110, 200, 30);
        panel.add(btnProjetos);

        JButton btnEquipes = new JButton("Gerenciar Equipes");
        btnEquipes.setBounds(100, 150, 200, 30);
        panel.add(btnEquipes);

        // **Agora criamos instâncias das telas passando os controllers**
        TelaUsuario telaUsuarios = new TelaUsuario(uc);
        TelaEquipe telaEquipes = new TelaEquipe(ec, uc);
        TelaProjeto telaProjetos = new TelaProjeto(pc, uc, ec);

        // Configura os botões
        btnUsuarios.addActionListener(e -> telaUsuarios.abrir());
        btnEquipes.addActionListener(e -> telaEquipes.abrir());
        btnProjetos.addActionListener(e -> telaProjetos.abrir());

        frame.setVisible(true);
    }
}
