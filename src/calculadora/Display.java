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
	
}