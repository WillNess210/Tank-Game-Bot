package bot;

import game.Constants;
import game.Point;
import game.Unit;

public class Action {
	private int id, type, actionType;
	private Point goal;
	public Action(int actionType) {
		this.actionType = actionType;
		this.id = -1;
		this.goal = new Point(-1, -1);
		this.type = -1;
	}
	public static final Action spawnAction(int type) {
		Action nextAction = new Action(Constants.SPAWN_ACTION);
		nextAction.type = type;
		return nextAction;
	}
	public static final Action moveAction(Unit u, Point goal) {
		Action nextAction = new Action(Constants.MOVE_ACTION);
		nextAction.id = u.getId();
		nextAction.goal = goal;
		return nextAction;
	}
	public static final Action fireAction(Unit u, Point goal) {
		Action nextAction = new Action(Constants.FIRE_ACTION);
		nextAction.id = u.getId();
		nextAction.goal = goal;
		return nextAction;
	}
	String getOutputString() {
		if(this.actionType == Constants.SPAWN_ACTION) {
			return "SPAWN " + this.type;
		}else if(this.actionType == Constants.MOVE_ACTION) {
			return "MOVE " + this.id + " " + this.goal.x + " " + this.goal.y;
		}else if(this.actionType == Constants.FIRE_ACTION) {
			return "FIRE " + this.id + " " + this.goal.x + " " + this.goal.y;
		}
		return "NA";
	}
}
