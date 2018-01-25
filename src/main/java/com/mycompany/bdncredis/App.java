package com.mycompany.bdncredis;

import com.google.gson.Gson;
import com.mycompany.jdbc.ItemPedidoJDBC;
import com.mycompany.jdbc.PedidoJDBC;
import com.mycompany.jdbc.ProdutoJDBC;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.clients.jedis.Jedis;

/**
 * @brief Classe App
 * @author Joseph Sousa
 * @mail jsantos.te@gmail.com
 * @date   22/01/2018
 */
public class App {

    public static void main(String[] args) {
        
        Gson gson= new Gson();
        Produto p= new Produto(1,"coca", 2);
        String resultado=gson.toJson(p);
        Produto p2=gson.fromJson(resultado,Produto.class);
        Jedis jedis=new Jedis("127.0.0.1", 6379);
        ItemPedido ip=new ItemPedido(p2, 2, 2, 2);
        Pedido pedido= new Pedido(2, new Date(12/12/1994));
        pedido.adicionarItens(ip);
        ip=new ItemPedido(p, 1, 1, 2);
        pedido.adicionarItens(ip);
        ProdutoJDBC prodDao= new ProdutoJDBC();
        prodDao.salvar(p2);
        PedidoJDBC pedidoDado=new PedidoJDBC();
        pedidoDado.salvar(pedido);
        ItemPedidoJDBC ipDao= new ItemPedidoJDBC();
        ipDao.salvar(ip);
        String pedidoString= gson.toJson(pedido);
        jedis.setex(""+pedido.getCodigo(), 300, pedidoString);
        
        try {
            System.out.println(pedidoDado.consultarPedido("2"));
        } catch (SQLException ex) {
            System.out.println("não pegou" + ex);
        }
        
    }
}
