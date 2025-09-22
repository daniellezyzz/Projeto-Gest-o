package app.models;

import app.models.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Projeto> projetos = new ArrayList<>();
    private static List<Equipe> equipes = new ArrayList<>();

    public static List<Usuario> getUsuarios() { return usuarios; }
    public static List<Projeto> getProjetos() { return projetos; }
    public static List<Equipe> getEquipes() { return equipes; }

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static void adicionarProjeto(Projeto projeto) {
        projetos.add(projeto);
    }

    public static void adicionarEquipe(Equipe equipe) {
        equipes.add(equipe);
    }
}
