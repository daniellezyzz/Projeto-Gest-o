package model;

/**
 * Representa uma tarefa dentro de um projeto.
 */
public class Tarefa {
    private static int nextId = 1;
    private final int id;
    private String titulo;
    private String descricao;
    private StatusTarefa status;
    private Usuario responsavel; // pode ser null

    public Tarefa(String titulo, String descricao, StatusTarefa status, Usuario responsavel) {
        this.id = nextId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.responsavel = responsavel;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusTarefa getStatus() { return status; }
    public void setStatus(StatusTarefa status) { this.status = status; }
    public Usuario getResponsavel() { return responsavel; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }

    @Override
    public String toString() {
        String r = (responsavel == null) ? "Sem responsável" : responsavel.getNome();
        return String.format("[%d] %s - %s (%s) - %s", id, titulo, descricao, status, r);
    }
}
