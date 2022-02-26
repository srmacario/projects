package grafico;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TelaAbertura implements MouseListener {
	
	static JFrame frame = new JFrame();
	static JLabel label1, label2, botao1, botao2;
	static ImageIcon b1n = new ImageIcon(), b1p = new ImageIcon(), b2n = new ImageIcon(), b2p = new ImageIcon();
	URL url1 = getClass().getResource("/resource/fluxo-normal.png");
	URL url2 = getClass().getResource("/resource/fluxo-press.png");
	URL url3 = getClass().getResource("/resource/saldo-normal.png");
	URL url4 = getClass().getResource("/resource/saldo-press.png");
	
	public TelaAbertura() {
		
		frame.setTitle("Fluxo de Caixa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.white);
		
		b1n = new ImageIcon(url1);
		b1p = new ImageIcon(url2);
		b2n = new ImageIcon(url3);
		b2p = new ImageIcon(url4);
		
		label1 = new JLabel();
		label1.setIcon(b1n);
		label1.setBounds(0, 0, 1280, 720);
		label2 = new JLabel();
		label2.setIcon(b2n);
		label2.setBounds(0, 0, 1280, 720);
		
		botao1 = new JLabel();
		botao1.setBounds(180, 143, 424, 424);
		botao1.addMouseListener(this);
		botao2 = new JLabel();
		botao2.setBounds(707, 143, 424, 424);
		botao2.addMouseListener(this);
		
		frame.add(botao1);
		frame.add(botao2);
		frame.add(label1);
		frame.add(label2);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == botao1)
			label1.setIcon(b1n);
		else
			label2.setIcon(b2n);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == botao1) {
			frame.setVisible(false);
			new TelaMensal(frame);
		}
		else {
			frame.setVisible(false);
			new TelaPeriodo(frame);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == botao1)
			label1.setIcon(b1p);
		if(e.getSource() == botao2)
			label2.setIcon(b2p);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == botao1)
			label1.setIcon(b1n);
		else
			label2.setIcon(b2n);
	}
}
