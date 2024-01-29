package blackjack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;

import org.fp.dam.naipes.blackjack.Blackjack;
import org.fp.dam.naipes.blackjack.BlackjackPedirException;
import org.fp.dam.naipes.blackjack.BlackjackPlantarseException;
import org.fp.dam.naipes.blackjack.BlackjackRepartirException;

public class Game implements Runnable {
	
	private Socket s;

	public Game (Socket s) {
		this.s = s;
	}
	
	public void run() {

		Blackjack game = new Blackjack();
		String str;
		boolean running = true;
		
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			try {
				while(running) {
					str = in.readLine();
					if (str == null) continue;
					System.out.println(str);
					switch (str) {
					case "deal":
						game.repartir();
						System.out.println(game.toString());
						out.println(game.toString().replaceAll("\\n", "<-->"));
						out.flush();
						break;
					case "hit":
						game.pedir();
						System.out.println(game.toString());
						out.println(game.toString().replaceAll("\\n", "<-->"));
						out.flush();
						break;
					case "nogo":
						game.plantarse();
						System.out.println(game.toString());
						out.println(game.toString().replaceAll("\\n", "<-->"));
						out.flush();
						break;
					default:
						if (str.contains("nueva:")) {
							MessageDigest md = MessageDigest.getInstance("MD5");
							byte[] hash = md.digest((str.replaceAll("nueva:", "")+System.currentTimeMillis()).getBytes());
							StringBuilder r = new StringBuilder();
							for(byte b : hash) r.append(String.format("%02X", b));
							out.println("OK#"+r.toString());
							out.flush();
						}else {
							running = false;
							out.println("Game ended.");
							out.flush();
							break;
						}
					}
				}
				
			} catch (BlackjackPedirException | BlackjackRepartirException | BlackjackPlantarseException err) {
				System.out.println(err);
				out.println("You have no hand right now.");
			}
		}catch (Exception e) { }
		
	}



}
