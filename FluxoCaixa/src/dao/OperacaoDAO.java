package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import classes.Operacao;
import principal.ConnectionDB;

public class OperacaoDAO {

	public static void create_operacao(Operacao oper) throws SQLException{
		String sql;
		if(oper.isReceita())
			sql = "INSERT INTO RECEITA(categoria, descricao, valor, dia, mes, ano) VALUES (?, ?, ?, ?, ?, ?) ";
		else
			sql = "INSERT INTO DESPESA(categoria, descricao, valor, dia, mes, ano) VALUES (?, ?, ?, ?, ?, ?) ";


		PreparedStatement pstm;
		pstm = (PreparedStatement) ConnectionDB.conn.prepareStatement(sql);
		pstm.setString(1, oper.getCategoria());
		pstm.setString(2, oper.getDescricao());
		pstm.setDouble(3, oper.getValor());
		pstm.setInt(4, oper.getDia());
		pstm.setInt(5, oper.getMes());
		pstm.setInt(6, oper.getAno());
		pstm.execute();
	}

	public static Operacao get_operacao(boolean receita, int id) throws SQLException{
		//Delete by ID
		String sql;
		if(receita)
			sql = "SELECT * FROM receita WHERE id = ?";
		else 
			sql = "SELECT * FROM despesa WHERE id = ?";
		
		PreparedStatement pstm = null;
		pstm = ConnectionDB.conn.prepareStatement(sql);
		pstm.setInt(1, id);
		ResultSet rset = null;
		rset = pstm.executeQuery();
		rset.next();
		Operacao oper = new Operacao(rset.getInt("id"), rset.getString("descricao"), rset.getString("categoria"), rset.getDouble("valor"), rset.getInt("dia"), rset.getInt("mes"), rset.getInt("ano"), receita);
		return oper;
		
	}
	
	public static void update_operacao(boolean receita_anterior, Operacao oper) throws SQLException{

		String sql;
		if(oper.isReceita() != receita_anterior) {
			delete_operacao(receita_anterior, oper.getId());
			create_operacao(oper);
		}
		else {
			if(oper.isReceita())
				sql = "UPDATE receita SET categoria = ?, descricao = ?, valor = ?, dia = ?, mes = ?, ano = ? " + "WHERE id = ?";
			else
				sql = "UPDATE despesa SET categoria = ?, descricao = ?, valor = ?, dia = ?, mes = ?, ano = ? " + "WHERE id = ?";
			
			PreparedStatement pstm = null;
			//Criar a classe para executar a query
			pstm = ConnectionDB.conn.prepareStatement(sql);
			//Adicionar os valores para atualizar
			pstm.setString(1, oper.getCategoria());
			pstm.setString(2, oper.getDescricao());
			pstm.setDouble(3, oper.getValor());
			pstm.setInt(4, oper.getDia());
			pstm.setInt(5, oper.getMes());
			pstm.setInt(6, oper.getAno());
			pstm.setInt(7, oper.getId());
			//Executar a query
			pstm.execute();
		}
	}

	public static void delete_operacao(boolean receita, int id) throws SQLException{
		//Delete by ID
		String sql;
		if(receita)
			 sql = "DELETE FROM receita WHERE id = ?";
		else 
			sql = "DELETE FROM despesa WHERE id = ?";
		
		PreparedStatement pstm = null;
		pstm = ConnectionDB.conn.prepareStatement(sql);
		pstm.setInt(1, id);
		pstm.execute();
	}
	
	public static List<Operacao> get_operacoes(boolean receita, int mes, int ano) throws SQLException{
		String sql;
		if(receita)
			sql = "SELECT * FROM RECEITA";
		else
			sql = "SELECT * FROM DESPESA";
		if(ano != 0)
			sql += " WHERE ano = " + ano;
		if(mes != 0)
			sql += " and mes = " + mes;

		List<Operacao> Operacoes = new ArrayList<Operacao>();

		PreparedStatement pstm = null;
		
		//Classe que recupera os dados do banco
		ResultSet rset = null;
		pstm = ConnectionDB.conn.prepareStatement(sql);
		rset = pstm.executeQuery();
		while (rset.next()) {
			Operacao oper = new Operacao(rset.getInt("id"), rset.getString("descricao"), rset.getString("categoria"), rset.getDouble("valor"), rset.getInt("dia"), rset.getInt("mes"), rset.getInt("ano"), receita);
			Operacoes.add(oper);
		}
		return Operacoes;
	}
	
	public static List<Operacao> join_operacoes(List<Operacao> lista, List <Operacao> lista_aux){
		lista.addAll(lista_aux);
		Comparator<Operacao> myComparator = new Comparator<Operacao>() {
		    public int compare(Operacao o1, Operacao o2) {
		    	int result = 1;
		    	if(o1.getAno() == o2.getAno()) {
		    		if(o1.getMes() == o2.getMes()) {
		    			if(o1.getDia() == o2.getDia()) {
					    	if(o1.getId() < o2.getId()) result = -1;
					        return result;
			    		}
				    	if(o1.getDia() < o2.getDia()) result = -1;
				        return result;
		    		}
			    	if(o1.getMes() < o2.getMes()) result = -1;
			        return result;
		    	}
		    	if(o1.getAno() < o2.getAno()) result = -1;
		        return result;

		    }
		};

		
		Collections.sort(lista, myComparator);
		return lista;
	}	
	
	public static void popular_bd(boolean receita) throws SQLException{
		int cnt = 1;
		for(int k = 0; k <= 4; k++) {
				for(int i = 1; i <= 3; i++) {
					int j = 0;
					if(receita) j = 1;
					create_operacao(new Operacao(cnt, "Automatico", "Dado gerado automaticamente", (k*10 + i*j) + 0.05, cnt, 1, 2021, receita));
					cnt++;
				}
			}
	}
}
