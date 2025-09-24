package controller;

import model.Perfil;
import model.Usuario;
import app.models.Validador;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private final List<Usuario> usuarios = new ArrayList<>();

    public void adicionarUsuario(Usuario u) {
        if (!Validador.validarEmail(u.getEmail())) {
            System.out.println("Email inválido: " + u.getEmail());
            return;
        }
        if (!Validador.validarCPF(u.getCpf())) {
            System.out.println("CPF inválido: " + u.getCpf());
            return;
        }
        usuarios.add(u);
        System.out.println("Usuário cadastrado com sucesso: " + u.getNome());
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

