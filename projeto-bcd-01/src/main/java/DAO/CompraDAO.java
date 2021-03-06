package DAO;

import db.ConnectionFactory;
import entities.Categoria;
import entities.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo processo compra de um usuário
 */
public class CompraDAO {

    public List<String> listaCategorias() {
        List<String> categorias = new ArrayList<>();
        String query = "SELECT * FROM Categoria";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                categorias.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public List<List<String>> listaPrestadores(String categoria) {
        List<List<String>> prestadores = new ArrayList<>();
        String query = "SELECT DISTINCT u.nome, pt.login FROM Prestador pt INNER JOIN Produto p ON pt.login = p.loginPrestador " +
                "INNER JOIN Usuario u ON u.login = pt.login WHERE nomeCategoria = " + "\'" + categoria + "\'";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                List<String> prestador = new ArrayList<>();
                prestador.add(rs.getString("login"));
                prestador.add(rs.getString("nome"));
                prestadores.add(prestador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestadores;
    }

    public List<List<String>> listarProdutos(String prestador) {
        List<List<String>> listaProdutos = new ArrayList<>();
        String query = "SELECT p.id, p.nome, p.preco FROM Produto p INNER JOIN Prestador pt ON p.loginPrestador = pt.login " +
                "INNER JOIN Usuario u ON u.login = pt.login WHERE u.nome = " + "\'" + prestador + "\'" + " AND p.ativo = 1";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                // retornar nome, código, preco (na main fazer loop excluindo index fora da faixa e que nao existem)
                List<String> produtos = new ArrayList<>();

                produtos.add(String.valueOf(rs.getInt("id")));
                produtos.add(rs.getString("nome"));
                produtos.add(rs.getString("preco"));

                listaProdutos.add(produtos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdutos;
    }

    public int maxIDcompra() {
        String query = "SELECT MAX(id) as maxId FROM Compra";
        int maxId = 0;

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {

                maxId = rs.getInt("maxId");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    public void realizaCompra(List<Integer> listaIdprodutos, double total, String consumidor, String prestador, String data, String formaPag) {
        int idCompra = maxIDcompra()+1;
        String query =
                "INSERT INTO Compra (id,loginConsumidor,loginPrestador,inicio,status,total,formaPagamento) VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query)) {

            stmt.setInt(1,idCompra);
            stmt.setString(2,consumidor);
            stmt.setString(3, prestador);
            stmt.setString(4,data);
            stmt.setString(5,"pendente");
            stmt.setDouble(6,total);
            stmt.setString(7,formaPag);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void listaDeProdutos(int idCompra) {}

}
