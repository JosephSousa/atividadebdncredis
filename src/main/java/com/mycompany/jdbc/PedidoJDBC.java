package com.mycompany.jdbc;

import com.google.gson.Gson;
import com.mycompany.bdncredis.Pedido;
import com.mycompany.bdncredis.Produto;
import com.mycompany.conection.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Joseph Sousa
 * @mail Jsantos.te@gmail.com
 * @since 01/08/2017 , 21:01:00
 */
public class PedidoJDBC {

    private final ConexaoBD conex = new ConexaoBD();

    private final Connection connection;

    public PedidoJDBC() {
        connection = conex.getConnection();
    }

    public boolean salvar(Pedido pedido) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT into pedido(id,data)VALUES (?,?)");
            prepareStatement.setInt(1, pedido.getCodigo());
            prepareStatement.setDate(2, pedido.getData());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean remover(Pedido pedido) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM pedido WHERE id=?");
            prepareStatement.setInt(1, pedido.getCodigo());
            prepareStatement.execute();
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar(Pedido pedido) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE pedido set data=? WHERE id=?");
            prepareStatement.setDate(1, pedido.getData());
            prepareStatement.setInt(2, pedido.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Pedido> todosOsProdutos() {
        try {
            List<Pedido> pedidos = new ArrayList<>();

            ResultSet result = consultarTodosOsPedidos();

            while (result.next()) {
                pedidos.add(criarPedido(result));
            }
            return pedidos;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private ResultSet consultarTodosOsPedidos() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("Select * from pedido");
        ResultSet result = prepareStatement.executeQuery();
        return result;
    }

    public Pedido consultarPedido(String id) throws SQLException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        if (!jedis.exists(id)) {
            PreparedStatement prepareStatement = connection.prepareStatement("Select data,id from pedido where id=" + id);
            ResultSet result = prepareStatement.executeQuery();
            if(result.next()){
              return criarPedido(result); 
            }
           return null;
        }
        Gson gson = new Gson();
        Pedido b = gson.fromJson(jedis.get(id), Pedido.class);
        return b;
    }

    private Pedido criarPedido(ResultSet result) throws SQLException {
        int codigo = result.getInt("id");
        Date data = result.getDate("data");
        return new Pedido(codigo, data);
    }
}
