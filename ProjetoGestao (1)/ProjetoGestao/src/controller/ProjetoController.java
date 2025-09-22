package controller;

import model.Projeto;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller para gerenciar projetos em memória.
 */
public class ProjetoController {
    private final List<Projeto> projetos = new ArrayList<>();

    public void adicionarProjeto(Projeto p) {
        projetos.add(p);
    }

    public List<Projeto> listarProjetos() {
        return projetos;
    }

    public Projeto buscarPorId(int id) {
        for (Projeto p : projetos) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}
