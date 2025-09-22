package controller;

import model.Equipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller para gerenciar equipes em memória.
 */
public class EquipeController {
    private final List<Equipe> equipes = new ArrayList<>();

    public void adicionarEquipe(Equipe e) {
        equipes.add(e);
    }

    public List<Equipe> listarEquipes() {
        return equipes;
    }

    public Equipe buscarPorId(int id) {
        for (Equipe e : equipes) {
            if (e.getId() == id) return e;
        }
        return null;
    }
}
