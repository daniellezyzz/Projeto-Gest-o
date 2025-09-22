package view;

import controller.ProjetoController;
import controller.UsuarioController;
import controller.EquipeController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaProjeto {

    private final ProjetoController pc;
    private final UsuarioController uc;
    private final EquipeController ec;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaProjeto(ProjetoController pc, UsuarioController uc, EquipeController ec) {
        this.pc = pc;
        this.uc = uc;
        this.ec = ec;
    }

    public void abrir() {
        JFrame frame = new JFrame("Projetos");
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        String[] colunas = {"ID", "Nome", "Descrição", "Início", "Término", "Status", "Gerente", "Equipes", "Tarefas"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        atualizarTabela(modelo);
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Projeto");
        JButton btnAlocarEquipe = new JButton("Alocar Equipe");
        JButton btnAdicionarTarefa = new JButton("Adicionar Tarefa");
        JButton btnVisualizar = new JButton("Visualizar Detalhes");
        botoes.add(btnAdicionar);
        botoes.add(btnAlocarEquipe);
        botoes.add(btnAdicionarTarefa);
        botoes.add(btnVisualizar);
        panel.add(botoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(frame, "Nome do projeto:");
                if(nome == null || nome.isEmpty()) return;
                String desc = JOptionPane.showInputDialog(frame, "Descrição:");
                String inicioStr = JOptionPane.showInputDialog(frame, "Data início (dd/MM/yyyy):");
                String terminoStr = JOptionPane.showInputDialog(frame, "Data término (dd/MM/yyyy):");
                LocalDate inicio = LocalDate.parse(inicioStr, fmt);
                LocalDate termino = LocalDate.parse(terminoStr, fmt);

                String[] statusOp = {"PLANEJADO", "EM_ANDAMENTO", "CONCLUIDO", "CANCELADO"};
                String statusStr = (String) JOptionPane.showInputDialog(frame, "Status do projeto:",
                        "Escolha status", JOptionPane.QUESTION_MESSAGE, null, statusOp, statusOp[0]);
                StatusProjeto status = StatusProjeto.valueOf(statusStr);

                // Selecionar gerente
                List<Usuario> gerentes = uc.listarPorPerfil(Perfil.GERENTE);
                if(gerentes.isEmpty()) { JOptionPane.showMessageDialog(frame, "Nenhum gerente cadastrado!"); return; }
                String[] opGerentes = gerentes.stream().map(u -> u.getId() + " - " + u.getNome()).toArray(String[]::new);
                String escolha = (String) JOptionPane.showInputDialog(frame, "Escolha o gerente:",
                        "Gerente", JOptionPane.QUESTION_MESSAGE, null, opGerentes, opGerentes[0]);
                int idGerente = Integer.parseInt(escolha.split(" - ")[0]);
                Gerente gerente = (Gerente) uc.buscarPorId(idGerente);

                Projeto p = new Projeto(nome, desc, inicio, termino, status, gerente);
                pc.adicionarProjeto(p);
                atualizarTabela(modelo);

            } catch(Exception ex) { JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage()); }
        });

        btnAlocarEquipe.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if(linha == -1) { JOptionPane.showMessageDialog(frame, "Selecione um projeto!"); return; }
            int idProj = (int) tabela.getValueAt(linha, 0);
            Projeto p = pc.buscarPorId(idProj);

            List<Equipe> equipes = ec.listarEquipes();
            if(equipes.isEmpty()) { JOptionPane.showMessageDialog(frame, "Nenhuma equipe cadastrada!"); return; }

            String[] opEquipes = equipes.stream().map(eq -> eq.getId() + " - " + eq.getNome()).toArray(String[]::new);
            String escolha = (String) JOptionPane.showInputDialog(frame, "Escolha a equipe:",
                    "Alocar equipe", JOptionPane.QUESTION_MESSAGE, null, opEquipes, opEquipes[0]);
            int idEq = Integer.parseInt(escolha.split(" - ")[0]);
            Equipe eAlocar = ec.buscarPorId(idEq);
            p.alocarEquipe(eAlocar);
            atualizarTabela(modelo);
        });

        btnAdicionarTarefa.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if(linha == -1) { JOptionPane.showMessageDialog(frame, "Selecione um projeto primeiro!"); return; }
            int idProj = (int) tabela.getValueAt(linha, 0);
            Projeto p = pc.buscarPorId(idProj);
            TelaAdicionarTarefa dialog = new TelaAdicionarTarefa(frame, p, pc, uc);
            dialog.setVisible(true);
            atualizarTabela(modelo);
        });

        btnVisualizar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) { JOptionPane.showMessageDialog(frame, "Selecione um projeto para visualizar!"); return; }
            int idProj = (int) tabela.getValueAt(linha, 0);
            Projeto p = pc.buscarPorId(idProj);

            // Criar um diálogo com as informações completas
            JDialog detalhes = new JDialog(frame, "Detalhes do Projeto", true);
            detalhes.setSize(500, 400);
            detalhes.setLocationRelativeTo(frame);
            detalhes.setLayout(new BorderLayout());

            JTextArea info = new JTextArea();
            info.setEditable(false);
            info.setText(
                "ID: " + p.getId() +
                "\nNome: " + p.getNome() +
                "\nDescrição: " + p.getDescricao() +
                "\nInício: " + p.getDataInicio().format(fmt) +
                "\nTérmino: " + p.getDataTerminoPrevisto().format(fmt) +
                "\nStatus: " + p.getStatus() +
                "\nGerente: " + p.getGerente().getNome() +
                "\nEquipes: " + (p.getEquipesAlocadas().isEmpty() ? "Nenhuma" :
                        String.join(", ", p.getEquipesAlocadas().stream().map(Equipe::getNome).toList())) +
                "\nTarefas: " + (p.getTarefas().isEmpty() ? "Nenhuma" :
                        p.getTarefas().stream().map(t -> t.getTitulo() + " (Responsável: " + t.getResponsavel().getNome() + ")")
                                .reduce("", (a, b) -> a + "\n - " + b))
            );
            detalhes.add(new JScrollPane(info), BorderLayout.CENTER);

            JButton fechar = new JButton("Fechar");
            fechar.addActionListener(ev -> detalhes.dispose());
            detalhes.add(fechar, BorderLayout.SOUTH);

            detalhes.setVisible(true);
        });

        frame.setVisible(true);
    }

    private void atualizarTabela(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Projeto> projetos = pc.listarProjetos();
        for(Projeto p : projetos) {
            String equipes = String.join(", ", p.getEquipesAlocadas().stream().map(Equipe::getNome).toList());
            String tarefas = String.join(", ", p.getTarefas().stream().map(Tarefa::getTitulo).toList());
            modelo.addRow(new Object[]{
                    p.getId(), p.getNome(), p.getDescricao(),
                    p.getDataInicio().format(fmt), p.getDataTerminoPrevisto().format(fmt),
                    p.getStatus(), p.getGerente().getNome(), equipes, tarefas
            });
        }
    }
}
