package controller;

import model.Perfil;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller para gerenciar usuários em memória.
 */
public class UsuarioController {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void adicionarUsuario(Usuario u) {
        usuarios.add(u);
    }

    public List<Usuario> listarUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public List<Usuario> listarPorPerfil(Perfil perfil) {
        List<Usuario> r = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getPerfil() == perfil) r.add(u);
        }
        return r;
    }
}
