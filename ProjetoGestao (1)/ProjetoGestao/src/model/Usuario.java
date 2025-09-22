package model;

/**
 * Superclasse que representa um usuário do sistema.
 * Implementa encapsulamento e um construtor parametrizado.
 */
public abstract class Usuario {
    private static int nextId = 1;

    private final int id;
    private String nome;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;
    private Perfil perfil;

    /**
     * Construtor parametrizado.
     */
    public Usuario(String nome, String cpf, String email, String cargo, String login, String senha, Perfil perfil) {
        this.id = nextId++;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    /* Getters e setters (encapsulamento) */
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    /**
     * Método para demonstrar polimorfismo: subclasses sobrescrevem este método.
     */
    public String acessarSistema() {
        return "Acesso padrão de usuário.";
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - %s", id, nome, perfil, email);
    }
}
