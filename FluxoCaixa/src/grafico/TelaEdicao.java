package grafico;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import classes.Operacao;
import dao.OperacaoDAO;

public class TelaEdicao implements ActionListener{
	
	Integer[] dias = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
	String[] meses = {"jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"};
	Integer[] anos = {2021, 2020, 2019, 2018, 2017, 2016, 2015};
	JComboBox<Integer> selectDia = new JComboBox<>(dias);
	JComboBox<String> selectMes = new JComboBox<>(meses);
	JComboBox<Integer> selectAno = new JComboBox<>(anos);
	JTextField categoriaText;
	JTextField valorText;
	JTextArea descText;
	JLabel error = new JLabel();
	final static JPanel warning = new JPanel();
	TelaMensal telaMae;

	JButton botSalvar;
	JButton botVoltar;
	JButton botExcluir;
	
	Color verde = new Color(0x01b88a);
	Color vermelho = new Color(0xe86b67);
	Color amarelo = new Color(0xF4D35E);
	
	int ID = 0;
	int diaEscrito = 1;
	int mesEscrito = 1;
	int anoEscrito = 2021;
	String categoria;
	String descricao;
	double valor;
	boolean receita = true;
	
	JFrame frame = new JFrame();
	
	TelaEdicao(TelaMensal tela) {
		telaMae = tela;
		
		mesEscrito = telaMae.getMes();
		anoEscrito = telaMae.getAno();
		selectMes.setSelectedIndex(mesEscrito - 1);
		selectAno.setSelectedItem(anoEscrito);
		
		frame.setTitle("Alterar Database");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(430, 450);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		
		error.setBounds(15, 375, 400, 30);
		error.setHorizontalAlignment(JLabel.CENTER);
		error.setForeground(vermelho);
		error.setFont(new Font("Arial", Font.BOLD, 15));
		frame.add(error);
		
		JLabel data_label = new JLabel("Data :");
		data_label.setBounds(20,5,65,30);
		data_label.setHorizontalAlignment(JLabel.RIGHT);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBounds(90,5,250,30);
		dataPanel.setOpaque(false);
		dataPanel.add(selectDia);
		dataPanel.add(selectMes);
		dataPanel.add(selectAno);
		selectDia.addActionListener(this);
		selectMes.addActionListener(this);
		selectAno.addActionListener(this);
		
		JLabel categoria_label = new JLabel("Categoria :");
		categoria_label.setBounds(0,40,85,30);
		categoria_label.setHorizontalAlignment(JLabel.RIGHT);
		
		categoriaText = new JTextField();
		categoriaText.setBounds(90,40,250,30);
		
		JLabel valor_label = new JLabel("Valor :");
		valor_label.setBounds(20,75,65,30);
		valor_label.setHorizontalAlignment(JLabel.RIGHT);
		
		valorText = new JTextField();
		valorText.setBounds(90, 75, 250, 30);
		
		JLabel desc_label = new JLabel("Descricao :");
		desc_label.setBounds(0,110,85,30);
		desc_label.setHorizontalAlignment(JLabel.RIGHT);
		
		descText = new JTextArea(5, 20);
		descText.setBounds(90, 110, 250, 150);
		descText.setLineWrap(true);
		descText.setWrapStyleWord(true);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		descText.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		JPanel botoesPanel = new JPanel();
		botoesPanel.setBounds(90,260,250,40);
		botoesPanel.setOpaque(false);
		botSalvar  = new JButton("Salvar");
		botVoltar  = new JButton("Voltar");
		botVoltar.setBounds(150,300,100,30);
		botoesPanel.add(botSalvar);
		botoesPanel.add(botVoltar);
		botSalvar.addActionListener(this);
		botVoltar.addActionListener(this);
		
		botSalvar.setBackground(verde);
		botSalvar.setForeground(Color.white);
		botVoltar.setBackground(amarelo);
		botVoltar.setForeground(Color.white);
		
		botExcluir  = new JButton("Excluir");
		botExcluir.setBounds(165,330,100,30);
		botExcluir.addActionListener(this);
		botExcluir.setBackground(vermelho);
		botExcluir.setForeground(Color.white);
		
		frame.add(data_label);
		frame.add(dataPanel);
		frame.add(categoria_label);
		frame.add(valor_label);
		frame.add(categoriaText);
		frame.add(valorText);
		frame.add(desc_label);
		frame.add(descText);
		frame.add(botoesPanel);
		
		frame.setVisible(true);
		
	}
	
	TelaEdicao(int index, double valor, TelaMensal tela) {
		this(tela);
		if(index != 0)
			frame.add(botExcluir);
		if(valor < 0) receita = false;
		ID = index;
		//Aqui devera ser importado os valores do objeto de index igual a ID.
		try {
			Operacao op = OperacaoDAO.get_operacao(receita, index);
			diaEscrito = op.getDia();
			selectDia.setSelectedIndex(diaEscrito - 1);
			mesEscrito = op.getMes();
			selectMes.setSelectedIndex(mesEscrito - 1);
			anoEscrito = op.getAno();
			selectAno.setSelectedItem(anoEscrito);
			categoria = op.getCategoria();
			categoriaText.setText(categoria);
			valor = op.getValor();
			valorText.setText(String.format("%.2f", valor));
			descricao = op.getDescricao();
			descText.setText(descricao);
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(warning, "Ocorreu um erro no banco de dados!", "Erro",
			        JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == botSalvar) {
				
				try{
					boolean receita_anterior = receita;
					descricao = descText.getText();
					categoria = categoriaText.getText();
					valor = Double.valueOf(valorText.getText().replaceAll(",", "."));
					
					if(valor < 0) {
						receita = false;
					}
					else receita = true;
					
					Operacao op = new Operacao(ID, descricao, categoria, valor, diaEscrito, mesEscrito, anoEscrito, receita);
					if(ID == 0) {
						//CRIA UM NOVO OBJETO NA DATABASE COM AS INFORMACOES PROVIDAS
						OperacaoDAO.create_operacao(op);
					}
					else {
						//ATUALIZA O OBJETO NESTE ID COM AS INFORMACOES PROVIDAS
						OperacaoDAO.update_operacao(receita_anterior, op);
					}
					frame.dispose();
					telaMae.atualizarLista();
				}
				catch (NumberFormatException e1) {
					error.setText("Valor invalido! Formato: 123.45 ou -123.45");
				} 
				catch (SQLException e1) {
					if(e1.getSQLState().charAt(0) == '2' && e1.getSQLState().charAt(1) == '2') error.setText("Dados invalidos, verifique o tamanho!");
					else error.setText("Erro no banco de dados!");
				}
				catch (Exception e1) {
					error.setText("Erro nao identificado!");
				}
			}
			if(e.getSource() == botVoltar) {
				frame.dispose();
			}
			if(e.getSource() == botExcluir) {
				if(ID != 0) {
					//EXCLUI O ITEM COM ESTE ID DA DATABASE
					try {
						OperacaoDAO.delete_operacao(receita, ID);
						frame.dispose();
						telaMae.atualizarLista();
					} 
					catch (SQLException e1) {
						error.setText("Erro no banco de dados!");
					}
					catch (Exception e1) {
						error.setText("Erro nao identificado!");
					}
				}
			}
			if(e.getSource() == selectDia) {
				diaEscrito = (int)selectDia.getSelectedItem();
			}
			if(e.getSource() == selectMes) {
				mesEscrito = selectMes.getSelectedIndex() + 1;
			}
			if(e.getSource() == selectAno) {
				anoEscrito = (int)selectAno.getSelectedItem();
			}
	}
}
