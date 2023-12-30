package calculadora;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Keyboard extends JPanel {

	private static final long serialVersionUID = 1L;
	private Font font;
	
	private Display display = new Display();
	
	private double ans;
	private String memory = "";
	private boolean clear = true;
	private boolean concat = false;
	private boolean getans = false;
	
	//We make a client side out of this keyboard:
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;

	public Keyboard() {
		
		//Client tools initialization
		try {
			socket = new Socket("localhost", 9999);
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch (Exception e) {}

		try {
			font = Font.createFont(Font.PLAIN, Keyboard.class.getResourceAsStream("code.otf")).deriveFont(50f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30), getBorder()));
		setBackground(Color.WHITE);
		GridBagConstraints constraints = new GridBagConstraints();
		addKey(display, 0, 0, 4, 1, constraints);
		addKey(new OperatorKey("C"), 0, 1, 2, 1, constraints);
		addKey(new OperatorKey("\u00b1"), 2, 1, 1, 1, constraints);
		addKey(new OperatorKey("\u221a"), 3, 1, 1, 1, constraints);
		addKey(new NumberKey("1"), 0, 2, 1, 1, constraints);
		addKey(new NumberKey("2"), 1, 2, 1, 1, constraints);
		addKey(new NumberKey("3"), 2, 2, 1, 1, constraints);
		addKey(new OperatorKey("\u00f7"), 3, 2, 1, 1, constraints);
		addKey(new NumberKey("4"), 0, 3, 1, 1, constraints);
		addKey(new NumberKey("5"), 1, 3, 1, 1, constraints);
		addKey(new NumberKey("6"), 2, 3, 1, 1, constraints);
		addKey(new OperatorKey("\u00d7"), 3, 3, 1, 1, constraints);
		addKey(new NumberKey("7"), 0, 4, 1, 1, constraints);
		addKey(new NumberKey("8"), 1, 4, 1, 1, constraints);
		addKey(new NumberKey("9"), 2, 4, 1, 1, constraints);
		addKey(new OperatorKey("-"), 3, 4, 1, 1, constraints);
		addKey(new NumberKey("0"), 0, 5, 1, 1, constraints);
		addKey(new OperatorKey("."), 1, 5, 1, 1, constraints);
		addKey(new OperatorKey("="), 2, 5, 1, 1, constraints);
		addKey(new OperatorKey("+"), 3, 5, 1, 1, constraints);
	}

	private void addKey(JComponent component, int x, int y, int width, int height, GridBagConstraints constraints) {
		constraints.gridwidth = width;
		constraints.gridheight = height;
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		add(component, constraints);
	}

	private abstract class Key extends JButton {
		protected static final long serialVersionUID = 1L;
		
		private Key(String text) {
			super(text);
			setFont(font);
			setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(20, 20, 20, 20)));
			addActionListener(this::update);
		}
		
		protected abstract void update(ActionEvent e);
	}
	
	private class NumberKey extends Key {
		private static final long serialVersionUID = 1L;
		private String value;
		
		public NumberKey(String number) {
			super(number);
			value = number;
		}
		
		protected void update(ActionEvent e) {
			getans = false;
			if (clear) {
				display.setText(value);
				clear = false;
			} else {
				display.setText(display.getText()+value);
			}
			memory += value;
		}
	}
	
	private class OperatorKey extends Key {
		private static final long serialVersionUID = 1L;
		private char symbol;
		
		public OperatorKey(String symbol) {
			super(symbol);
			this.symbol = symbol.charAt(0);
		}
		
		protected void update(ActionEvent e) {
			switch(symbol) {
			case '=':
				out.println(memory);
				out.flush();
				try {
					display.setText(in.readLine());
				} catch (IOException e1) { }
				clear = true;
				getans = true;
				concat = false;
				ans = Double.parseDouble(display.getText());
				memory=Double.toString(ans);
				break;
			case '\u221a':
				memory+="\u221a";
				out.println(memory);
				out.flush();
				try {
					display.setText(in.readLine());
					memory = display.getText();
				} catch (IOException e1) { }
				clear = true;
				break;
			case '.':
				display.setText(display.getText()+symbol);
				break;
			case '\u00b1':
				if (display.getText().charAt(0) == '-') {
					display.setText(display.getText().substring(1));
					if(memory.contains(" ")) {
						memory = memory.substring(0, memory.indexOf(" ")-1) + display.getText(); 
					} else {
						memory = display.getText();
					}
				}else {
					display.setText("-"+display.getText());
					if(memory.contains(" ")) {
						memory = memory.substring(0, memory.indexOf(" ")+1) + '-' + display.getText(); 
					}else {
						memory = '-' + memory;
					}
				}
				break;
			case 'C':
				display.setText("0");
				memory = "";
				clear = true;
				concat = false;
				break;
			default:
				if(concat) {
					out.println(memory);
					out.flush();
					try {
						display.setText(in.readLine());
						ans = Double.parseDouble(display.getText());
						getans = true;
						memory = Double.toString(ans);
					} catch (IOException e1) { }
				}
				memory += symbol + " ";
				concat = true;
				clear = true;
				break;
			}
			
		}
	}
}
