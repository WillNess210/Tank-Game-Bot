package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class State {
	public Point[] sites;
	public Player[] players;
	public State(Player p1, Player p2) {
		this.players = new Player[2];
		this.players[0] = p1;
		this.players[1] = p2;
	}
	public State(Scanner in, Point[] sites, int myId) {
		int myCoins = in.nextInt();
        int opCoins = in.nextInt();
        int numUnits = in.nextInt();
        Unit[] allUnits = new Unit[numUnits];
        for(int i = 0; i < numUnits; i++){
        	allUnits[i] = new Unit(in);
        }
        this.players = new Player[2];
        this.players[0] = new Player(0, myId == 0 ? myCoins : opCoins, allUnits);
        this.players[1] = new Player(1, myId == 1 ? myCoins : opCoins, allUnits);
        this.sites = sites;
	}
	
	//
	
	public Point getToHomebase(Unit u) {
		return u.getOwner() == 0 ? new Point(100, u.y) : new Point(900, u.y);
	}
	public int getClosestSiteIndex(Unit u, ArrayList<Integer> indicesToIgnore) {
		if(this.sites.length == 0) {
			return -1;
		}
		int closestSite = -1;
		double closestDist = Double.MAX_VALUE;
		for(int i = 0; i < this.sites.length; i++) {
			if(indicesToIgnore.contains(i)) {
				continue;
			}
			double dist = u.distance(this.sites[i]);
			if(dist < closestDist) {
				closestSite = i;
				closestDist = dist;
			}
		}
		return closestSite;
	}
	public int getClosestSiteIndex(Unit u) {
		return this.getClosestSiteIndex(u, new ArrayList<Integer>());
	}
	public Point getClosestSite(Unit u) {
		return this.sites[this.getClosestSiteIndex(u)];
	}
}
