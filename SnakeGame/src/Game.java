import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game extends Canvas {

	private static final long serialVersionUID = -8395759457708163217L;

	public static final int CELLSIZE = 20, ROWSIZE = 20, COLSIZE = 28;
	public static final int BOARDSIZE = 50, DX = 2 * CELLSIZE, DY = DX + BOARDSIZE;
	public static final int WIDTH = ROWSIZE * CELLSIZE + DX, HEIGHT = (COLSIZE + 1) * CELLSIZE + BOARDSIZE;

	public static final int TickT = 100, RenT = TickT / 2;

	private Color lightBlack = new Color(35, 35, 35);

	private boolean running = false;

	private int highScore = 0;
	private int score = 0;

	ScheduledExecutorService renderService;
	ScheduledExecutorService tickService;

	KeyInput keyInput;
	Grid grid;

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		new Window(this, WIDTH, HEIGHT, "Snake Game");
	}

	public void init() {

		keyInput = new KeyInput();
		this.addKeyListener(keyInput);

		grid = new Grid(this, ROWSIZE, COLSIZE);

		running = true;

		this.createBufferStrategy(3);

		renderService = Executors.newSingleThreadScheduledExecutor();
		tickService = Executors.newSingleThreadScheduledExecutor();

		renderService.scheduleWithFixedDelay(() -> renderContent(), (long) 1e8, (long) RenT * (long) 1e6,
				TimeUnit.NANOSECONDS);

		tickService.scheduleWithFixedDelay(() -> tickContent(), (long) 1e8, (long) TickT * (long) 1e6,
				TimeUnit.NANOSECONDS);

	}

	public void render() {
		renderService.execute(() -> renderContent());
	}

	public void tick() {
		tickService.execute(() -> tickContent());
	}

	public void renderContent() {

		Graphics g = this.getBufferStrategy().getDrawGraphics();

		g.setColor(lightBlack);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		grid.renderGrid(g, CELLSIZE, DX / 2, DY / 2);

		renderScores(g);

		g.dispose();
		this.getBufferStrategy().show();

	}

	public void renderScores(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(DX / 2, BOARDSIZE / 4, ((ROWSIZE - 1) * CELLSIZE) / 2, BOARDSIZE / 2);

		g.setColor(Color.black);
		g.fillRect(DX / 2 + ((ROWSIZE + 1) * CELLSIZE) / 2, BOARDSIZE / 4, ((ROWSIZE - 1) * CELLSIZE) / 2,
				BOARDSIZE / 2);

		g.setColor(Color.WHITE);
		g.drawString("SCORE: " + score, DX/2 + 10,  5 *BOARDSIZE / 8);
		
		g.drawString("HIGH SCORE: " + highScore, DX / 2 + ((ROWSIZE + 1) * CELLSIZE) / 2 + 10,  5 *BOARDSIZE / 8);
	}

	public void tickContent() {
		if (running) {
			if (!grid.tick()) {
				running = false;
				if (score > highScore) {
					highScore = score;
				}
			}
		} else {
			if (score > highScore) {
				highScore = score;
			}
			grid.resetGame();
			running = true;
		}
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public KeyInput getKeyInput() {
		return keyInput;
	}

}
