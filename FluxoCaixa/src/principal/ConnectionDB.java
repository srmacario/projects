package principal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.OperacaoDAO;
import grafico.TelaAbertura;

import java.sql.Statement;

public class ConnectionDB {

	final static JPanel warning = new JPanel();
	private static final String DRIVER = "org.postgresql.Driver";
	
	public static Connection conn;
	
	public static void create_connection_to_database(String user, String password, String url) throws SQLException, ClassNotFoundException{
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(url, user, password);
	}
	
	public static void create_database(String database_name, String user) throws SQLException{
	    JOptionPane.showMessageDialog(warning, "Criando banco de dados, clique em OK para continuar e aguarde.", "Conexao",
		        JOptionPane.WARNING_MESSAGE);
		Statement stmt = conn.createStatement();
		// Cria banco de dados com o nome e o usuario fornecidos
		String sql = "CREATE DATABASE\n"
				+ database_name + "\n"
				+ "    WITH \n"
				+ "    OWNER = "
				+ user + "\n" 
				+ "    ENCODING = 'UTF8'\n"
				+ "    LC_COLLATE = 'pt_BR.UTF-8'\n"
				+ "    LC_CTYPE = 'pt_BR.UTF-8'\n"
				+ "    TABLESPACE = pg_default\n"
				+ "    CONNECTION LIMIT = -1"
				+ "    TEMPLATE template0;";
        stmt.executeUpdate(sql);
	}
	
	public static void create_tables() throws SQLException{
		String sql;
		Statement stmt = conn.createStatement();
		DatabaseMetaData dbm = conn.getMetaData();
		ResultSet rs = dbm.getTables(null, null, "receita", null);
		if(!rs.next()) {
	        //criar tabela RECEITA
	        sql = "create table RECEITA (\n"
	        		+ "\n"
	        		+ "id SERIAL UNIQUE,\n"
	        		+ "categoria varchar(50) not null,\n"
	        		+ "descricao varchar(280) not null,\n"
	        		+ "valor decimal(10, 2) not null,\n"
	        		+ "dia int not null,\n"
	        		+ "mes int not null,\n"
	        		+ "ano int not null,\n"
	        		+ "primary key (id));";
	        stmt.executeUpdate(sql);
			OperacaoDAO.popular_bd(true);
		}

		rs = dbm.getTables(null, null, "despesa", null);
		if(!rs.next()) {
	        //criar tabela DESPESA
	        sql = "create table DESPESA (\n"
	        		+ "\n"
	        		+ "id SERIAL UNIQUE,\n"
	        		+ "categoria varchar(50) not null,\n"
	        		+ "descricao varchar(280) not null,\n"
	        		+ "valor decimal(10, 2) not null,\n"
	        		+ "dia int not null,\n"
	        		+ "mes int not null,\n"
	        		+ "ano int not null,\n"
	        		+ "primary key (id));";
	        stmt.executeUpdate(sql);
			OperacaoDAO.popular_bd(false);
		}
	}
	
	public static boolean search_database(String database_name) throws SQLException{
		PreparedStatement ps = conn.prepareStatement("SELECT datname FROM pg_database WHERE datistemplate = false;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
        	String db_search = rs.getString(1);
        	if(database_name.equals(db_search)){
		        return true;
			}
        }
        return false;
	}
	
	public static void iniciarDB(String url, String user, String password, String database_name) throws SQLException, ClassNotFoundException{
		//Inicializa conexao do postgres utilizando url pre definido, caso deseje digitar, trocar a funcao
		create_connection_to_database(user, password, url);
		if(conn != null) {
			//Busca se banco de dados existe
			database_name = database_name.toLowerCase();
			if(search_database(database_name) == false) {
				create_database(database_name, user);
			}
			
			//Reinicia a conexao com o banco de dados 
			conn.close();
			create_connection_to_database(user, password, url + database_name);
			create_tables();
			if(conn != null) {
				new TelaAbertura();
			}
		}
	} 
}