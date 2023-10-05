package eval1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex5T2p2 {

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

		List<File> files = new ArrayList<>();
		File folder = new File(dir);
		if(folder.isDirectory()) {
			File[] fileList = folder.listFiles();
			if (fileList != null) {
				for (File f : fileList) {
					if(f.isFile()) {
						files.add(f);
					}
				}
			}
		}
		ExecutorService ex = Executors.newFixedThreadPool(8);
		for (File file : files) {
			ex.execute(new FileProcessor(file));
		}
		ex.shutdown();
		System.out.println("donette");
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
				System.out.printf("%s: %d letters, %d words, %d lines. %n", file.getName(), letters, words, lines);
			}
		}
	}
	
}

class FileProcessor implements Runnable{
	
	private File file;
	
	public FileProcessor (File file){
		this.file = file;
	}

	@Override
	public void run() {
		
		System.out.println(file.getName());
		
	}
	
}
