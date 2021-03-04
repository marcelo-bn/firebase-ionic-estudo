import db.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMySQL {

    /**
     * Listando todos os logins de Consumidor e Prestador
     */
    public String verificaLogin(String n) {
        String nome = n;
        String user = "";
        String query = "SELECT login FROM Consumidor UNION SELECT login FROM Prestador;";

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            boolean aux = false;

            while(rs.next()) {
                String loginrs = rs.getString("login");
                if (loginrs.equals(nome)) {
                    aux = true;
                    user = loginrs;
                }
            }

            if (!aux) {
                user = "error";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
        Informações de determinado usuário
     */
    public String[] infoLogin(String login) {
        String query = "SELECT * FROM Consumidor WHERE login = " + "\'" + login + "\'";
        String[] usuario = new String[7];

        try (PreparedStatement stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                /*String login_rs = rs.getString("login");
                String senha_rs = rs.getString("senha");
                String tipo_rs = rs.getString("tipo");
                String nomeCliente_rs = rs.getString("nomeCliente");
                String telefoneCliente_rs = rs.getString("telefoneCliente");
                String enderecoCliente_rs = rs.getString("enderecoCliente");
                String numCartao_rs = rs.getString("numCartao");*/
                usuario[0] = rs.getString("login");
                usuario[1] = rs.getString("senha");
                usuario[2] = rs.getString("tipo");
                usuario[3] = rs.getString("nomeCliente");
                usuario[4] = rs.getString("telefoneCliente");
                usuario[5] = rs.getString("enderecoCliente");
                usuario[6] = rs.getString("numCartao");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;

    }

}
