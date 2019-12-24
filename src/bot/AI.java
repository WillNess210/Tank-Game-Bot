package bot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import game.State;

// TODO have rovers go to the closest point in the site to the home base, not the center of the site. DUH!

public class AI {
	private State state;
	private int myId;
	private Strategy strat;
	public AI(int myId) throws FileNotFoundException, IOException {
		this.myId = myId;
		//this.strat = new StrategyDefault(myId); // IDLE
		this.strat = new StrategyAllRover(myId); // ROVERS ONLY
	}
	public void setState(State state) {
		this.state = state;
		strat.updateState(state);
	}
	public Action[] getTurnActions() {
		return strat.getActions();
	}
	public String getAIOutput() {
		String output = "";
		Action[] actions = this.getTurnActions();
		for(Action ac : actions) {
			output += ac.getOutputString() + ",";
		}
		return output.length() == 0 ? "IDLE" : output.substring(0, output.length() - 1);
	}
}
