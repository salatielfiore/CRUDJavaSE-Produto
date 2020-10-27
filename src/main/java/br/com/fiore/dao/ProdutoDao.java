package br.com.fiore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.fiore.data.ConnectionFactory;
import br.com.fiore.model.Produto;

public class ProdutoDao {
	
	public static final String TABLE_NAME = "table_produto";
	public static final String CODIGO = "codigo";
	public static final String PRODUTO = "produto";
	public static final String PRECO = "preco";
	public static final String QUANTIDADE = "quantidade";
	public static final String SUBTOTAL = "subtotal";

	private Connection conn;
	private PreparedStatement statement;
	private Statement st;
	private ResultSet rs;
	private StringBuilder sql;
	private List<Produto> produtos = new ArrayList<>();

	public ProdutoDao() throws Exception {
		conn = ConnectionFactory.getConexao();
	}

	public void save(Produto produto) throws SQLException {

		sql = new StringBuilder();
		sql.append("INSERT INTO ")
				.append(TABLE_NAME)
				.append("(produto,preco,quantidade,subtotal) ")
				.append("VALUES(")
				.append("?,?,?,?)");
		
		try {
			
			statement = conn.prepareStatement(String.valueOf(sql));
			//
			statement.setString(1, produto.getProduto());
			statement.setDouble(2, produto.getPreco());
			statement.setInt(3, produto.getQuantidade());
			statement.setDouble(4, produto.getSubTotal());
			//
			statement.execute();
			
		} catch (SQLException e) {
			
			throw new SQLException("Não foi possivel cadastrar o produto");
		} finally {
			
			statement.close();
			conn.close();	
		}
		
	}
	
	public void update(Produto produto) throws SQLException {
			
		sql = new StringBuilder();
		sql.append("UPDATE ")
				.append(TABLE_NAME)
				.append(" SET ")
				.append("produto = ?,preco = ?,")
				.append("quantidade = ?,subtotal = ? ")
				.append("WHERE codigo = ?");
		
		try {
			
			statement = conn.prepareStatement(String.valueOf(sql));
			//
			statement.setString(1, produto.getProduto());
			statement.setDouble(2, produto.getPreco());
			statement.setInt(3, produto.getQuantidade());
			statement.setDouble(4, produto.getSubTotal());
			statement.setInt(5, produto.getCodigo());
			//
			statement.execute();
			
		} catch (SQLException e) {
			
			throw new SQLException("Não foi possivel alterar o produto");
		} finally {
			
			statement.close();
			conn.close();
		}
		
	}
	
	public void delete(Integer codigo) throws SQLException {
		
		sql = new StringBuilder();
		sql.append("DELETE FROM ")
				.append(TABLE_NAME)
				.append(" WHERE ")
				.append("codigo = ")
				.append(codigo);
		
		try {
			
			st = conn.createStatement();
			st.execute(String.valueOf(sql));
			
			
		} catch (SQLException e) {
			
			throw new SQLException("Não foi possivel deletar o produto");
		} finally {
			
			st.close();
			conn.close();
		}
		
	}
	
	public List<Produto> findAll() throws SQLException{
		
		sql = new StringBuilder();
		sql.append("SELECT * FROM ")
				.append(TABLE_NAME);
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(String.valueOf(sql));
			
			while(rs.next()) {
				
				Produto produto = new Produto();
				produto.setCodigo(rs.getInt(CODIGO));
				produto.setProduto(rs.getString(PRODUTO));
				produto.setPreco(rs.getDouble(PRECO));
				produto.setQuantidade(rs.getInt(QUANTIDADE));
				produto.setSubTotal(rs.getDouble(SUBTOTAL));
				//
				produtos.add(produto);
				
			}
			
		} catch (SQLException e) {
			
			throw new SQLException("erro ao listar todos os produto");
		} finally {
			
			st.close();
			rs.close();
			conn.close();
		}
		
		
		return produtos;
	}
	
public Produto findById(Integer codigo) throws SQLException{
		
		Produto produto = null;
		
		sql = new StringBuilder();
		sql.append("SELECT * FROM ")
				.append(TABLE_NAME)
				.append(" WHERE ")
				.append(CODIGO)
				.append(" = ")
				.append(codigo);
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(String.valueOf(sql));
			
			if(rs.next()) {
				
				produto = new Produto();
				produto.setCodigo(rs.getInt(CODIGO));
				produto.setProduto(rs.getString(PRODUTO));
				produto.setPreco(rs.getDouble(PRECO));
				produto.setQuantidade(rs.getInt(QUANTIDADE));
				produto.setSubTotal(rs.getDouble(SUBTOTAL));
				
			}
			
		} catch (SQLException e) {
			
			throw new SQLException("erro ao listar produto " );
		}
		
		
		return produto;
	}

}
