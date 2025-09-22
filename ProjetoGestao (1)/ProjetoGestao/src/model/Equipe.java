package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma equipe — agregação de usuários e pode atuar em vários projetos.
 */
public class Equipe {
    private static int nextId = 1;
    private final int id;
    private String nome;
    private String descricao;
    private List<Usuario> membros = new ArrayList<>();

    public Equipe(String nome, String descricao) {
        this.id = nextId++;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Usuario> getMembros() { return membros; }

    public void adicionarMembro(Usuario u) {
        if (!membros.contains(u)) {
            membros.add(u);
        }
    }

    public void removerMembro(Usuario u) {
        membros.remove(u);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (membros: %d)", id, nome, descricao, membros.size());
    }
}
