package game;

import java.util.Scanner;

public class Unit extends Point{
	private int id, owner, type, param1;
	public Unit(Scanner in) {
		super(-1, -1);
		this.id = in.nextInt();
		this.owner = in.nextInt();
		this.type = in.nextInt();
		this.x = in.nextInt();
		this.y = in.nextInt();
		this.param1 = in.nextInt();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getParam1() {
		return param1;
	}
	public void setParam1(int param1) {
		this.param1 = param1;
	}
	
	// Custom Funcs
	public boolean isRover() {
		return this.getType() == Constants.ROVER;
	}
	public boolean isRoverFull() {
		return this.getParam1() >= 30;
	}
	
}
