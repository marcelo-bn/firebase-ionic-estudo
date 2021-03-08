package DAO.Consumidor;

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

    /**
     * Realiza a consulta de todas as categorias
     * @return Retorna uma lista de Strings como o nome das Categorias
     */
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

    /**
     * Consulta os prestadores de determinada Categoria
     * @param categoria
     * @return Lista de Listas de String contendo informações relevantes dos Prestadores
     */
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

    /**
     * Consulta os produtos de determinado Prestador
     * @param prestador
     * @return Lista de Listas de String contendo informações relevantes dos Produtos
     */
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

    /**
     * Consulta o id máximo presente na tabela Compra
     * @return id máximo
     */
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

    /**
     * Insere ocorrência na tabela Compra
     * @param listaIdprodutos
     * @param total
     * @param consumidor
     * @param prestador
     * @param data
     * @param formaPag
     * @return Id da ocorrência
     */
    public int realizaCompra(List<Integer> listaIdprodutos, double total, String consumidor, String prestador, String data, String formaPag) {
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

        return idCompra;
    }

    /**
     * Insere ocorrência na tabela Lista_Produtos
     * @param idProduto
     * @param idCompra
     */
    public void listaDeProdutos(int idProduto, int idCompra) {

        String query = "INSERT INTO Lista_Produtos (idProduto,idCompra) VALUES (?,?)";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query)) {
            stmt.setInt(1,idProduto);
            stmt.setInt(2,idCompra);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
