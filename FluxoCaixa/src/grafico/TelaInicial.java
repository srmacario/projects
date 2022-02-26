package grafico;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.*;

import principal.ConnectionDB;

public class TelaInicial implements ActionListener{

	static JFrame frame = new JFrame();
	JButton botao = new JButton("Conectar");
	URL url = getClass().getResource("/resource/TelaInicialFluxo.png");
	ImageIcon fundo = new ImageIcon(url);
	JLabel fundoLabel = new JLabel();
	JLabel error = new JLabel();
	
	private static JTextField endServ;
	private static JTextField user;
	private static JPasswordField password;
	private static JTextField name;
	
	Color vermelho = new Color(0xe86b67);
	
	TelaInicial() {

		frame.setTitle("Fluxo de Caixa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		
		JLayeredPane camadas = new JLayeredPane();
		camadas.setBounds(0, 0, 1280, 720);
		error.setBounds(200, 160, 880, 40);
		error.setHorizontalAlignment(JLabel.CENTER);
		error.setForeground(vermelho);
		error.setFont(new Font("Arial", Font.BOLD, 15));
		camadas.add(error);
		frame.add(camadas);
		
		fundoLabel.setIcon(fundo);
		fundoLabel.setBounds(0, -39, 1280, 720);
		
		botao.setBounds(17, 560, 254, 39);
		botao.setFocusable(false);
		botao.addActionListener(inserir);
		botao.setBackground(new Color(0x01b88a));
		botao.setForeground(Color.white);
		botao.setFont(new Font("Arial", Font.BOLD, 15));
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		endServ = new JTextField("jdbc:postgresql://localhost:5432/");
		endServ.setBounds(17, 192, 254, 39);
		endServ.setBackground(Color.white);
		endServ.setForeground(Color.black);
		endServ.setBorder(null);
		endServ.setFont(new Font("Arial", Font.PLAIN, 15));
		
		user = new JTextField();
		user.setBounds(17, 287, 254, 39);
		user.addActionListener(inserir);
		user.setBackground(Color.white);
		user.setForeground(Color.black);
		user.setBorder(null);
		user.setFont(new Font("Arial", Font.PLAIN, 20));
		
		password = new JPasswordField();
		password.setBounds(17, 379, 254, 39);
		password.setBackground(Color.white);
		password.setForeground(Color.black);
		password.setBorder(null);
		password.setFont(new Font("Arial", Font.PLAIN, 20));
		
		name = new JTextField();
		name.setBounds(17, 464, 254, 39);
		name.addActionListener(inserir);
		name.setBackground(Color.white);
		name.setForeground(Color.black);
		name.setBorder(null);
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		
		panel.add(endServ);
		panel.add(user);
		panel.add(password);
		panel.add(name);
		panel.setOpaque(false);
		panel.setBounds(492, 20, 300, 745);
		panel.add(botao);
		
		camadas.add(panel);
		camadas.add(fundoLabel);
		
		frame.setVisible(true);
	}

	
	Action inserir = new AbstractAction()
	{
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
			try {
				String pass = new String(password.getPassword());
				ConnectionDB.iniciarDB(endServ.getText(), user.getText(), pass, name.getText());
				frame.dispose();
			} catch (ClassNotFoundException e1) {
				error.setText("Problema no Driver \"" + e1.getMessage() + "\". Verifique se o build path foi adicionado!");
			} catch (SQLException e1) {
				error.setText(e1.getMessage());
			}
	    }
	};

	@Override
	public void actionPerformed(ActionEvent e) {}
	
}
