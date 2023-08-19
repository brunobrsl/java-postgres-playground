package com.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.example.model.Produto;
import com.example.model.Marca;

public class ProdutoDAO extends DAO {
    public ProdutoDAO(Connection conn) {
        super(conn);
    }

    public void inserir(Produto produto) {
        var sql = "insert into produto (nome, marca_id, valor) values (?, ?, ?)";
        try {
            var statement = conn.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setLong(2, produto.getMarca().getId());
            statement.setDouble(3, produto.getValor());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro na execução da consulta: " + e.getMessage());
        }
    }

    public List<Produto> listar() throws SQLException {
        var lista = new LinkedList<Produto>();
        var statement = conn.createStatement();
        var result = statement.executeQuery("select * from produto");

        while(result.next()){
            var produto = new Produto();
            var marca = new Marca();

            produto.setId(result.getLong("id"));
            produto.setNome(result.getString("nome"));
            produto.setMarca(marca);
            produto.setValor(result.getDouble("valor"));
            lista.add(produto);
        }

        return lista;
    }

    public void alterar(Produto produto) {
        var sql = "update produto set nome = ?, marca_id = ?, valor = ? where id = ?";
        try {
            var statement = conn.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setLong(2, produto.getMarca().getId());
            statement.setDouble(3, produto.getValor());
            statement.setLong(4, produto.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro na alteração do produto: " + e.getMessage());
        }
    }

    public void excluir(long id) { // long minusculo não aceita null
        var sql = "delete from produto where id = ?";
        try {
            var statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            if(statement.executeUpdate() == 1)
                System.out.println("Produto excluído com sucesso");
            else System.out.println("Produto não localizado");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir o produto: " + e.getMessage());
        }
    }
}
