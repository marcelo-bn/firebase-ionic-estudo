package Menu.Consumidor;

import DAO.Consumidor.CompraDAO;
import entities.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Realiza os procedimentos de compra do Consumidor
 */
public class CompraMenu {

    private Scanner teclado;

    public CompraMenu() { this.teclado = new Scanner(System.in); }

    public boolean consumidorCompra(Usuario usuario) {
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
        if (usuario.getNumCartao() != null) {
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

        // Inseridno ocorrência na entidade Compra
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        int idCompra = compra.realizaCompra(produtosEscolhasId, total, usuario.getLogin(), prestadorEscolhidoLogin, date, formaPagamento);

        // Inseridno ocorrência na entidade Lista_Produtos
        for (int idProduto: produtosEscolhasId) {
            compra.listaDeProdutos(idProduto, idCompra);
        }

        return true;
    }

}
