package zeldaminiclone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blocks extends Rectangle{

	public Blocks (int x, int y) {
		super(x,y,32,32);
	}
	
	public void render(Graphics g) {

		g.drawImage(Spritesheet.tileWall, x, y, 32, 32, null);
	}
}
