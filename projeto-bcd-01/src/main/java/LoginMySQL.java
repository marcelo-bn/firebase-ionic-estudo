import db.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMySQL {

    /**
     * Listando todos os logins de Consumidor e Prestador
     */
    public String[] verificaLogin(String n) {
        String nome = n;
        String[] user = new String[2];
        String tipo = "";
        String query = "(SELECT login, tipo FROM Consumidor) UNION (SELECT login, tipo FROM Prestador)";

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
        Informações de determinado usuário
     */
    public Usuario infoLogin(String[] login) {
        String query = "";

        if (login[1].equals("consumidor")) {
            query = "SELECT * FROM Consumidor WHERE login = " + "\'" + login[0] + "\'";
        } else {
            query = "SELECT * FROM Prestador WHERE login = " + "\'" + login[0] + "\'";
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
