package view;

import controller.EquipeController;
import controller.ProjetoController;
import controller.UsuarioController;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de console (View) que interage com o usuário e os controllers.
 */
public class ConsoleView {
    private final UsuarioController uc;
    private final ProjetoController pc;
    private final EquipeController ec;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleView(UsuarioController uc, ProjetoController pc, EquipeController ec) {
        this.uc = uc;
        this.pc = pc;
        this.ec = ec;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            mostrarMenu();
            try {
                System.out.print("Opção: ");
                String line = sc.nextLine();
                int opc = Integer.parseInt(line);
                switch (opc) {
                    case 0 -> { System.out.println("Saindo..."); running = false; }
                    case 1 -> cadastrarUsuario(sc);
                    case 2 -> listarUsuarios();
                    case 3 -> cadastrarProjeto(sc);
                    case 4 -> listarProjetos();
                    case 5 -> cadastrarEquipe(sc);
                    case 6 -> adicionarMembrosAEquipe(sc); // agora chama a versão sem parâmetro
                    case 7 -> listarEquipes();
                    case 8 -> alocarEquipeEmProjeto(sc);
                    case 9 -> adicionarTarefaAProjeto(sc);
                    case 10 -> mostrarDetalhesProjeto(sc);
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        }
        sc.close();
    }

    private void mostrarMenu() {
        System.out.println("=== Sistema de Gestão de Projetos ===");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Listar Usuários");
        System.out.println("3 - Cadastrar Projeto");
        System.out.println("4 - Listar Projetos");
        System.out.println("5 - Cadastrar Equipe");
        System.out.println("6 - Adicionar Usuário em Equipe");
        System.out.println("7 - Listar Equipes");
        System.out.println("8 - Alocar Equipe em Projeto");
        System.out.println("9 - Adicionar Tarefa a Projeto");
        System.out.println("10 - Mostrar Detalhes de Projeto");
    }

    private void cadastrarUsuario(Scanner sc) {
        try {
            System.out.println("== Cadastrar Usuário ==");
            System.out.print("Nome completo: ");
            String nome = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            System.out.print("E-mail: ");
            String email = sc.nextLine();
            System.out.print("Cargo: ");
            String cargo = sc.nextLine();
            System.out.print("Login: ");
            String login = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();

            System.out.println("Perfil: 1-ADMIN  2-GERENTE  3-COLABORADOR");
            System.out.print("Escolha (1/2/3): ");
            int p = Integer.parseInt(sc.nextLine());

            Usuario u;
            if (p == 1) {
                u = new Administrador(nome, cpf, email, cargo, login, senha);
            } else if (p == 2) {
                u = new Gerente(nome, cpf, email, cargo, login, senha);
            } else {
                u = new Colaborador(nome, cpf, email, cargo, login, senha);
            }
            uc.adicionarUsuario(u);
            System.out.println("Usuário cadastrado: " + u);
        } catch (NumberFormatException e) {
            System.out.println("Perfil inválido. Operação cancelada.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
    
    private void listarUsuarios() {
        System.out.println("== Lista de Usuários ==");
        List<Usuario> usuarios = uc.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    private void cadastrarProjeto(Scanner sc) {
        try {
            System.out.println("== Cadastrar Projeto ==");
            System.out.print("Nome do projeto: ");
            String nome = sc.nextLine();
            System.out.print("Descrição: ");
            String desc = sc.nextLine();
            System.out.print("Data início (DD/MM/YYYY): ");
            LocalDate inicio = LocalDate.parse(sc.nextLine(), fmt);
            System.out.print("Data término prevista (DD/MM/YYYY): ");
            LocalDate termino = LocalDate.parse(sc.nextLine(), fmt);
            System.out.println("Status: 1-PLANEJADO 2-EM_ANDAMENTO 3-CONCLUIDO 4-CANCELADO");
            System.out.print("Escolha (1-4): ");
            int s = Integer.parseInt(sc.nextLine());
            StatusProjeto status = switch (s) {
                case 1 -> StatusProjeto.PLANEJADO;
                case 2 -> StatusProjeto.EM_ANDAMENTO;
                case 3 -> StatusProjeto.CONCLUIDO;
                default -> StatusProjeto.CANCELADO;
            };

            System.out.println("Escolha o gerente responsável (digite o ID). Lista de gerentes:");
            List<Usuario> gerentes = uc.listarPorPerfil(Perfil.GERENTE);
            if (gerentes.isEmpty()) {
                System.out.println("Nenhum gerente cadastrado. Cadastre um gerente antes de criar o projeto.");
                return;
            }
            for (Usuario g : gerentes) System.out.println(g);
            System.out.print("ID do gerente: ");
            int idGerente = Integer.parseInt(sc.nextLine());
            Usuario gu = uc.buscarPorId(idGerente);
            if (!(gu instanceof Gerente)) {
                System.out.println("ID informado não é um gerente. Operação cancelada.");
                return;
            }
            Gerente gerente = (Gerente) gu;

            Projeto p = new Projeto(nome, desc, inicio, termino, status, gerente);
            pc.adicionarProjeto(p);
            System.out.println("Projeto cadastrado: " + p);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato DD/MM/YYYY.");
        } catch (NumberFormatException e) {
            System.out.println("Entrada numérica inválida.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar projeto: " + e.getMessage());
        }
    }

    private void listarProjetos() {
        System.out.println("== Lista de Projetos ==");
        List<Projeto> ps = pc.listarProjetos();
        if (ps.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
            return;
        }
        for (Projeto p : ps) {
            System.out.println(p);
        }
    }

    private void cadastrarEquipe(Scanner sc) {
        try {
            System.out.println("== Cadastrar Equipe ==");
            System.out.print("Nome da equipe: ");
            String nome = sc.nextLine();
            System.out.print("Descrição: ");
            String desc = sc.nextLine();
            Equipe e = new Equipe(nome, desc);
            ec.adicionarEquipe(e);
            System.out.println("Equipe criada: " + e);

            System.out.print("Deseja adicionar membros agora? (s/n): ");
            String r = sc.nextLine();
            if (r.equalsIgnoreCase("s")) {
                adicionarMembrosAEquipe(sc, e);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao criar equipe: " + ex.getMessage());
        }
    }

    /**
     * Versão usada ao criar equipe (já existente no seu código) —
     * recebe a equipe alvo como parâmetro e pede IDs separados por vírgula.
     */
    private void adicionarMembrosAEquipe(Scanner sc, Equipe e) {
        listarUsuarios();
        System.out.println("Digite os IDs dos usuários para adicionar (separados por vírgula): ");
        String linha = sc.nextLine();
        String[] parts = linha.split(",");
        for (String p : parts) {
            try {
                int id = Integer.parseInt(p.trim());
                Usuario u = uc.buscarPorId(id);
                if (u != null) {
                    e.adicionarMembro(u);
                    System.out.println("Adicionado: " + u.getNome());
                } else {
                    System.out.println("Usuário ID " + id + " não encontrado.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("ID inválido: " + p);
            }
        }
    }

    /**
     * Nova versão sem parâmetro — lista equipes e permite escolher qual equipe
     * vai receber os membros. Em seguida delega para adicionarMembrosAEquipe(sc, equipe).
     */
    private void adicionarMembrosAEquipe(Scanner sc) {
        List<Equipe> equipes = ec.listarEquipes();
        if (equipes.isEmpty()) {
            System.out.println("Nenhuma equipe cadastrada.");
            return;
        }

        System.out.println("== Equipes cadastradas ==");
        for (Equipe eq : equipes) {
            System.out.println(eq);
        }

        try {
            System.out.print("Digite o ID da equipe para adicionar membros: ");
            int idEq = Integer.parseInt(sc.nextLine());
            Equipe equipeSelecionada = ec.buscarPorId(idEq);
            if (equipeSelecionada == null) {
                System.out.println("Equipe não encontrada.");
                return;
            }
            // chama a versão que pede os IDs dos usuários
            adicionarMembrosAEquipe(sc, equipeSelecionada);
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida. Informe um ID numérico.");
        }
    }

    private void listarEquipes() {
        System.out.println("== Lista de Equipes ==");
        List<Equipe> es = ec.listarEquipes();
        if (es.isEmpty()) {
            System.out.println("Nenhuma equipe cadastrada.");
            return;
        }
        for (Equipe e : es) {
            System.out.println(e);
            if (!e.getMembros().isEmpty()) {
                System.out.println("  Membros:");
                for (Usuario m : e.getMembros()) System.out.println("    - " + m);
            }
        }
    }

    private void alocarEquipeEmProjeto(Scanner sc) {
        try {
            listarProjetos();
            System.out.print("Digite o ID do projeto: ");
            int idProj = Integer.parseInt(sc.nextLine());
            Projeto p = pc.buscarPorId(idProj);
            if (p == null) { System.out.println("Projeto não encontrado."); return; }

            listarEquipes();
            System.out.print("Digite o ID da equipe: ");
            int idEq = Integer.parseInt(sc.nextLine());
            Equipe e = ec.buscarPorId(idEq);
            if (e == null) { System.out.println("Equipe não encontrada."); return; }

            p.alocarEquipe(e);
            System.out.println("Equipe alocada ao projeto com sucesso.");
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida.");
        }
    }

    private void adicionarTarefaAProjeto(Scanner sc) {
        try {
            listarProjetos();
            System.out.print("ID do projeto: ");
            int idProj = Integer.parseInt(sc.nextLine());
            Projeto p = pc.buscarPorId(idProj);
            if (p == null) { System.out.println("Projeto não encontrado."); return; }

            System.out.print("Título da tarefa: ");
            String titulo = sc.nextLine();
            System.out.print("Descrição: ");
            String desc = sc.nextLine();
            System.out.println("Status da tarefa: 1-PENDENTE 2-EM_ANDAMENTO 3-CONCLUIDA");
            System.out.print("Escolha (1-3): ");
            int s = Integer.parseInt(sc.nextLine());
            StatusTarefa st = switch (s) {
                case 1 -> StatusTarefa.PENDENTE;
                case 2 -> StatusTarefa.EM_ANDAMENTO;
                default -> StatusTarefa.CONCLUIDA;
            };

            System.out.print("Deseja atribuir um responsável agora? (s/n): ");
            String r = sc.nextLine();
            Usuario resp = null;
            if (r.equalsIgnoreCase("s")) {
                listarUsuarios();
                System.out.print("ID do responsável: ");
                int idResp = Integer.parseInt(sc.nextLine());
                resp = uc.buscarPorId(idResp);
                if (resp == null) System.out.println("Usuário não encontrado. Sem responsável atribuído.");
            }

            Tarefa t = new Tarefa(titulo, desc, st, resp);
            p.adicionarTarefa(t);
            System.out.println("Tarefa adicionada: " + t);
        } catch (NumberFormatException e) {
            System.out.println("Entrada numérica inválida.");
        }
    }

    private void mostrarDetalhesProjeto(Scanner sc) {
        try {
            listarProjetos();
            System.out.print("ID do projeto para ver detalhes: ");
            int idProj = Integer.parseInt(sc.nextLine());
            Projeto p = pc.buscarPorId(idProj);
            if (p == null) { System.out.println("Projeto não encontrado."); return; }
            System.out.println("== Detalhes do Projeto ==");
            System.out.println(p);
            if (!p.getEquipesAlocadas().isEmpty()) {
                System.out.println("Equipes alocadas:");
                for (Equipe e : p.getEquipesAlocadas()) System.out.println("  - " + e);
            }
            if (!p.getTarefas().isEmpty()) {
                System.out.println("Tarefas:");
                for (Tarefa t : p.getTarefas()) System.out.println("  - " + t);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }
}
