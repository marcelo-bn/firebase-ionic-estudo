package Menu.Consumidor;

import DAO.Consumidor.ListarComprasDAO;
import entities.Usuario;

import java.util.Scanner;

public class ListarComprasMenu {

    private Scanner teclado;

    public ListarComprasMenu() { this.teclado = new Scanner(System.in); }

    public boolean listar(Usuario u) {
        String query = "SELECT c.id as idCompra, u.nome as prestador, c.status, c.total, c.inicio, c.fim FROM Compra c " +
                "INNER JOIN Usuario u ON c.loginPrestador = u.login WHERE loginConsumidor = " + "\'" + u.getLogin() + "\'";
        return menu(query, u);
    }

    public boolean pendentes(Usuario u) {
        String query = "SELECT c.id as idCompra, u.nome as prestador, c.status, c.total, c.inicio, c.fim FROM Compra c " +
                "INNER JOIN Usuario u ON c.loginPrestador = u.login WHERE loginConsumidor = " + "\'" + u.getLogin() + "\' AND c.status = \'pendente\'";
        return menu(query, u);
    }

    public boolean menu(String query, Usuario u) {
        ListarComprasDAO l = new ListarComprasDAO();
        String[] compras = l.listarCompras(query);
        boolean aux = false; int op = -1; boolean voltar = false;

        // Tabela com todas as compras
        System.out.println(compras[0]);

        // Lista com idCompra
        String[] idCompra = compras[1].split(";");
        int[] idCompra_integer = new int[idCompra.length];
        for (int i = 0; i < idCompra_integer.length; i++) {
            idCompra_integer[i] = Integer.parseInt(idCompra[i]);
        }

        // Listando produtos de uma determinada compra
        do {
            System.out.println("> Selecione uma das opções:");
            System.out.println("1 - Listar produtos de uma compra");
            System.out.println("2 - Voltar ao menu principal");
            op = teclado.nextInt();

            switch (op) {
                case 1:
                    do {
                        System.out.println("> Digite o código de uma compra:");
                        boolean contem = false;
                        op = teclado.nextInt();
                        for (int j : idCompra_integer) { // Verifica se op é um dos idCompra
                            if (op == j) {
                                aux = true;
                                contem = true;
                                break;
                            }
                        }
                        if (!contem) { System.out.println("> Código inválido!"); }
                    } while (!aux);
                    System.out.println(l.produtosCompra(u.getLogin(), op));
                    aux = false;
                    break;
                case 2:
                    aux = true;
                    break;
                default:
                    System.out.println("> Opção inválida!");
            }

        } while (!aux);

        return true;
    }


}
