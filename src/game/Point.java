package game;

import java.util.Scanner;

public class Point extends java.awt.Point{
	public Point(Scanner in) {
		super(in.nextInt(), in.nextInt());
	}
	public Point(int x, int y) {
		super(x, y);
	}
	public Point(Point b) {
		super(b.x, b.y);
	}
}
