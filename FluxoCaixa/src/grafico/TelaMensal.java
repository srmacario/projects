package grafico;

import dao.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import classes.Operacao;

public class TelaMensal implements ActionListener{
	JFrame frame = new JFrame();
	JFrame frameAntigo;
	JButton botao = new JButton("Atualizar");
	JButton botaoAdd = new JButton("+");
	JButton botaoVoltar = new JButton("Voltar");
	JPanel localVoltar = new JPanel();
	JPanel localBotao = new JPanel();
	JPanel panel = new JPanel();
	JPanel periodo = new JPanel();
	JPanel saldoFinal = new JPanel();
	JPanel creditoFinal = new JPanel();
	JPanel debitoFinal = new JPanel();
	String[] meses = {"jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"};
	Integer[] anos = {2021, 2020, 2019, 2018, 2017, 2016, 2015};
	JComboBox<String> selectMes = new JComboBox<>(meses);
	JComboBox<Integer> selectAno = new JComboBox<>(anos);
	
	Color verde = new Color(0x01b88a);
	Color vermelho = new Color(0xe86b67);
	Color azul = new Color(0x5BC0EB);
	Color amarelo = new Color(0xF4D35E);
	
	int nLinhas = 0;
	int mesPesquisa = 1;
	int anoPesquisa = 2021;
	double saldo = 0;
	double credito = 0;
	double debito = 0;

	
	Border borda = BorderFactory.createLineBorder(new Color(0xe5e5e5), 1);
	
