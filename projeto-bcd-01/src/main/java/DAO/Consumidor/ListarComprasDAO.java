package DAO.Consumidor;

import db.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListarComprasDAO {

    /**
     * Realiza consulta de todas as compras de um cliente
     * @param loginConsumidor
     * @return Vetor de String, [0] - StringBuilder, [1] - String de idCompra
     */
    public String[] todasCompras(String loginConsumidor) {

        String query = "SELECT c.id as idCompra, u.nome as prestador, c.status, c.total, c.inicio, c.fim FROM Compra c " +
                "INNER JOIN Usuario u ON c.loginPrestador = u.login WHERE loginConsumidor = " + "\'" + loginConsumidor + "\'";

        StringBuilder sb = new StringBuilder();
        String lista_idCompra = "";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {

                sb.append("------------------------------------------------------------------------\n");
                sb.append(String.format("|%-10s|%-15s|%-10s|%-10s|%-10s|%-10s|\n", "Código", "Prestador", "Status", "Total", "Inicio", "Fim"));
                sb.append("------------------------------------------------------------------------\n");

                do {
                    int idCompra = rs.getInt("idCompra");
                    String prestador = rs.getString("prestador");
                    String status = rs.getString("status");
                    double total = rs.getDouble("total");
                    String inicio = rs.getString("inicio");
                    String fim = rs.getString("fim");

                    sb.append(String.format("|%-10s|%-15s|%-10s|%-10.2f|%-10s|%-10s|\n", idCompra, prestador, status, total, inicio, fim));
                    lista_idCompra += Integer.toString(idCompra) + ";";

                } while (rs.next());
                sb.append("------------------------------------------------------------------------\n");
            } else {
                sb.append("Não há registros no banco de dados\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] r = new String[2];
        r[0] = sb.toString();
        r[1] = lista_idCompra;
        return r;
    }

    /**
     * Lista produtos de uma determinada compra
     * @param loginConsumidor
     * @param idCompra
     * @return String com produtos
     */
    public String produtosCompra(String loginConsumidor, int idCompra) {

        String query = "SELECT p.nome, p.preco FROM Produto p INNER JOIN Lista_Produtos l ON p.id = l.idProduto " +
                "INNER JOIN Compra c ON l.idCompra = c.id WHERE c.loginConsumidor = " + "\'" + loginConsumidor + "\' AND c.id = " + idCompra;

        StringBuilder sb = new StringBuilder();

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {

                sb.append("-------------------------\n");
                sb.append(String.format("|%-15s|%-10s|\n", "Produto", "Preço"));
                sb.append("-------------------------\n");

                do {
                    String produto = rs.getString("nome");
                    double preco = rs.getDouble("preco");
                    sb.append(String.format("%-15s|%-10.2f|\n", produto, preco));
                } while (rs.next());
                sb.append("-------------------------\n");
            } else {
                sb.append("Não há registros no banco de dados\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public void pendentesCompras(String loginConsumidor) {}
}
