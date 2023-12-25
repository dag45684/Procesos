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
		addKey(new ClearKey(), 0, 1, 2, 1, constraints);
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
		addKey(new DecimalKey(), 1, 5, 1, 1, constraints);
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
	
	private class ClearKey extends Key {
		private static final long serialVersionUID = 1L;
		
		public ClearKey() {
			super("C");
		}
		
		protected void update(ActionEvent e) {
			display.setText("");
			ans = 0;
			out.println("C");
			out.flush();
		}
	}
	
	private class NumberKey extends Key {
		private static final long serialVersionUID = 1L;
		private String value;
		
		public NumberKey(String number) {
			super(number);
			value = number;
		}
		
		protected void update(ActionEvent e) {
			if (Double.toString(ans).contains(display.getText()) || display.getText().equals("0")) display.setText(value);
			else display.setText(display.getText()+value);
		}
	}
	
	private class OperatorKey extends Key {
		private static final long serialVersionUID = 1L;
		private String symbol;
		
		public OperatorKey(String symbol) {
			super(symbol);
			this.symbol = symbol;
		}
		
		protected void update(ActionEvent e) {
			if (symbol.equals("=")) {
				ans = Double.parseDouble(display.getText()); 
				out.println(display.getText());
				out.flush();
				try {
					String response = in.readLine(); 
					System.out.println(response);
					display.setText(response);
				} catch (IOException err) {System.err.println(err);}
				return;
			}
			if (ans == 0) {
				ans = Double.parseDouble(display.getText());
				out.println(display.getText()+symbol);
				out.flush();
			} else {
				if (!symbol.equals("\u00f7") && !symbol.equals("\u00b1")) {
					out.println(display.getText());
					out.flush();
					try {
						ans = Double.parseDouble(in.readLine());
					} catch (Exception e1) {}
					display.setText(Double.toString(ans));
					out.println(display.getText()+symbol);
					out.flush();
				}else {
					out.println(display.getText()+symbol);
					out.flush();
					try {
						display.setText(in.readLine());
					} catch (IOException e1) {
						display.setText("ERR");
					}
				}
			}
		}
	}
	
	private class DecimalKey extends Key {
		private static final long serialVersionUID = 1L;
		
		public DecimalKey() {
			super(",");
		}
		
		protected void update(ActionEvent e) {
			display.setText(display.getText()+'.');
		}
	}
}
