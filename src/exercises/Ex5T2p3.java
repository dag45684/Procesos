package exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ex5T2p3 {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner s = new Scanner(System.in);
		String dir = s.next();
		String mode = s.next();
		
		switch(mode) {
		case "S":
			long t0 = System.nanoTime();
			parseSec(dir);
			System.out.printf("Sequential time: %.3f ms", (System.nanoTime()-t0)/1000000d);
			break;
		case "C":
			long t1 = System.nanoTime();
			parseCon(dir);
			System.out.printf("Concurrent time: %.3f ms", (System.nanoTime()-t1)/1000000d);
			break;
		default:
			System.out.println("Wrong flag");
			break;
		}
	}
	
	public static void parseCon(String dir) {
		File[] temp = new File(dir).listFiles();
		long t0 = System.nanoTime();
		List<File> files = List.of(temp).stream().filter(e -> e.isFile()).collect(Collectors.toList()); 
		boolean bruteforce = true;
		if(bruteforce) {
			LinkedList<Thread> tl = new LinkedList<>();
			files.forEach(e -> tl.add(new Thread(new Counter(e))));
			tl.forEach(e -> e.start());
			tl.forEach(e -> {
				try {
					e.join();
				}catch (Exception err) {}
			});
			System.out.println(System.nanoTime() - t0);
		}
	}
	
	public static void parseSec(String dir) throws FileNotFoundException {
		File f = new File(dir);
		if (!f.exists()) { return; }
		File[] content = f.listFiles();
		for(File file : content) {
			if(file.isDirectory()) { continue; }
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

class Counter implements Runnable{
	
	private File file;
	
	Counter(File file){
		this.file = file;
	}

	@Override
	public void run() {
		int letters = 0;
		int words = 0;
		int lines = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while((line = br.readLine()) != null) {
				lines++;
				words += line.split(" ").length;
				letters += line.length();
			}
		}catch (Exception e) {}
		System.out.printf("%s: %d letters, %d words, %d lines. %n", file.getName(), letters, words, lines);
	}
}
