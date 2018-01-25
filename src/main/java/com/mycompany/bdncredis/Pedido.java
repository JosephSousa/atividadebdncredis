package com.mycompany.bdncredis;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @brief Classe Pedido
 * @author Joseph Sousa
 * @mail jsantos.te@gmail.com
 * @date   24/01/2018
 */
public class Pedido {

    private int codigo;
    private List<ItemPedido> itens;
    private Date data;

    public Pedido(int codigo, Date data) {
        this.codigo = codigo;
        this.itens = new ArrayList();
        this.data = data;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public void adicionarItens(ItemPedido p){
        this.itens.add(p);
    }
    
    public List getItens(){
        return Collections.unmodifiableList(itens);
    }
    @Override
    public String toString() {
        return "Pedido{" + "codigo=" + codigo + ", itens=" + itens + ", data=" + data + '}';
    }
}
