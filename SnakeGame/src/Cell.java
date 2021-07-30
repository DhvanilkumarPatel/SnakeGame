import java.awt.Color;
import java.awt.Graphics;

public class Cell {

	int x, y;

	private Color darkGreen = new Color(0, 102, 0);
	private Color lightGreen = new Color(0, 153, 0);
	private Color lightBlack = new Color(35, 35, 35);

	boolean isSnakeHead, isSnakeBody, hasFood;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Graphics g, int x, int y, int size) {

		if (isSnakeHead)
			g.setColor(lightGreen);
		else if (isSnakeBody)
			g.setColor(darkGreen);
		else
			g.setColor(Color.BLACK);

		g.fillRect(x, y, size, size);

		if (hasFood) {
			g.setColor(Color.RED);
			g.fillRect(x + size / 4, y + size / 4, size / 2, size / 2);
		}

		if (!isSnakeBody && !isSnakeHead) {
			g.setColor(lightBlack);
			g.drawRect(x, y, size, size);
		}
	}

	public void reset() {
		isSnakeHead = false;
		isSnakeBody = false;
		hasFood = false;
	}

}
