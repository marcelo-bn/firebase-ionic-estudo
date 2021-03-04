import java.util.Scanner;

public class Principal {

    private final String[] LOGIN = {
            "\n..:: Olá! insira seu login ::..\n"
    };

    private final String[] CLIENTE_principal = {
            "\n..:: Bem vindo! Selecione uma das opções ::..\n",
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

    public Principal() {
        this.teclado = new Scanner(System.in);
    }

    public static void main(String[] args) {

        Principal p = new Principal();
        int op = -1;

        //System.out.println(p.LOGIN);
        Usuario u = p.login();

        // Scanner teclado = new Scanner(System.in);;
        /*LoginMySQL app = new LoginMySQL();
        System.out.println("Insira seu login: ");

        String nome = this.teclado.nextLine();
        String user = app.listaLogin(nome);
        if (user.equals("error")) {
            System.out.println("Login inválido");
        } else {
            System.out.println("Bem vindo " + user + "!");
        }*/
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

    private Usuario login() {
        LoginMySQL app = new LoginMySQL();
        Usuario usuario = new Usuario();
        String[] teste = new String[7];

        /* Autenticando Login*/
        System.out.println("Insira seu login: ");
        String nome = this.teclado.nextLine();
        String user = app.verificaLogin(nome);
        if (user.equals("error")) {
            System.out.println("Login inválido");
        } else {
            System.out.println("Bem vindo " + user + "!");

            /* Pegando informações do Login*/
            teste = app.infoLogin(user);

            for (int i = 0; i < 7; i++) {
                System.out.println(teste[i]);
            }

        }
        return usuario;
    }
    private void compra() {}
}
