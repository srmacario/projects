package grafico;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class LinhaMensal extends JPanel implements ActionListener{
	
	int ID = 0;
	String data = "01/01/2021";
	String categoria = "Outros";
	String descricao = "N/A";
	double valor = 1.00;
	
	JLabel labelData, labelCat, labelDesc, labelValor;
	JPanel localBotao;
	JButton botao = new JButton("Editar");
	TelaMensal telaMae;
	
	Color verde = new Color(0x01b88a);
	Color vermelho = new Color(0xe86b67);
	Color azul = new Color(0x5BC0EB);
	Color amarelo = new Color(0xF4D35E);
	
	LinhaMensal(int index, String dat, String cat, String des, double val, TelaMensal tela) {
		telaMae = tela;
		ID = index;
		data = dat;
		categoria = cat;
		descricao = des;
		valor = val;
		
		Border borda = BorderFactory.createLineBorder(new Color(0xe5e5e5), 1);
		this.setBorder(borda);
		this.setBackground(Color.white);
		this.setLayout(null);
		setBounds(0, 0, 1280, 70);
		labelData = new JLabel(data);
		labelCat = new JLabel(categoria);
		labelDesc = new JLabel(descricao);
		labelValor = new JLabel("R$ " + String.format("%.2f", valor));
		
		labelData.setBounds(0, 0, 250, 70);
		labelData.setHorizontalAlignment(JLabel.CENTER);
		labelData.setFont(new Font("Arial", Font.PLAIN, 15));
		labelData.setForeground(Color.black);
		labelData.setBackground(Color.green);
		
		labelCat.setBounds(250, 0, 300, 70);
		labelCat.setFont(new Font("Arial", Font.PLAIN, 15));
		labelCat.setForeground(Color.gray);
		labelCat.setBackground(Color.red);
		
		labelDesc.setBounds(550, 0, 400, 70);
		labelDesc.setFont(new Font("Arial", Font.PLAIN, 15));
		labelDesc.setForeground(Color.gray);
		labelDesc.setBackground(Color.blue);
		
		labelValor.setBounds(950, 0, 330, 70);
		labelValor.setFont(new Font("Arial", Font.BOLD, 15));
		labelValor.setForeground(verde);
		if(valor < 0)
			labelValor.setForeground(vermelho);
		labelValor.setHorizontalAlignment(JLabel.CENTER);
		labelValor.setBackground(Color.gray);
		
		localBotao = new JPanel();
		localBotao.setBounds(0, 15, 80, 40);
		localBotao.setOpaque(false);
		botao.setFocusable(false);
		botao.addActionListener(this);
		botao.setBackground(amarelo);
		botao.setForeground(Color.white);
		botao.setFont(new Font("Arial", Font.BOLD, 15));
		localBotao.add(botao);
		
		this.add(localBotao);
		this.add(labelData);
		this.add(labelCat);
		this.add(labelDesc);
		this.add(labelValor);
		
	}
	
	public double getValor() {
		return valor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new TelaEdicao(ID, valor, telaMae);
		
	}
}

