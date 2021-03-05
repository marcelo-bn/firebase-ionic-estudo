import java.util.Scanner;

public class Principal {

    private final String[] CONSUMIDOR_principal = {
            "..:: Bem vindo! Selecione uma das opções ::..\n",
            "1 - Comprar",
            "2 - Listar todas as compras",
            "3 - Listar compras pendentes",
            "4 - Sair do sistema"
    };

    private final String[] PRESTADOR_principal = {
            "\n..:: Bem vindo! Selecione uma das opções ::..\n",
            "1 - Listar vendas realizadas",
            "2 - Listar vendas pendentes",
            "3 - Finalizar venda",
            "4 - Sair do sistema"
    };

    private Scanner teclado;
    private Usuario usuario;

    public Principal() {
        this.teclado = new Scanner(System.in);
        this.usuario = new Usuario();
    }

    public static void main(String[] args) {

        Principal p = new Principal();
        int op = -1;

        // Objeto Usuario
        Usuario u = p.login();
        System.out.println("> Olá, " + u.getNome() + "!");

        // Verificando tipo do usuário
        if (u.getTipo().equals("consumidor")) {
            op = p.menuGeral(p.CONSUMIDOR_principal);
            System.out.println(op);
        } else {
            System.out.println("prestador");
        }

    }

    private int menuGeral(String[] menuComOpcoes) {
        int opcao = -1;
        if (menuComOpcoes != null) {
            for (String linha : menuComOpcoes) {
                System.out.println(linha);
            }
            System.out.print("Entre com uma opção: ");
            try {
                opcao = teclado.nextInt();
            } catch (Exception e) {
                System.err.println("Erro. Informe um número inteiro.");
                opcao = -1;
                teclado.nextLine();// consumindo caracter extra NL/CR
            }

        }
        return opcao;
    }

    /**
     * Realiza o procedimento de login no sistema
     * @return Usuario
     */
    private Usuario login() {
        LoginMySQL app = new LoginMySQL();
        Usuario usuario = new Usuario();
        boolean ok = true;

        System.out.println("..:: Seja bem vindo! ::..");

        while (ok) {
            System.out.println("> Insira seu login: ");
            String nome = this.teclado.nextLine();
            String[] user = app.verificaLogin(nome);

            if (user[0].equals("error")) {
                System.out.println("> Login inválido");
            } else {
                usuario = app.infoLogin(user);
                ok = false;
            }
        }

        return usuario;
    }

    private void consumidor() {}

    private void prestador() {}
}
