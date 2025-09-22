package app;

import controller.EquipeController;
import controller.ProjetoController;
import controller.UsuarioController;
import view.ConsoleView;

/**
 * Ponto de entrada da aplicação.
 */
public class Main {
    public static void main(String[] args) {
        UsuarioController uc = new UsuarioController();
        ProjetoController pc = new ProjetoController();
        EquipeController ec = new EquipeController();

        ConsoleView view = new ConsoleView(uc, pc, ec);
        view.run();
    }
}
