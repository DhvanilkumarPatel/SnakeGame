import java.awt.Graphics;
import java.util.ArrayList;

public class Grid {

	private ArrayList<Cell> snake;
	private Cell[][] grid;
	private Game game;
	private KeyInput keyInput;
	private int velX = 1, velY = 0;

	public Grid(Game game, int rowSize, int colSize) {
		this.game = game;
		this.keyInput = game.getKeyInput();
		grid = new Cell[rowSize][colSize];
		initCells();
		resetGame();
	}

	// Initializes all cells being empty
	public void initCells() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				grid[x][y] = new Cell(x, y);
			}
		}
	}

	public void resetGame() {
		for (Cell[] col : grid) {
			for (Cell cell : col) {
				if (cell == null)
					continue;
				cell.reset();
			}
		}

		snake = new ArrayList<Cell>();
		
		int x = grid.length / 2;
		int y = grid[0].length / 2;

		grid[x][y].isSnakeHead = true;
		snake.add(grid[x][y]);
		
		game.setScore(snake.size());

		placeFood();

	}

	// Renders all cells in grid
	public void renderGrid(Graphics g, int cellSize, int dx, int dy) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				if (grid[x][y] == null)
					continue;
				grid[x][y].render(g, x * cellSize + dx, y * cellSize + dy, cellSize);
			}
		}
	}

	// Places food on an empty cell and returns that cell
	public Cell placeFood() {

		while (true) {
			int x = (int) (Math.random() * grid.length);
			int y = (int) (Math.random() * grid[0].length);

			Cell cell = grid[x][y];
			if (cell == null)
				continue;

			if (!cell.hasFood && !cell.isSnakeBody && !cell.isSnakeHead) {
				cell.hasFood = true;
				return cell;
			}
		}
	}

	// Ticks the snake
	public boolean tick() {

		if (!(keyInput.moveX == -velX || keyInput.moveY == -velY || keyInput.moveX + keyInput.moveY > 1
				|| keyInput.moveX + keyInput.moveY == 0)) {
			velX = keyInput.moveX;
			velY = keyInput.moveY;
		}

		if (snake.size() < 1)
			return false;

		Cell head = snake.get(0);

		int nX = head.x + velX;
		int nY = head.y + velY;

		if (nX < 0 || nX >= grid.length || nY < 0 || nY >= grid[0].length)
			return false;

		Cell next = grid[nX][nY];
		if (next.isSnakeBody)
			return false;

		head.isSnakeHead = false;
		head.isSnakeBody = true;
		next.isSnakeHead = true;
		snake.add(0, next);

		if (next.hasFood) {
			next.hasFood = false;
			placeFood();
		} else {
			snake.remove(snake.size() - 1).reset();
		}
		
		game.setScore(snake.size());
		if(snake.size() > game.getHighScore()) game.setHighScore(snake.size());
		
		return true;

	}

	// Returns grid
	public Cell[][] getGrid() {
		return grid;
	}

}
