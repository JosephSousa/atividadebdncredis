package com.mycompany.bdncredis;

/**
 * @brief Classe ItemPedido
 * @author Joseph Sousa
 * @mail jsantos.te@gmail.com
 * @date   24/01/2018
 */
public class ItemPedido {

    private Produto produto;
    private int quantidade;
    private int id;
    private int idPedido;

    public ItemPedido(Produto produto, int quantidade, int id, int idPedido) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.id = id;
        this.idPedido = idPedido;
    }
    
     public ItemPedido(int quantidade, int id, int idPedido) {
        this.quantidade = quantidade;
        this.id = id;
        this.idPedido = idPedido;
    }
    
    
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ItemPedido{" + "produto=" + produto + ", quantidade=" + quantidade + ", id=" + id + ", idPedido=" + idPedido + '}';
    }
}
