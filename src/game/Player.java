package game;

import java.util.HashMap;
import java.util.Map;

public class Player {
	public Map<Integer, Unit> units;
	private int id, balance;
	
	public Player(int id, int balance, Unit[] allUnits) {
		this.id = id;
		this.units = new HashMap<Integer, Unit>();
		this.load(allUnits);
		this.balance = balance;
	}
	
	public void load(Unit[] allUnits) {
		for(Unit u : allUnits) {
			if(u.getOwner() == this.id) {
				units.put(u.getId(), u);
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
