package model;

/**
 * Usuário gerente.
 */
public class Gerente extends Usuario {

    public Gerente(String nome, String cpf, String email, String cargo, String login, String senha) {
        super(nome, cpf, email, cargo, login, senha, Perfil.GERENTE);
    }

    @Override
    public String acessarSistema() {
        return "Acesso de gerente (pode gerenciar projetos).";
    }
}
