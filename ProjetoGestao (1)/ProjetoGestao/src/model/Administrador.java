package model;

/**
 * Usuário administrador.
 */
public class Administrador extends Usuario {

    public Administrador(String nome, String cpf, String email, String cargo, String login, String senha) {
        super(nome, cpf, email, cargo, login, senha, Perfil.ADMINISTRADOR);
    }

    @Override
    public String acessarSistema() {
        return "Acesso total (Administrador).";
    }
}
