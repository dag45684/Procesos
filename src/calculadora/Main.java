package calculadora;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public Main() {
		super("Calculator Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new Keyboard());
		pack();
		setLocationRelativeTo(null);
	}
	
	void start() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main()::start);
	}

}
