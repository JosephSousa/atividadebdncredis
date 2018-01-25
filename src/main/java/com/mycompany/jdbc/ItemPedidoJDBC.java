package com.mycompany.jdbc;

import com.mycompany.bdncredis.ItemPedido;
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

/**
 *
 * @author Joseph Sousa
 * @mail Jsantos.te@gmail.com
 * @since 01/08/2017 , 21:01:00
 */
public class ItemPedidoJDBC {

    private final ConexaoBD conex = new ConexaoBD();

    private final Connection connection;

    public ItemPedidoJDBC() {
        connection = conex.getConnection();
    }

    public boolean salvar(ItemPedido itemPedido) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT into itempedido(id,quantidade,idpedido)VALUES (?,?,?)");
            prepareStatement.setInt(1, itemPedido.getId());
            prepareStatement.setInt(2, itemPedido.getQuantidade());
            prepareStatement.setInt(3, itemPedido.getIdPedido());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean remover(ItemPedido itemPedido) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM pedido WHERE id=?");
            prepareStatement.setInt(1, itemPedido.getId());
            prepareStatement.execute();
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar(ItemPedido itemPedido) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE itempedido set quantidade=?,idpedido=? WHERE id=?");
            prepareStatement.setInt(1, itemPedido.getQuantidade());
            prepareStatement.setInt(2, itemPedido.getIdPedido());
            prepareStatement.setInt(3, itemPedido.getId());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<ItemPedido> todosOsItens() {
        try {
            List<ItemPedido> itens = new ArrayList<>();

            ResultSet result = consultarTodosOsItens();

            while (result.next()) {
                itens.add(criarItem(result));
            }
            return itens;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private ResultSet consultarTodosOsItens() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("Select * from itempedido");
        ResultSet result = prepareStatement.executeQuery();
        return result;
    }

    private ItemPedido criarItem(ResultSet result) throws SQLException {
        int codigo = result.getInt("id");
        int qtde = result.getInt("quantidade");
        int idPedido = result.getInt("idpedido");
        return new ItemPedido(qtde, codigo, idPedido);
    }
}
