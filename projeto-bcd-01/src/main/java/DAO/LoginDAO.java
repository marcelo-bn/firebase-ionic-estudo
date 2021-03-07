package DAO;

import db.ConnectionFactory;
import entities.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável pelo processo de Login do usuário
 */
public class LoginDAO {

    /**
     * Listando todos os logins de Consumidor e Prestador
     * @param n
     * @return Vetor de String como todos os logins (Consumidor e Prestador)
     */
    public String[] verificaLogin(String n) {
        String nome = n;
        String[] user = new String[2];
        String query = "SELECT u.login, t.tipo FROM Usuario u INNER JOIN Tipo t ON u.tipo = t.id";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            boolean aux = false;

            while(rs.next()) {
                String loginrs = rs.getString("login");
                String tipors = rs.getString("tipo");
                if (loginrs.equals(nome)) {
                    aux = true;
                    user[0] = loginrs;
                    user[1] = tipors;
                }
            }

            if (!aux) {
                user[0] = "error";
                user[1] = "error";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Informações de determinado usuário
     * @param login
     * @return Retornar um objeto da classe Usuario
     */
    public Usuario infoLogin(String[] login) {
        String query = "";

        if (login[1].equals("consumidor")) {
            query = "SELECT u.login, u.senha, u.nome, u.telefone, u.endereco, cm.numCartao, t.tipo FROM Usuario u" +
                    " INNER JOIN Consumidor cm ON cm.login = u.login INNER JOIN Tipo t ON t.id = u.tipo WHERE u.login = " + "\'" + login[0] + "\'";
        } else {
            query = "SELECT u.login, u.senha, u.nome, u.telefone, u.endereco, pt.descGeral, t.tipo FROM Usuario u" +
                    " INNER JOIN Prestador pt ON pt.login = u.login INNER JOIN Tipo t ON t.id = u.tipo WHERE u.login = " + "\'" + login[0] + "\'";
        }

        Usuario usuario = new Usuario();

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {

                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setEndereco(rs.getString("endereco"));

                if (rs.getString("tipo").equals("consumidor")) {
                    usuario.setNumCartao(rs.getString("numCartao"));
                } else {
                    usuario.setDescGeral(rs.getString("descGeral"));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;

    }

}
