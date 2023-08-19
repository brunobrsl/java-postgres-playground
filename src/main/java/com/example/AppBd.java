package com.example;

import java.sql.SQLException;

import com.example.dao.ConnectionManager;
import com.example.dao.DAO;
import com.example.dao.EstadoDAO;
import com.example.dao.ProdutoDAO;
import com.example.model.Marca;
import com.example.model.Produto;

public class AppBd {
    public static void main(String[] args) {
        new AppBd();
    }

    public AppBd(){
        try(var conn = ConnectionManager.getConnection()){
            var estadoDAO = new EstadoDAO(conn);
            var listaEstados = estadoDAO.listar();
            
            for (var estado : listaEstados) {
                System.out.println(estado);
            }

            estadoDAO.localizar("PR");

            var marca = new Marca();
            marca.setId(2L);

            var produto = new Produto();
            produto.setId(206L);
            produto.setValor(90);
            produto.setMarca(marca);
            produto.setNome("Produto novo");

            var produtoDAO = new ProdutoDAO(conn);
            //inserir(produto);
            //produtoDAO.alterar(produto);
            //produtoDAO.excluir(207L);
            var listaProdutos = produtoDAO.listar();

            for (var produto2 : listaProdutos) {
                System.out.println(produto2);
            }

            var dao = new DAO(conn);
            //dao.listar("produto");

        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }        
    }
}