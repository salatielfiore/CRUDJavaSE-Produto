package br.com.fiore.data;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	//metodo responsavel por estabelecer a conexão com o banco
    public static Connection getConexao() throws Exception {

    	Connection conn = null;
    	
        // Armazenando informações referente ao banco de dados
        String url = "jdbc:mysql://localhost:3306/produtodb";
        String user = "root";
        String pwd = "";

        // Estabelecendo a conexão com o banco
        try {
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
           throw new Exception("Não foi possivel conectar com o banco de dados");
        }
 
        return conn;
    }
	
}
