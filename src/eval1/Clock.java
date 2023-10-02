package eval1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Clock extends JFrame implements Runnable {
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private JLabel hour;
	
	public Clock() {
		super("Clock");
		hour = new JLabel(formatter.format(LocalDateTime.now()));
		hour.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(70,70,70,70),
				hour.getBorder()));
		hour.setFont(hour.getFont().deriveFont(30f));
		hour.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(hour);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void run() {
		Runnable setHour = new Runnable() {
			public void run() {
				hour.setText(formatter.format(LocalDateTime.now()));
			}
		};
		while (true) {
			SwingUtilities.invokeLater(setHour);
			try {
				Thread.sleep(100);
			}catch (Exception e) {}
		}
	}
	
	public void start() {
		setVisible(true);
		new Thread(this, "seconds").start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				new Clock().start();
			}
		});
	}
}
