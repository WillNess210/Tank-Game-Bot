package bot;

import game.State;

public interface Strategy {
	public void updateState(State state);
	public Action[] getActions();
}
