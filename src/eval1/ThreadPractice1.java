package eval1;

import java.util.Iterator;

public class ThreadPractice1 {

	public static void main(String[] args) {
		
		for(int i=0; i<3; i++) {
			new t(i).start();
		}

		//Anonimous class
//		for(int i=0; i<3; i++) {
//			new Thread("Anon t " + i) {
//				@Override
//				public void run () {
//					for(int i=0; i<5; i++) {	
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException err){}
//						System.out.println(getName() + " message " + i);
//					}
//				}
//			}.start();
//		}
	}
}

class t extends Thread{
	
	public t (int n) {
		super("thread number " + n);
	}
	
	@Override
	public void run() {
		for(int i=0; i<5; i++) {	
			try {
				Thread.sleep(100);
			} catch (InterruptedException err){}
			System.out.println(getName() + " message " + i);
		}
	}
}
