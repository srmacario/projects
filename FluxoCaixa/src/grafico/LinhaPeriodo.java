package grafico;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class LinhaPeriodo extends JPanel {
	
	String[] meses = {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
	
	String data = "janeiro/2021";
	double saldoInicial = 0.00;
	double receitas = 0.00;
	double debitos = 0.00;
	
	JLabel labelData, labelSaI, labelRec, labelDeb, labelSaF;
	
	Color verde = new Color(0x01b88a);
	Color vermelho = new Color(0xe86b67);
	Color azul = new Color(0x5BC0EB);
	
	LinhaPeriodo(int mes, int ano, double sal, double rec, double deb) {
		data = meses[mes-1] + "/" + ano;
		saldoInicial = sal;
		receitas = rec;
		debitos = deb;
		
		Border borda = BorderFactory.createLineBorder(new Color(0xe5e5e5), 1);
		this.setBorder(borda);
		this.setBackground(Color.white);
		this.setLayout(null);
		setBounds(0, 0, 1280, 70);
		labelData = new JLabel(data);
		labelSaI = new JLabel("R$ " + String.format("%.2f", saldoInicial) + "  ");
		labelRec = new JLabel("R$ " + String.format("%.2f", receitas) + "  ");
		labelDeb = new JLabel("R$ " + String.format("%.2f", debitos) + "  ");
		labelSaF = new JLabel("R$ " + String.format("%.2f", (saldoInicial + receitas + debitos)) + "  ");
		
		labelData.setBounds(0, 0, 250, 70);
		labelData.setFont(new Font("Arial", Font.BOLD, 15));
		labelData.setHorizontalAlignment(JLabel.CENTER);
		labelData.setForeground(Color.black);
		labelData.setBackground(Color.green);
		
		labelSaI.setBounds(250, 0, 258, 70);
		labelSaI.setFont(new Font("Arial", Font.BOLD, 15));
		labelSaI.setHorizontalAlignment(JLabel.RIGHT);
		labelSaI.setForeground(verde);
		if(saldoInicial<0)
			labelSaI.setForeground(vermelho);
		labelSaI.setBackground(Color.red);
		
		labelRec.setBounds(508, 0, 258, 70);
		labelRec.setFont(new Font("Arial", Font.BOLD, 15));
		labelRec.setHorizontalAlignment(JLabel.RIGHT);
		labelRec.setForeground(verde);
		labelRec.setBackground(Color.blue);
		
		labelDeb.setBounds(766, 0, 258, 70);
		labelDeb.setFont(new Font("Arial", Font.BOLD, 15));
		labelDeb.setForeground(vermelho);
		labelDeb.setHorizontalAlignment(JLabel.RIGHT);
		labelDeb.setBackground(Color.gray);
		
		labelSaF.setBounds(1024, 0, 220, 70);
		labelSaF.setFont(new Font("Arial", Font.BOLD, 15));
		labelSaF.setHorizontalAlignment(JLabel.RIGHT);
		labelSaF.setForeground(verde);
		if((saldoInicial + receitas + debitos)<0)
			labelSaF.setForeground(vermelho);
		labelSaF.setBackground(Color.red);
		
		this.add(labelData);
		this.add(labelSaI);
		this.add(labelRec);
		this.add(labelDeb);
		this.add(labelSaF);
		
	}
	
	public double getSaldoFinal() {
		return (saldoInicial + receitas + debitos);
	}
	public double getReceita() {
		return receitas;
	}
	public double getDebito() {
		return debitos;
	}
}

