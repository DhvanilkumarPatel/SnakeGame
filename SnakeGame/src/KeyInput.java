import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	int moveX = 0;
	int moveY = 0;

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			moveX = 0;
			moveY = -1;
		}
		else if (key == KeyEvent.VK_S) {
			moveX = 0;
			moveY = 1;
		}
		else if (key == KeyEvent.VK_A) {
			moveX = -1;
			moveY = 0;
		}
		else if (key == KeyEvent.VK_D) {
			moveX = 1;
			moveY = 0;
		}

		

	}
}
