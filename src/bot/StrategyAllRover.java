package bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import game.Constants;
import game.Player;
import game.Point;
import game.State;
import game.Unit;
import tools.BotParameters;

public class StrategyAllRover implements Strategy{
	//TUNING CONSTANTS
	final int STOP_SPAWNING_AT = 125;
	//
	int myId, turn, maxNeededRovers;
	private State state;
	private BotParameters params;
	public StrategyAllRover(int myId) throws FileNotFoundException, IOException {
		this.myId = myId;
		this.state = null;
		this.turn = 0;
		this.maxNeededRovers = 8;
		this.params = new BotParameters();
	}
	public void updateState(State state) {
		if(this.state == null) {
			this.maxNeededRovers = state.sites.length * this.params.getInt("rovers_per_site");
			System.err.println("Only need " + this.maxNeededRovers + " rovers.");
		}
		this.state = state;
		this.turn++;
	}
	Player getMyPlayer() {
		return this.state.players[this.myId];
	}
	
	int getNumMyUnits() {
		return this.getMyPlayer().units.size();
	}
	
	public Action[] getActions() {
		if(state == null) {
			return new Action[0];
		}
		ArrayList<Action> actions = new ArrayList<Action>();
		
		// ROVER ACTIONS
		Map<Unit, Point> roverAssignments = this.getSiteAssignments();
		for(Unit u : this.getMyPlayer().units.values()) {
			if(u.isRover()) {
				if(u.isRover() && u.isRoverFull()) {
					actions.add(Action.moveAction(u, this.state.getToHomebase(u)));
				}else {
					if(roverAssignments.containsKey(u)) {
						actions.add(Action.moveAction(u, roverAssignments.get(u)));
					}else {
						System.err.println("0 - this should be unreachable.");
					}
				}
			}
			
		}
		
		// SPAWN ACTIONS
		if(this.shouldSpawnRovers()) {
			int nSpawned = 0;
			for(int i = this.getMyPlayer().getBalance(); i >= 25 && this.getNumMyUnits() + nSpawned < this.maxNeededRovers; i -= 25) {
				actions.add(Action.spawnAction(Constants.ROVER));
				nSpawned++;
			}
		}
		
		// FORMATTING FOR RETURN
		Action[] toReturn = new Action[actions.size()];
		for(int i = 0; i < actions.size(); i++) {
			toReturn[i] = actions.get(i);
		}
		return toReturn;
	}
	
	
	Action getRoverAction(Unit u) {
		if(u.isRoverFull()) {
			return Action.moveAction(u, this.state.getToHomebase(u));
		}
		return Action.moveAction(u, this.state.getClosestSite(u));
	}
	
	boolean shouldSpawnRovers() {
		return this.turn <= this.STOP_SPAWNING_AT && this.getNumMyUnits() < this.maxNeededRovers;
	}
	
	public Map<Unit, Point> getSiteAssignments(){
		int[] assignmentTracker = new int[this.state.sites.length];
		for(int i = 0; i < assignmentTracker.length; i++) {
			assignmentTracker[i] = 0;
		}
		ArrayList<Unit> units = new ArrayList<Unit>(this.getMyPlayer().units.values());
		for(int i = units.size() - 1; i >= 0; i--) {
			if(units.get(i).isRover() && units.get(i).isRoverFull()) {
				units.remove(i);
			}
		}
		ArrayList<Integer> targetIndex = new ArrayList<Integer>();
		ArrayList<Double> distToTarget = new ArrayList<Double>();
		Map<Unit, Point> assignments = new HashMap<Unit, Point>();
		for(int i = 0; i < units.size(); i++) {
			targetIndex.add(this.state.getClosestSiteIndex(units.get(i)));
			distToTarget.add(units.get(i).distance(this.state.sites[targetIndex.get(i)]));
		}
		ArrayList<Integer> siteIndexToIgnore = new ArrayList<Integer>();
		while(units.size() > 0) {
			// SELECT CLOSEST
			int selectedIndex = 0;
			Unit closest = units.get(selectedIndex);
			double closestDist = distToTarget.get(selectedIndex);
			for(int i = 1; i < units.size(); i++) {
				if(distToTarget.get(i) < closestDist) {
					selectedIndex = i;
					closestDist = distToTarget.get(selectedIndex);
					closest = units.get(selectedIndex);
				}
			}
			// ASSIGN
			int siteIndex = targetIndex.get(selectedIndex);
			if(siteIndex == -1) {
				assignments.put(units.get(selectedIndex), this.state.getClosestSite(units.get(selectedIndex)));
				units.remove(selectedIndex);
				targetIndex.remove(selectedIndex);
				distToTarget.remove(selectedIndex);
				continue;
			}
			assignments.put(closest, this.state.sites[targetIndex.get(selectedIndex)]);
			units.remove(selectedIndex);
			targetIndex.remove(selectedIndex);
			distToTarget.remove(selectedIndex);
			// UPDATE & RE-TARGET
			assignmentTracker[siteIndex]++;
			if(assignmentTracker[siteIndex] >= 4) { // reassign all bad
				siteIndexToIgnore.add(siteIndex);
				for(int i = 0; i < units.size(); i++) {
					if(siteIndexToIgnore.contains(targetIndex.get(i))) {
						int newTargetIndex = (this.state.getClosestSiteIndex(units.get(i), siteIndexToIgnore));
						double newDistToTarget = (units.get(i).distance(this.state.sites[targetIndex.get(i)]));
						targetIndex.set(i, newTargetIndex);
						distToTarget.set(i, newDistToTarget);
					}
				}
			}
		}
		return assignments;
	}
}
