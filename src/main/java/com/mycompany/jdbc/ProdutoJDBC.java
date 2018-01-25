package com.mycompany.jdbc;

import com.mycompany.bdncredis.Produto;
import com.mycompany.conection.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Sousa
 * @mail Jsantos.te@gmail.com
 * @since 01/08/2017 , 21:01:00
 */
public class ProdutoJDBC {

    private final ConexaoBD conex = new ConexaoBD();

    private final Connection connection;

    public ProdutoJDBC() {
        connection = conex.getConnection();
    }

    public boolean salvar(Produto produto) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT into produto(descricao,preco)VALUES (?,?)");
            prepareStatement.setString(1, produto.getDescricao());
            prepareStatement.setDouble(2, produto.getPreco());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean remover(Produto produto) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM produto WHERE codigo=?");
            prepareStatement.setInt(1, produto.getCodigo());
            prepareStatement.execute();
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean atualizar(Produto produto) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE produto set descricao=?, preco=? WHERE codigo=?");
            prepareStatement.setString(1, produto.getDescricao());
            prepareStatement.setDouble(2, produto.getPreco());
            prepareStatement.setInt(3, produto.getCodigo());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Produto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Produto> todosOsProdutos() {
        try {
            List<Produto> produtos = new ArrayList<>();

            ResultSet result = consultarTodosOsProdutos();

            while (result.next()) {
                produtos.add(criarProduto(result));
            }
            return produtos;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private ResultSet consultarTodosOsProdutos() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("Select * from produto");
        ResultSet result = prepareStatement.executeQuery();
        return result;
    }

    private Produto criarProduto(ResultSet result) throws SQLException {
        int codigo = result.getInt("codigo");
        String nome = result.getString("descricao");
        float preco = result.getFloat("preco");
        return new Produto(codigo, nome, preco);
    }

    public Produto buscaProduto(Produto produto, javax.swing.JTable jTableProduto) throws SQLException {
        produto.setCodigo(Integer.parseInt((String) jTableProduto.getValueAt(jTableProduto.getSelectedRow(), 0)));
        PreparedStatement prepareStatement = connection.prepareStatement("Select * from produto where codigo='" + produto.getCodigo() + "'", ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet result = prepareStatement.executeQuery();
        try {
            result.first();
            produto.setDescricao(result.getString("descricao"));
            produto.setPreco(result.getFloat("preco"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erro ao selecionar item" + ex);
        }
        return produto;
    }
}
