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
	
	//We make a client side out of this keyboard:
	Socket s = null;
	PrintWriter out = null;
	BufferedReader in = null;

	public Keyboard() {
		
		//Client tools initialization
		try {
			s = new Socket("localhost", 9999);
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
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
		addKey(new UnaryOperatorKey("\u00b1", a -> -a), 2, 1, 1, 1, constraints);
		addKey(new UnaryOperatorKey("\u221a", a -> Math.sqrt(a)), 3, 1, 1, 1, constraints);
		addKey(new NumberKey("1"), 0, 2, 1, 1, constraints);
		addKey(new NumberKey("2"), 1, 2, 1, 1, constraints);
		addKey(new NumberKey("3"), 2, 2, 1, 1, constraints);
		addKey(new BinaryOperatorKey("\u00f7", (a, b) -> a / b), 3, 2, 1, 1, constraints);
		addKey(new NumberKey("4"), 0, 3, 1, 1, constraints);
		addKey(new NumberKey("5"), 1, 3, 1, 1, constraints);
		addKey(new NumberKey("6"), 2, 3, 1, 1, constraints);
		addKey(new BinaryOperatorKey("\u00d7", (a, b) -> a * b), 3, 3, 1, 1, constraints);
		addKey(new NumberKey("7"), 0, 4, 1, 1, constraints);
		addKey(new NumberKey("8"), 1, 4, 1, 1, constraints);
		addKey(new NumberKey("9"), 2, 4, 1, 1, constraints);
		addKey(new BinaryOperatorKey("-", (a, b) -> a - b), 3, 4, 1, 1, constraints);
		addKey(new NumberKey("0"), 0, 5, 1, 1, constraints);
		addKey(new DecimalKey(), 1, 5, 1, 1, constraints);
		addKey(new UnaryOperatorKey("=", a -> a), 2, 5, 1, 1, constraints);
		addKey(new BinaryOperatorKey("+", (a, b) -> {return a + b;}), 3, 5, 1, 1, constraints);
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
		
		
		protected void setText() {
			try {
				String s = in.readLine();
				display.setText(s);
			}catch (Exception e) {}
		}
		
		protected abstract void update(ActionEvent e);
	}
	
	private class ClearKey extends Key {

		private static final long serialVersionUID = 1L;
		
		public ClearKey() {
			super("C");
		}
		
		protected void update(ActionEvent e) {
			out.println("C");
			out.flush();
			setText();
		}
		
	}
	
	private class NumberKey extends Key {

		private static final long serialVersionUID = 1L;
		private int value;
		
		public NumberKey(String number) {
			super(number);
			value = Integer.parseInt(number);
		}
		
		protected void update(ActionEvent e) {
			out.println(value);
			out.flush();
			setText();
		}

	}
	
	private class BinaryOperatorKey extends Key {

		private static final long serialVersionUID = 1L;
		private BinaryOperator<Double> operation;
		private String symbol;
		
		public BinaryOperatorKey(String symbol, BinaryOperator<Double> operation) {
			super(symbol);
			this.operation = operation;
			this.symbol = symbol;
		}
		
		protected void update(ActionEvent e) {
			out.println(symbol);
			out.flush();
			setText();
		}
		
	}
	
	private class UnaryOperatorKey extends Key {

		private static final long serialVersionUID = 1L;
		private UnaryOperator<Double> operation;
		private String symbol;
		
		public UnaryOperatorKey(String symbol, UnaryOperator<Double> operation) {
			super(symbol);
			this.operation = operation;
			this.symbol = symbol;
		}
		
		protected void update(ActionEvent e) {
			out.println(symbol);
			out.flush();
			setText();
		}
		
	}
	
	private class DecimalKey extends Key {

		private static final long serialVersionUID = 1L;
		
		public DecimalKey() {
			super(",");
		}
		
		protected void update(ActionEvent e) {
			out.println("decimal");
			setText();
		}
		
	}
}
