package eval1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex5T2 {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(System.in);
		String dir = s.next();
		String mode = s.next();
		
		switch(mode) {
		case "S":
			long t0 = System.nanoTime();
			parseSec(dir);
			System.out.printf("Sequential time: %dms", (System.nanoTime()-t0)/1000000);
			break;
		case "C":
			long t1 = System.nanoTime();
			parseCon(dir);
			while(Thread.activeCount()>1) {}
			System.out.printf("Concurrent time: %dms", (System.nanoTime()-t1)/1000000);
			break;
		default:
			System.out.println("Wrong flag");
			break;
		}
	}
	
	public static void parseSec(String dir) throws FileNotFoundException {

		File f = new File(dir);
		if (!f.exists()) { return; }
		File[] content = f.listFiles();
		for(File file : content) {
			if(!file.isDirectory()) {
				Scanner sc = new Scanner(file);
				int letters = 0;
				int words = 0;
				int lines = 0;
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					lines++;
					words += line.split(" ").length;
					letters += line.length();
				}
				System.out.printf("%s: %d letters, %d words, %d lines. %n", file.getName(), letters, words, lines);
			}
		}
	}
	
	public static void parseCon(String dir) {
		File f = new File(dir);
		if (!f.exists()) { return; }
		File[] content = f.listFiles();
		for(File file : content) {
			if(!file.isDirectory()) {
				new Thread (new ParserRunnable(file)).start();
			}
		}
	}
	
}


class ParserRunnable implements Runnable{
	
	File f;
	
	public ParserRunnable(File f) {
		super();
		this.f = f;
	}
	
	@Override
	public void run() {
		Runnable parser = new Runnable(){
			@Override
			public void run() {
				int letters = 0;
				int words = 0;
				int lines = 0;
				String line = null;
				try {
					BufferedReader sc = new BufferedReader(new FileReader(f));
					while((line = sc.readLine()) != null) {
						lines++;
						words += line.split(" ").length;
						letters += line.length();
					}
					sc.close();
				} catch (IOException e) {}
					System.out.printf("%s: %d letters, %d words, %d lines. %n", f.getName(), letters, words, lines);
			}
		};
		parser.run();
	}
}

