package eval1;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		
		int np = 5;
		Person[] people = new Person[np];
		Thread[] threads = new Thread[np];
		Critical c = new Critical(np);
		
		for (int i=0; i<np; i++) {
			try {
				people[i] = new Person(c, i, i+1);
			}catch (Exception e) {
				people[i] = new Person(c, i, 0);
			}
			threads[i] = new Thread(people[i]);
			threads[i].start();
		}
	}
}

class Critical {
	
	boolean[] usage;
	
	public Critical (int n) {
		usage = new boolean[n];
		for(int i=0; i<n; i++) {
			usage[i] = true;
		}
	}
	
	public synchronized boolean giveUse(int right, int left) { //This is awful
		boolean inUse = false;
		if(usage[right] && usage[left]) {
			usage[right] = false;
			usage[left] = false;
			inUse = true;
		}
		return inUse;
	}
	
	public void stopUse(int right, int left) {
			usage[right] = true;
			usage[left] = true;
	}
}

class Person implements Runnable {
	
	Critical c;
	int right;
	int left;
	
	public Person(Critical c, int right, int left) {
		this.c = c;
		this.right = right;
		this.left = left;
	}

	@Override
	public void run() {
		while(true) {
			boolean inuse = this.c.giveUse(right, left);
			if(inuse) {
				eat();
				this.c.stopUse(right, left);
//				nap(); //why tf this was this call here if it doesnt implement anything of the sort
			}
		}
	}
	
	private void eat() {
		System.out.println("Person " + Thread.currentThread().getName() + " is eating");
		waitFor();
	}

	private void speak() {
		System.out.println("Person " + Thread.currentThread().getName() + " is speaking.");
		waitFor();
	}
	
	private void waitFor() {
		Random r = new Random();
		int t = r.nextInt(3);
		try {
			Thread.sleep(t);
		}catch (Exception e) {
			System.err.println(e);
		}
	}
}