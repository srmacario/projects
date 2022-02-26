package grafico;

import dao.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import classes.Operacao;

public class TelaPeriodo implements ActionListener{
	JFrame frame = new JFrame();
	JFrame frameAntigo;
	JPanel panel = new JPanel();
	JPanel periodo = new JPanel();
	JPanel saldoFinal = new JPanel();
	JPanel receitaFinal = new JPanel();
	JPanel debitoFinal = new JPanel();
	JButton botaoVoltar = new JButton("Voltar");
	JPanel localBotao = new JPanel();
	final static JPanel warning = new JPanel();
	
	Color verde = new Color(0x01b88a);
	Color vermelho = new Color(0xe86b67);
	Color azul = new Color(0x5BC0EB);
	Color amarelo = new Color(0xF4D35E);
	
	int nLinhas = 0;
	double saldo = 0;
	double receita = 0;
	double debito = 0;

	
	Border borda = BorderFactory.createLineBorder(new Color(0xe5e5e5), 1);
	
	TelaPeriodo(JFrame fr) {
		frameAntigo = fr;
		frame.setTitle("Saldo por Periodo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		
		localBotao.setBounds(10, 5, 100, 40);
		localBotao.setOpaque(false);
		botaoVoltar.setFocusable(false);
		botaoVoltar.addActionListener(this);
		botaoVoltar.setBackground(amarelo);
		botaoVoltar.setForeground(Color.white);
		botaoVoltar.setFont(new Font("Arial", Font.BOLD, 15));
		localBotao.add(botaoVoltar);
		frame.add(localBotao);
		
		periodo.setBounds(0, 0, 1280, 50);
		periodo.setLayout(new FlowLayout());
		periodo.setBackground(Color.white);
		frame.add(periodo);
		
		JLabel labelData = new JLabel("Data");
		JLabel labelSaI = new JLabel("  Saldo Inicial");
		JLabel labelRec = new JLabel("  Receitas");
		JLabel labelDeb = new JLabel("  Debitos");
		JLabel labelSaF = new JLabel("  Saldo Final");
		
		labelData.setBounds(0, 50, 250, 50);
		labelData.setBorder(borda);
		labelData.setHorizontalAlignment(JLabel.CENTER);
		labelData.setFont(new Font("Arial", Font.BOLD, 15));
		labelData.setForeground(Color.DARK_GRAY);
		
		labelSaI.setBounds(250, 50, 258, 50);
		labelSaI.setBorder(borda);
		labelSaI.setFont(new Font("Arial", Font.BOLD, 15));
		labelSaI.setForeground(Color.DARK_GRAY);
		
		labelRec.setBounds(508, 50, 258, 50);
		labelRec.setBorder(borda);
		labelRec.setFont(new Font("Arial", Font.BOLD, 15));
		labelRec.setForeground(Color.DARK_GRAY);
		
		labelDeb.setBounds(766, 50, 258, 50);
		labelDeb.setBorder(borda);
		labelDeb.setFont(new Font("Arial", Font.BOLD, 15));
		labelDeb.setForeground(Color.DARK_GRAY);
		
		labelSaF.setBounds(1024, 50, 258, 50);
		labelSaF.setBorder(borda);
		labelSaF.setFont(new Font("Arial", Font.BOLD, 15));
		labelSaF.setForeground(Color.DARK_GRAY);
		
		frame.add(labelData);
		frame.add(labelSaI);
		frame.add(labelRec);
		frame.add(labelDeb);
		frame.add(labelSaF);
		
		panel.setPreferredSize(new Dimension(1280, nLinhas*71));
		panel.setBackground(Color.white);
		panel.setOpaque(true);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollFrame = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFrame.setBounds(0, 100, 1265, 430);
		
		saldoFinal.setBounds(0, 530, 1280, 50);
		saldoFinal.setLayout(null);
		saldoFinal.setBackground(Color.white);
		saldoFinal.setBorder(borda);
		frame.add(saldoFinal);
		
		receitaFinal.setBounds(0, 580, 1280, 50);
		receitaFinal.setLayout(null);
		receitaFinal.setBackground(Color.white);
		receitaFinal.setBorder(borda);
		frame.add(receitaFinal);
		
		debitoFinal.setBounds(0, 630, 1280, 50);
		debitoFinal.setLayout(null);
		debitoFinal.setBackground(Color.white);
		debitoFinal.setBorder(borda);
		frame.add(debitoFinal);
		
		calcular();
		
		frame.add(scrollFrame);
		frame.setVisible(true);
		try {
			popularLista();
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(warning, "Ocorreu um erro ao recuperar informacao do banco de dados", "Aviso",
			        JOptionPane.WARNING_MESSAGE);
		}
	}

	private void calcular() {
		JLabel sV = new JLabel("R$ " + String.format("%.2f", saldo));
		JLabel cV = new JLabel("R$ " + String.format("%.2f", receita));
		JLabel dV = new JLabel("R$ " + String.format("%.2f", debito));
		
		saldoFinal.removeAll();
		receitaFinal.removeAll();
		debitoFinal.removeAll();
		
		JLabel sD = new JLabel("Saldo Total:  ");
		sD.setBounds(550, 0, 400, 50);
		sD.setHorizontalAlignment(JLabel.RIGHT);
		sD.setFont(new Font("Arial", Font.BOLD, 20));
		sD.setForeground(azul);
		sV.setBounds(950, 0, 315, 50);
		sV.setHorizontalAlignment(JLabel.CENTER);
		sV.setBackground(verde);
		if(saldo < 0)
			sV.setBackground(vermelho);
		sV.setOpaque(true);
		sV.setFont(new Font("Arial", Font.BOLD, 20));
		sV.setForeground(Color.white);
		saldoFinal.add(sD);
		saldoFinal.add(sV);
		
		JLabel cD = new JLabel("Receita Total:  ");
		cD.setBounds(550, 0, 400, 50);
		cD.setHorizontalAlignment(JLabel.RIGHT);
		cD.setFont(new Font("Arial", Font.BOLD, 20));
		cD.setForeground(azul);
		cV.setBounds(950, 0, 315, 50);
		cV.setHorizontalAlignment(JLabel.CENTER);
		cV.setFont(new Font("Arial", Font.BOLD, 20));
		cV.setForeground(verde);
		receitaFinal.add(cD);
		receitaFinal.add(cV);
		
		JLabel dD = new JLabel("Debito Total:  ");
		dD.setBounds(550, 0, 400, 50);
		dD.setHorizontalAlignment(JLabel.RIGHT);
		dD.setFont(new Font("Arial", Font.BOLD, 20));
		dD.setForeground(azul);
		dV.setBounds(950, 0, 315, 50);
		dV.setHorizontalAlignment(JLabel.CENTER);
		dV.setFont(new Font("Arial", Font.BOLD, 20));
		dV.setForeground(vermelho);
		debitoFinal.add(dD);
		debitoFinal.add(dV);
		
		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	public void inserir(LinhaPeriodo linha) {
		nLinhas++;
		panel.setPreferredSize(new Dimension(1280, nLinhas*71));
		panel.add(linha);
		saldo += linha.getReceita() + linha.getDebito();
		receita += linha.getReceita();
		debito += linha.getDebito();
		calcular();
	}
	
	public void limparLista() {
		nLinhas = 0;
		saldo = 0;
		receita = 0;
		debito = 0;
		panel.removeAll();
		panel.setPreferredSize(new Dimension(1280, nLinhas*71));
		calcular();
	}
	
	public void popularLista() throws Exception{
		List <Operacao> receitas = OperacaoDAO.get_operacoes(true, 0, 0);
		List <Operacao> despesas = OperacaoDAO.get_operacoes(false, 0, 0);
		List <Operacao> total = OperacaoDAO.join_operacoes(receitas, despesas);
		double saldo_inicial = 0, saldo_receitas = 0, saldo_despesas = 0;
		int ultimo_mes = 0, ultimo_ano = 0;
		ListIterator<Operacao> it = total.listIterator();
		while(it.hasNext()) {
			Operacao opx = it.next();
			if(opx.getMes() != ultimo_mes && ultimo_mes != 0) {
				LinhaPeriodo linha = new LinhaPeriodo(ultimo_mes, ultimo_ano, saldo_inicial, saldo_receitas, saldo_despesas);
				saldo_inicial += saldo_receitas + saldo_despesas;
				saldo_receitas = saldo_despesas = 0;
				inserir(linha);
			}
			if(opx.isReceita()) saldo_receitas += opx.getValor();
			else saldo_despesas += opx.getValor();
			ultimo_mes = opx.getMes();
			ultimo_ano = opx.getAno();
		}
		LinhaPeriodo linha = new LinhaPeriodo(ultimo_mes, ultimo_ano, saldo_inicial, saldo_receitas, saldo_despesas);
		inserir(linha);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botaoVoltar) {
			frame.dispose();
			frameAntigo.setVisible(true);
		}
	}
}
