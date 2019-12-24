package bot;

import java.util.ArrayList;

import game.State;

public class StrategyDefault implements Strategy{
	int myId;
	private State state;
	public StrategyDefault(int myId) {
		this.myId = myId;
		this.state = null;
	}
	public void updateState(State state) {
		this.state = state;
	}
	public Action[] getActions() {
		if(state == null) {
			return new Action[0];
		}
		ArrayList<Action> actions = new ArrayList<Action>();
		
		return (Action[]) actions.toArray();
	}
}
