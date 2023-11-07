package smokers;

import java.util.Random;

import eval1.Cigars;

public class SmokersSingleFile {

	public static void main(String[] args) {

		Table table = new Table();
		new Agent(table).start();
		new Smoker("Alberto", Cigars.PAPEL, table).start();
		new Smoker("Flowee", Cigars.TABACO, table).start();
		new Smoker("Ame", Cigars.FILTRO, table).start();
	}

}

class Smoker extends Thread{

	private Table t;
	private Cigars e;

	public Smoker (String name, Cigars e, Table t) {
		super(name);
		this.t = t;
		this.e = e;
	}

	public void run() {
		while (true) {
			if (t.getProds()[0] != e && t.getProds()[1] != e) {
				t.retrieve();
				System.out.println(Thread.currentThread().getName() + " rolls one and smokes it");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
	}

}

class Agent extends Thread{
	
	Table t;
	Random rnd = new Random();
	private Cigars e1;
	private Cigars e2;

	public Agent (Table t) {
		super("Agent");
		this.t = t;
	}

	public void run() {
		while (true) {
			this.e1 = Cigars.values()[rnd.nextInt(Cigars.values().length)];
			this.e2 = Cigars.values()[rnd.nextInt(Cigars.values().length)];
			while (e2 == e1) this.e2 = Cigars.values()[rnd.nextInt(Cigars.values().length)];
			t.store(e1, e2);
			System.out.println("Agent puts on the table: " + e1 + " + " + e2);
		}
	}
}

class Table {

	private Cigars e1 = null;
	private Cigars e2 = null;

	public Table () {}

	public synchronized void store(Cigars e1, Cigars e2) {
		while (this.e1 != null || this.e2 != null) 
			try {
				wait(); 
			} catch (InterruptedException err) {}
		this.e1 = e1;
		this.e2 = e2;
		notify(); 
	}

	public synchronized void retrieve() {
		while (this.e1 == null || this.e2 == null) 
			try {
				wait();
			} catch (InterruptedException err) {}
		this.e1 = null;
		this.e2 = null;
		try {
			Thread.sleep(1000);
		}catch (Exception e) {}
		notify();
	}
	
	public synchronized Cigars[] getProds() {
		return new Cigars[] {e1, e2};
	}
	
}
