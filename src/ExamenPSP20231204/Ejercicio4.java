package ExamenPSP20231204;

import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio4 {

	public static void main(String[] args) throws InterruptedException {
		
		Scanner s = new Scanner(System.in);
		
		String data = s.nextLine();
		if(!data.matches("(\\w\\d+)+")){
			System.err.println("Wrong format. Exiting....");
			s.close();
			System.exit(0);
		}
		s.close();
		
		ArrayList<EscribeLetras> ts = new ArrayList<>();
		
		//He asumido que el maximo numero de repeticiones son 9 para que sean parejas de 2 char
		//en el input como en tu ejemplo. Si quieres permitir que el numero sea 1284 basta con cambiar
		//este for a un bucle while de regex que capturen (\\D\d+)+ igual que en el matches de la linea 13
		for(int i=0; i<data.length(); i+=2) {
			ts.add(new EscribeLetras(data.substring(i, i+2)));
		}
		
		
		Worder w = new Worder(ts);
		w.WriteThings();
	}
}

class Worder {

	ArrayList<EscribeLetras> ts = new ArrayList<>();
	
	public Worder( ArrayList<EscribeLetras> ts) {
		this.ts = ts;
	}
	
	public synchronized void WriteThings() throws InterruptedException {
		for(EscribeLetras t : this.ts) {
			t.start();
			t.join();
			notify();
		}
	}
}

class EscribeLetras extends Thread{
	
	int reps;
	char letter;

	public EscribeLetras(String s) {
		reps = Integer.parseInt(s.replaceAll("\\D", ""));
		letter = s.replace("\\d+", "").charAt(0);

	}

	public void run() {
		for (int  i=0; i<reps; i++) {
			System.out.print(letter);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
			
	}
}
