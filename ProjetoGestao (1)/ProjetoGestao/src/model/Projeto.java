package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um projeto com tarefas e equipes alocadas.
 */
public class Projeto {
    private static int nextId = 1;
    private final int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevisto;
    private StatusProjeto status;
    private Gerente gerente; // gerente responsável
    private List<Tarefa> tarefas = new ArrayList<>();
    private List<Equipe> equipesAlocadas = new ArrayList<>();

    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevisto, StatusProjeto status, Gerente gerente) {
        this.id = nextId++;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevisto = dataTerminoPrevisto;
        this.status = status;
        this.gerente = gerente;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataTerminoPrevisto() { return dataTerminoPrevisto; }
    public void setDataTerminoPrevisto(LocalDate dataTerminoPrevisto) { this.dataTerminoPrevisto = dataTerminoPrevisto; }
    public StatusProjeto getStatus() { return status; }
    public void setStatus(StatusProjeto status) { this.status = status; }
    public Gerente getGerente() { return gerente; }
    public void setGerente(Gerente gerente) { this.gerente = gerente; }

    public List<Tarefa> getTarefas() { return tarefas; }
    public List<Equipe> getEquipesAlocadas() { return equipesAlocadas; }

    public void adicionarTarefa(Tarefa t) {
        tarefas.add(t);
    }

    public void alocarEquipe(Equipe e) {
        if (!equipesAlocadas.contains(e)) equipesAlocadas.add(e);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (status: %s) | Gerente: %s | Equipes: %d | Tarefas: %d",
                id, nome, descricao, status, (gerente == null ? "Nenhum" : gerente.getNome()), equipesAlocadas.size(), tarefas.size());
    }
}
