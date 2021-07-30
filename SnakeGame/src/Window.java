import javax.swing.JFrame;

public class Window {
	
	JFrame frame;
	Game game;
	
	public Window(Game game, int width, int height, String name) {
		
		// Adjustment for differance in frame and canvas size
		width += 14;
		// Should be 37 but 36 helps with drawing to screen
		height += 36; 
		
		
		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		
		System.out.println(frame.getSize());
		System.out.println(game.getSize());
		
		Thread t = new Thread(() -> game.init());
		t.start();
	}

}
