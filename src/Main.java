import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import bot.AI;
import game.Point;
import game.State;
import game.Unit;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner in = new Scanner(System.in);
        // INIT
        int myId = in.nextInt();
		int numSites = in.nextInt();
		Point[] sites = new Point[numSites];
        for(int i = 0; i < numSites; i++){
        	sites[i] = new Point(in);
        }
        // GAME START
        AI ai = new AI(myId);
        // FOR EACH TURN
        while(true){
        	State state = new State(in, sites, myId);
        	ai.setState(state);
        	String aiCommand = ai.getAIOutput();
            System.out.println(aiCommand);
        }
    }
}