	TelaMensal(JFrame fr) {	
		frameAntigo = fr;
		frame.setTitle("Fluxo Mensal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		
		localVoltar.setBounds(10, 5, 100, 40);
		localVoltar.setOpaque(false);
		botaoVoltar.setFocusable(false);
		botaoVoltar.addActionListener(this);
		botaoVoltar.setBackground(amarelo);
		botaoVoltar.setForeground(Color.white);
		botaoVoltar.setFont(new Font("Arial", Font.BOLD, 15));
		localVoltar.add(botaoVoltar);
		frame.add(localVoltar);
		
		localBotao.setBounds(1190, 5, 80, 40);
		localBotao.setOpaque(false);
		botaoAdd.setFocusable(false);
		botaoAdd.addActionListener(this);
		botaoAdd.setBackground(verde);
		botaoAdd.setForeground(Color.white);
		botaoAdd.setFont(new Font("Arial", Font.BOLD, 15));
		localBotao.add(botaoAdd);
		frame.add(localBotao);
		
		botao.setBounds(120, 550, 254, 39);
		botao.setFocusable(false);
		botao.addActionListener(this);
		botao.setBackground(verde);
		botao.setForeground(Color.white);
		botao.setFont(new Font("Arial", Font.BOLD, 15));
		periodo.add(botao);
		
		periodo.setBounds(0, 0, 1280, 50);
		periodo.setLayout(new FlowLayout());
		periodo.setBackground(Color.white);
		periodo.add(selectMes);
		periodo.add(selectAno);
		selectMes.addActionListener(this);
		selectAno.addActionListener(this);
		frame.add(periodo);
		
		JLabel labelData = new JLabel("Data");
		JLabel labelCat = new JLabel("  Categoria");
		JLabel labelDesc = new JLabel("  Descricao");
		JLabel labelValor = new JLabel("Valor");
		
		labelData.setBounds(0, 50, 250, 50);
		labelData.setBorder(borda);
		labelData.setHorizontalAlignment(JLabel.CENTER);
		labelData.setFont(new Font("Arial", Font.BOLD, 15));
		labelData.setForeground(Color.DARK_GRAY);
		
		labelCat.setBounds(250, 50, 300, 50);
		labelCat.setBorder(borda);
		labelCat.setFont(new Font("Arial", Font.BOLD, 15));
		labelCat.setForeground(Color.DARK_GRAY);
		
		labelDesc.setBounds(550, 50, 400, 50);
		labelDesc.setBorder(borda);
		labelDesc.setFont(new Font("Arial", Font.BOLD, 15));
		labelDesc.setForeground(Color.DARK_GRAY);
		
		labelValor.setBounds(950, 50, 330, 50);
		labelValor.setBorder(borda);
		labelValor.setFont(new Font("Arial", Font.BOLD, 15));
		labelValor.setForeground(Color.DARK_GRAY);
		labelValor.setHorizontalAlignment(JLabel.CENTER);
		
		frame.add(labelData);
		frame.add(labelCat);
		frame.add(labelDesc);
		frame.add(labelValor);
		
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
		
		creditoFinal.setBounds(0, 580, 1280, 50);
		creditoFinal.setLayout(null);
		creditoFinal.setBackground(Color.white);
		creditoFinal.setBorder(borda);
		frame.add(creditoFinal);
		
		debitoFinal.setBounds(0, 630, 1280, 50);
		debitoFinal.setLayout(null);
		debitoFinal.setBackground(Color.white);
		debitoFinal.setBorder(borda);
		frame.add(debitoFinal);
		
		calcular();
		
		frame.add(scrollFrame);
		frame.setVisible(true);
		atualizarLista();
	}

	public void calcular() {
		JLabel sV = new JLabel("R$ " + String.format("%.2f", saldo));
		JLabel cV = new JLabel("R$ " + String.format("%.2f", credito));
		JLabel dV = new JLabel("R$ " + String.format("%.2f", debito));
		
		saldoFinal.removeAll();
		creditoFinal.removeAll();
		debitoFinal.removeAll();
		
		JLabel sD = new JLabel("Saldo do Periodo:  ");
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
		
		JLabel cD = new JLabel("Creditos:  ");
		cD.setBounds(750, 0, 200, 50);
		cD.setHorizontalAlignment(JLabel.RIGHT);
		cD.setFont(new Font("Arial", Font.BOLD, 20));
		cD.setForeground(azul);
		cV.setBounds(950, 0, 315, 50);
		cV.setHorizontalAlignment(JLabel.CENTER);
		cV.setFont(new Font("Arial", Font.BOLD, 20));
		cV.setForeground(verde);
		creditoFinal.add(cD);
		creditoFinal.add(cV);
		
		JLabel dD = new JLabel("Debitos:  ");
		dD.setBounds(750, 0, 200, 50);
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
	
	public void inserir(LinhaMensal linha) {
		nLinhas++;
		panel.setPreferredSize(new Dimension(1280, nLinhas*71));
		panel.add(linha);
		saldo += linha.getValor();
		credito += linha.getValor();
		if(linha.getValor() < 0) {
			credito -= linha.getValor();
			debito += linha.getValor();
		}
		calcular();
	}
	
	public void limparLista() {
		nLinhas = 0;
		saldo = 0;
		credito = 0;
		debito = 0;
		panel.removeAll();
		panel.setPreferredSize(new Dimension(1280, nLinhas*71));
		calcular();
	}
	
	public void atualizarLista() {
		limparLista();
		try {
			//Busca despesas e receitas no banco de dados
			List<Operacao> despesas = OperacaoDAO.get_operacoes(false, mesPesquisa, anoPesquisa);
			List<Operacao> receitas = OperacaoDAO.get_operacoes(true, mesPesquisa, anoPesquisa);
			
			//Junta as listas e ordena
			List<Operacao> total = OperacaoDAO.join_operacoes(despesas, receitas);
			ListIterator<Operacao> it = total.listIterator();
			
			//Adiciona lista a interface
			while(it.hasNext()) {
				Operacao opx = it.next();
				String data = opx.getDia() + "/" + opx.getMes() + "/" + opx.getAno();
				inserir(new LinhaMensal(opx.getId(), data, opx.getCategoria(), opx.getDescricao(), opx.getValor(), this));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public int getMes() {
		return mesPesquisa;
	}
	
	public int getAno() {
		return anoPesquisa;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botao) {
			atualizarLista();
		}
		if(e.getSource() == botaoAdd) {
			new TelaEdicao(this);
		}
		if(e.getSource() == selectMes) {
			mesPesquisa = selectMes.getSelectedIndex() + 1;
		}
		if(e.getSource() == selectAno) {
			anoPesquisa = (int)selectAno.getSelectedItem();
		}
		if(e.getSource() == botaoVoltar) {
			frame.dispose();
			frameAntigo.setVisible(true);
		}
	}
}
