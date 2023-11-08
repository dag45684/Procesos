package semaphores;

public class Person extends Thread{
	
	private Park park;
	
	public Person(int id, Park park) {
		super("Persona " + id);
		this.park = park;
	}
	
	public void run() {
		try {
			System.out.println(getName() + " is walking");
			Thread.sleep(2000);
			System.out.println(getName() + " reaches the bench.");
			
			park.sitDown();
			park.standUp();
		}catch (Exception e) {}
	}
}
