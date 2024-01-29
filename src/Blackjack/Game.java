package blackjack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
				out.println("You've joined the game.");
				
				while(running) {
					str = in.readLine();
					switch (str) {
					case "deal":
						game.repartir();
						out.println(game.toString());
						break;
					case "hit":
						game.pedir();
						out.println(game.toString());
						break;
					case "nogo":
						game.plantarse();
						out.println(game.toString());
						break;
					default:
						running = false;
						out.println("Game ended.");
						break;
					}
				}
				
			} catch (BlackjackPedirException | BlackjackRepartirException | BlackjackPlantarseException err) {
				out.println("You have no hand right now.");
			}
		}catch (Exception e) { }
		
	}



}
