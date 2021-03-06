import DAO.CompraDAO;
import DAO.LoginDAO;
import entities.Categoria;
import entities.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String[] CONSUMIDOR_principal = {
            "\n> Menu principal, selecione uma das opções: ",
            "1 - Comprar",
            "2 - Listar todas as compras",
            "3 - Listar compras pendentes",
            "4 - Sair do sistema"
    };

    private final String[] PRESTADOR_principal = {
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
        System.out.print("\n..:: Olá, " + u.getNome() + "! ::..");

        // Verificando tipo do usuário
        if (u.getTipo().equals("consumidor")) {
            while (true) {
                op = p.menuGeral(p.CONSUMIDOR_principal);

                switch (op) {
                    case 1:
                        if (p.consumidorCompra()) {
                            System.out.println("> Compra realizada!");
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

        System.out.println("\n..:: Sistema de Serviços e Produtos ::..");

        while (ok) {
            System.out.println("> Insira seu login: ");
            String nome = this.teclado.nextLine();
            String[] user = app.verificaLogin(nome);

            if (user[0].equals("error")) {
                System.out.println("> Login inválido");
            } else {
                usuario = app.infoLogin(user);
                this.usuario = usuario;
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
        System.out.println("\n> Escolha uma das categorias");
        do {
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i+1) + " - " + categorias.get(i));
            }
            System.out.println((categorias.size()+1) + " - Voltar ao menu principal");

            op = teclado.nextInt();

            if ((op > categorias.size()+1) || (op < 1)) {
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
        List<List<String>> prestadores;
        prestadores = compra.listaPrestadores(categorias.get(op-1));
        System.out.println("\n> Vendedores da categoria ..:: " + categorias.get(op-1) + " ::..");
        aux = false; op = -1;
        do {
            for (int i = 0; i < prestadores.size(); i++) {
                System.out.println((i+1) + " | " + prestadores.get(i).get(1) + "  (" + prestadores.get(i).get(0) + ")");
            }
            System.out.println((prestadores.size()+1) + " - Voltar ao menu principal");

            op = teclado.nextInt();

            if ((op > prestadores.size()+1) || (op < 1)) {
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

        // Escolhendo produtos
        String prestadorEscolhidoLogin = prestadores.get(op-1).get(0);
        String prestadorEscolhidoNome = prestadores.get(op-1).get(1);
        System.out.println("\n> Vendedor escolhido ..:: " + prestadorEscolhidoNome + " ::..");
        List<List<String>> listaProdutos;
        listaProdutos = compra.listarProdutos(prestadorEscolhidoNome);
        System.out.println("> Produtos disponíveis:");
        aux = false; op = -1;
        List<String> produtosEscolhasNome = new ArrayList<>();
        List<Double> produtosEscolhasValor = new ArrayList<>();
        List<Integer> produtosEscolhasId = new ArrayList<>();

        for (int i = 0; i < listaProdutos.size(); i++) {
            System.out.println((i+1) + " | " + listaProdutos.get(i).get(1) + " | " + listaProdutos.get(i).get(2));
        }
        System.out.println((listaProdutos.size()+1) + " - Finalizar compra");
        System.out.println((listaProdutos.size()+2) + " - Voltar ao menu principal");

        do {
            op = teclado.nextInt();

            if ((op > listaProdutos.size()+2) || (op < 0)) {
                System.out.println("> Escolha inválida!");
            }
            else if (op == listaProdutos.size()+2) {
                voltar = true;
                break;
            }
            else if (op == listaProdutos.size()+1) {
                aux = true;
            } else {
                produtosEscolhasId.add(Integer.parseInt(listaProdutos.get(op-1).get(0)));
                produtosEscolhasNome.add(listaProdutos.get(op-1).get(1));
                produtosEscolhasValor.add(Double.parseDouble(listaProdutos.get(op-1).get(2)));
            }

        } while(!aux);

        // Verifica se deve cancelar a compra
        if (voltar) {
            return false;
        }

        // Visualizando compra
        double total = 0;
        System.out.println("> Produtos escolhidos:");
        for (int i = 0; i < produtosEscolhasNome.size(); i++) {
            System.out.println(produtosEscolhasNome.get(i) + " - R$ " + produtosEscolhasValor.get(i));
            total += produtosEscolhasValor.get(i);
        }
        total = Math.floor(total * 100) / 100;
        System.out.println("> Valor total: R$ " + total);

        // Forma de pagamento
        aux = false; op = -1;
        String formaPagamento = "";
        if (this.usuario.getNumCartao() != null) {
            System.out.println("> Forma de pagamento: ");
            do {
                System.out.println("1 - Cartão");
                System.out.println("2 - Dinheiro");
                op = teclado.nextInt();

                switch (op) {
                    case 1:
                        formaPagamento = "cartão";
                        aux = true;
                        break;
                    case 2:
                        formaPagamento = "dinheiro";
                        aux = true;
                        break;
                    default:
                        System.out.println("> Opção inválida!");
                }
            } while (!aux);
        } else {
            formaPagamento = "dinheiro";
        }

        // Confirmando compra
        aux = false; op = -1;
        System.out.println("> Confirmar compra:");
        do {
            System.out.println("1 - Confirmar compra");
            System.out.println("2 - Voltar ao menu principal");
            op = teclado.nextInt();

           if (op == 2) {
                voltar = true;
                aux = true;
            }
            else if (op == 1) {
                aux = true;
            } else {
               System.out.println("> Escolha inválida!");
            }

        } while (!aux);

        // Verifica se deve cancelar a compra
        if (voltar) {
            return false;
        }

        // Realizando compra
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        compra.realizaCompra(produtosEscolhasId, total, this.usuario.getLogin(), prestadorEscolhidoLogin, date, formaPagamento);

        return true;
    }

    private void prestador() {}
}
