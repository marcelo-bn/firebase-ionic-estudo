import DAO.CompraDAO;
import DAO.LoginDAO;
import entities.Categoria;
import entities.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String[] CONSUMIDOR_principal = {
            "> Bem vindo! Selecione uma das opções \n",
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

        // Objeto entities.Usuario
        Usuario u = p.login();
        System.out.println("> Olá, " + u.getNome() + "!");

        // Verificando tipo do usuário
        if (u.getTipo().equals("consumidor")) {
            while (true) {
                op = p.menuGeral(p.CONSUMIDOR_principal);

                switch (op) {
                    case 1:
                        if (p.consumidorCompra()) {
                            System.out.println("> Compra finalizada!");
                        } else {
                            System.out.println("> Compra cancelada!");
                        }
                        break;
                    case 2:
                        System.out.println();
                        break;
                    default:
                        System.out.println("> Opção inválida!");
                        break;
                }
            }

        } else {
            op = p.menuGeral(p.PRESTADOR_principal);
            System.out.println(op);
        }

    }

    /**
     * Apresenta menu de opções
     * @param menuComOpcoes
     * @return
     */
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
     * @return entities.Usuario
     */
    private Usuario login() {
        LoginDAO app = new LoginDAO();
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

    /**
     * Realiza os procedimentos do Consumidor
     */
    private boolean consumidorCompra() {
        CompraDAO compra = new CompraDAO();
        List<String> categorias;
        categorias = compra.listaCategorias();
        boolean aux = false;
        boolean voltar = false;
        int op = -1;

        // Escolhendo Categoria
        System.out.println("> Escolha uma das categorias");
        do {
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i+1) + " - " + categorias.get(i));
            }
            System.out.println((categorias.size()+1) + " - Voltar ao menu principal");

            op = teclado.nextInt();

            if ((op > categorias.size()+1) || (op < 0)) {
                System.out.println("> Escolha inválida!");
            }
            else if (op == categorias.size()+1) {
                voltar = true;
                break;
            }
            else {
                aux = true;
            }
        } while(!aux);

        // Verifica se deve cancelar a compra
        if (voltar) {
            return false;
        }

        // Escolhendo Prestador
        List<String> prestadores;
        prestadores = compra.listaPrestadores(categorias.get(op-1));
        aux = false; op = -1;
        System.out.println("> Vendedores da categoria");
        do {
            for (int i = 0; i < prestadores.size(); i++) {
                System.out.println((i+1) + " - " + prestadores.get(i));
            }
            System.out.println((prestadores.size()+1) + " - Voltar ao menu principal");

            op = teclado.nextInt();

            if ((op > prestadores.size()+1) || (op < 0)) {
                System.out.println("> Escolha inválida!");
            }
            else if (op == prestadores.size()+1) {
                voltar = true;
                break;
            }
            else {
                aux = true;
            }
        } while(!aux);

        // Verifica se deve cancelar a compra
        if (voltar) {
            return false;
        }

        // Listar produtos do vendedor
        // Lista de produtos
        // O retorno da lista terá que ser objetos
        // Inserir vendedores de todas as categorias

        // Escolhendo produtos
        System.out.println("> Vendedor escolhido .:: " + prestadores.get(op-1) + "::.");
        List<List<String>> listaProdutos;
        listaProdutos = compra.listarProdutos(prestadores.get(op-1));
        System.out.println("> Produtos disponíveis:");
        aux = false; op = -1;
        boolean finalizar = false;

        do {
            for (int i = 0; i < listaProdutos.size(); i++) {
                System.out.println((i+1) + " | " + listaProdutos.get(i).get(0) + " | " + listaProdutos.get(i).get(1)
                        + " | " + listaProdutos.get(i).get(2));
            }
            System.out.println((prestadores.size()+1) + " - Finalizar compra");
            System.out.println((prestadores.size()+2) + " - Voltar ao menu principal");

            op = teclado.nextInt();

            if ((op > prestadores.size()+2) || (op < 0)) {
                System.out.println("> Escolha inválida!");
            }
            else if (op == prestadores.size()+1) {
                voltar = true;
                break;
            }
            else if (op == prestadores.size()+2) {
                finalizar = true;
                aux = true;
            }
            
        } while(!aux);

        // Verifica se deve cancelar a compra
        if (voltar) {
            return false;
        }

        return true;
    }

    private void prestador() {}
}
