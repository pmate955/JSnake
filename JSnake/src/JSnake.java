import javax.swing.JFrame;

public class JSnake extends JFrame{
	
	private SnakeMap sm;
	
	public JSnake(){
		this.setTitle("JSnake v1.0");
		this.setSize(550, 550);
		sm = new SnakeMap(50);
		this.add(sm);
		this.addKeyListener(sm);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sm.gameThread.start();
	}

	public static void main(String[] args) {
		
		JSnake js = new JSnake();
		js.setVisible(true);
	}

}
