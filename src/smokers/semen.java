package smokers;

import java.util.Random;

public class semen {
	public static void main(String[] args) {
		Mesa mesa = new Mesa();
		Fumador fumador1 = new Fumador("tabaco", mesa);
		Fumador fumador2 = new Fumador("papel", mesa);
		Fumador fumador3 = new Fumador("cerillas", mesa);
		Agente agente = new Agente(mesa);
		agente.start();
		fumador1.start();
		fumador2.start();
		fumador3.start();
	}
}

class Fumador extends Thread{
	private String ingrediente;
	private Mesa mesa;
	
	Fumador(String ingrediente, Mesa mesa){
		this.ingrediente = ingrediente;
		this.mesa = mesa;
	}
	@Override
	public void run() {
		while(true) {
			if(mesa.comprobar(ingrediente) == true ) {
				mesa.quitar();
				System.out.println("se ha liado un cigarro " +Thread.currentThread().getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
class Agente extends Thread{
	private String [] ingredientes = {"tabaco" ,"papel","cerillas"};
	private Mesa mesa;
	private Random random = new Random();
	
	Agente(Mesa mesa){
		this.mesa = mesa;
	}
	@Override
	public void run() {
		while(true) {
			String ingrediente1 = ingredientes[random.nextInt(3)];
			String ingrediente2 = ingredientes[random.nextInt(3)];
			while(ingrediente1.equals(ingrediente2)) {
				ingrediente2 = ingredientes[random.nextInt(3)];
			}
			mesa.colocar(ingrediente1, ingrediente2);
			System.out.println("se ha colocado los ingredientes " +Thread.currentThread().getName());
		}
	}
}
class Mesa{
	private String ingrediente1;
	private String ingrediente2;
	
	Mesa(){
	}
	public boolean comprobar (String ingrediente) {
		//System.out.println("aaaaaaa" + ingrediente);
		//System.out.println("bbbbbbb" + ingrediente1 + ingrediente2);
		if(!ingrediente.equals(ingrediente1) && !ingrediente.equals(ingrediente2)) {
			return true;
		}
		return false;
	}
	public synchronized void quitar () {
		if(ingrediente1 == null && ingrediente2 == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("ingredientes: " + ingrediente1 + ingrediente2 );
		ingrediente1 = null;
		ingrediente2 = null;
		notify();
	}
	public synchronized void colocar (String ingredienteU , String ingredienteP) {
		if(ingrediente1 != null && ingrediente2 != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ingrediente1 = ingredienteU;
		ingrediente2 = ingredienteP;
		notify();
	}
	
	
}