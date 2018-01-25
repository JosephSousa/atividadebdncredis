package com.mycompany.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Joseph Sousa
 * @mail Jsantos.te@gmail.com
 * @since 19/06/2017 , 20:10:35
 */
public class ConexaoBD {
    private Connection conn;

    public void criarConexao() {
        try {
            String url = "jdbc:postgresql://localhost:5432/atividadebdnc";
            String user = "joseph";
            String password = "123";
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException |ClassNotFoundException  e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        criarConexao();
        return this.conn;
    }

    public void desconecta() {
        try {
            conn.close();
        } catch (SQLException ex) {
        }
}
}
