package classes;

public class Operacao {
	
	private int id;
	private String descricao;
	private String categoria;
	private double valor;
	private int dia;
	private int mes;
	private int ano;
	private boolean receita;
	
	public Operacao() {}
	
	public Operacao(int new_id, String new_descricao, String new_categoria, double new_valor, int new_dia, int new_mes, int new_ano, boolean new_receita) {
		this.id = new_id;
		this.descricao = new_descricao;
		this.categoria = new_categoria;
		this.valor = new_valor;
		this.dia = new_dia;
		this.mes = new_mes;
		this.ano = new_ano;
		this.receita = new_receita;
		if(this.receita == false && this.valor > 0) this.valor *= -1;
	}
	
	public boolean isReceita() {
		return receita;
	}
	
	public void setReceita(boolean receita) {
		this.receita = receita;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
		if(this.receita == false && this.valor > 0) this.valor *= -1;
	}
	
	public int getDia() {
		return dia;
	}
	
	public void setDia(int dia) {
		this.dia = dia;
	}
	
	public int getMes() {
		return mes;
	}
	
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
}
