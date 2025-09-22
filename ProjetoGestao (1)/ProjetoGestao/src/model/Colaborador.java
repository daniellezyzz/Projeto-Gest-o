package model;

/**
 * Usuário colaborador.
 */
public class Colaborador extends Usuario {

    public Colaborador(String nome, String cpf, String email, String cargo, String login, String senha) {
        super(nome, cpf, email, cargo, login, senha, Perfil.COLABORADOR);
    }

    @Override
    public String acessarSistema() {
        return "Acesso de colaborador (tarefas e participação em projetos).";
    }
}
