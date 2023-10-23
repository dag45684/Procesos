package eval1;

public class SmokeResources {
	
	private Cigars thing1;
	private Cigars thing2;
	
	public synchronized void get(Cigars thing) throws InterruptedException {
		while(thing == thing1 || thing == thing2) {
			wait();
		}
		thing1 = thing2 = null;
		notifyAll();
	}
	
	public synchronized void back(Cigars thing1, Cigars thing2) throws InterruptedException {
		while(this.thing1 != null && this.thing2 != null) {
			wait();
		}
		this.thing1 = thing1;
		this.thing2 = thing2;
		notifyAll();
	}
	

}
