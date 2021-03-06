package DAO;

import db.ConnectionFactory;
import entities.Categoria;

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

    public List<String> listaPrestadores(String categoria) {
        List<String> prestadores = new ArrayList<>();
        String query = "SELECT DISTINCT u.nome, pt.login FROM Prestador pt INNER JOIN Produto p ON pt.login = p.loginPrestador " +
                "INNER JOIN Usuario u ON u.login = pt.login WHERE nomeCategoria = " + "\'" + categoria + "\'";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                prestadores.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestadores;
    }

    public void listarProdutos() {}

}
