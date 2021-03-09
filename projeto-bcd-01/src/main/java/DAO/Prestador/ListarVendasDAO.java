package DAO.Prestador;

import db.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListarVendasDAO {

    public String[] listarVendas(String q) {
        StringBuilder sb = new StringBuilder();
        String lista_idCompra = "";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(q);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {

                sb.append("------------------------------------------------------------------------\n");
                sb.append(String.format("|%-10s|%-15s|%-10s|%-10s|%-10s|%-10s|\n", "Código", "Consumidor", "Status", "Total", "Inicio", "Fim"));
                sb.append("------------------------------------------------------------------------\n");

                do {
                    int idCompra = rs.getInt("id");
                    String consumidor = rs.getString("consumidor");
                    String status = rs.getString("status");
                    double total = rs.getDouble("total");
                    String inicio = rs.getString("inicio");
                    String fim = rs.getString("fim");

                    sb.append(String.format("|%-10s|%-15s|%-10s|%-10.2f|%-10s|%-10s|\n", idCompra, consumidor, status, total, inicio, fim));
                    lista_idCompra += Integer.toString(idCompra) + ";"; // Array contendo os idCompras

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
     * Lista produtos de uma determinada venda
     * @param loginPrestador
     * @param idCompra
     * @return String com produtos
     */
    public String produtosVenda(String loginPrestador, int idCompra) {

        String query = "SELECT p.nome, p.preco FROM Produto p INNER JOIN Lista_Produtos l ON p.id = l.idProduto " +
                "INNER JOIN Compra c ON l.idCompra = c.id WHERE c.loginPrestador = " + "\'" + loginPrestador + "\' AND c.id = " + idCompra;

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

}
