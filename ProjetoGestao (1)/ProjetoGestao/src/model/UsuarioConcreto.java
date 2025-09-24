package model;

public class UsuarioConcreto extends Usuario {
    public UsuarioConcreto(String nome, String cpf, String email, String cargo, String login, String senha, Perfil perfil) {
        super(nome, cpf, email, cargo, login, senha, perfil);
    }
}
