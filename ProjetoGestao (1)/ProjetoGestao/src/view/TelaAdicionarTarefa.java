package view;

import controller.ProjetoController;
import controller.UsuarioController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaAdicionarTarefa extends JDialog {

    private final Projeto projeto;
    private final ProjetoController pc;
    private final UsuarioController uc;

    public TelaAdicionarTarefa(JFrame parent, Projeto projeto, ProjetoController pc, UsuarioController uc) {
        super(parent, "Adicionar Tarefa", true);
        this.projeto = projeto;
        this.pc = pc;
        this.uc = uc;
        setupUI();
    }

    private void setupUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new GridLayout(6, 2, 5, 5));

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();
        JLabel lblDescricao = new JLabel("Descrição:");
        JTextField txtDescricao = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"PENDENTE", "EM ANDAMENTO", "CONCLUÍDA"});

        JLabel lblResp = new JLabel("Responsável:");
        JComboBox<Usuario> cbResponsavel = new JComboBox<>();
        List<Usuario> usuarios = uc.listarUsuarios();
        for (Usuario u : usuarios) {
            cbResponsavel.addItem(u);
        }

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnCancelar = new JButton("Cancelar");

        add(lblTitulo);
        add(txtTitulo);
        add(lblDescricao);
        add(txtDescricao);
        add(lblStatus);
        add(cbStatus);
        add(lblResp);
        add(cbResponsavel);
        add(btnAdicionar);
        add(btnCancelar);

        btnAdicionar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String desc = txtDescricao.getText().trim();
            StatusTarefa status = StatusTarefa.PENDENTE;
            switch (cbStatus.getSelectedIndex()) {
                case 0 -> status = StatusTarefa.PENDENTE;
                case 1 -> status = StatusTarefa.EM_ANDAMENTO;
                case 2 -> status = StatusTarefa.CONCLUIDA;
            }
            Usuario resp = (Usuario) cbResponsavel.getSelectedItem();
            Tarefa t = new Tarefa(titulo, desc, status, resp);
            projeto.adicionarTarefa(t);
            JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!");
            dispose(); // fecha o dialog
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
