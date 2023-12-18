package calculadora;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class Display extends JTextField {

	static final long serialVersionUID = 1L;
	private double saved, current;
	private boolean clear;
	private int decimal;
	private BinaryOperator<Double> binaryOperation;

	public Display() {
		super("0", 20);
		try {
			setFont(Font.createFont(Font.PLAIN, Display.class.getResourceAsStream("Calculator.ttf"))
						.deriveFont(70f));
		} catch (FontFormatException | IOException e) {
		}
		setFocusable(false);
		setEditable(false);
		setHorizontalAlignment(JTextField.RIGHT);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30), getBorder()));
	}
	
	public double getCurrent() {
		return current;
	}

	public double getSaved() {
		return saved;
	}

	public void startDecimal() {
		if (decimal == 0)
			decimal++;
	}
	
	public void clear() {
		setText("0");
		saved = current = decimal = 0;
		clear = false;
		binaryOperation = null;
	}

	
	public void binaryOperation(BinaryOperator<Double> operation) {
		if (binaryOperation != null) 
			setText(String.valueOf(current =  binaryOperation.apply(saved, current)));
		saved = current;
		clear = true;
		binaryOperation = operation;
		System.out.printf("saved(%f), current(%f), %s\n", saved, current, binaryOperation);
	}
	
	protected void unaryOperation(UnaryOperator<Double> operation) {
		if (binaryOperation != null) {
			current = binaryOperation.apply(saved, current);
			binaryOperation = null;
		}
		setText(String.valueOf(current = operation.apply(current)));
		clear = true;
		
		System.out.printf("saved(%f), current(%f), %s\n", saved, current, binaryOperation);
	}
	
	public void update(int digit) {
		if (clear) {
			current = decimal = 0;
			clear = false;
		}
		if (decimal > 0)
			current = current + digit / Math.pow(10, decimal);
		else
			current = current * 10 + digit;
		String format = String.format("%%.%df", decimal == 0 ? 0 : decimal++);
		setText(String.format(format, current));
	}
	
}
