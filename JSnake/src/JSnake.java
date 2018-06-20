import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class JSnake extends JFrame{
	
	private SnakeMap sm;
	
	public JSnake(){
		this.setTitle("JSnake v1.0");
		this.setSize(550, 550);
		sm = new SnakeMap(50);
		this.add(sm);
		this.addKeyListener(sm);
		this.setJMenuBar(this.createMenuBar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sm.gameThread.start();
	}

	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu jm = new JMenu("Menu");
		jm.addMenuListener(new MenuListener(){

			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				sm.setPause();
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				// TODO Auto-generated method stub
				sm.setPause();
			}
			
		});
		menu.add(jm);
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem pause = new JMenuItem("Pause");
		JMenuItem newGame = new JMenuItem("New game");
		jm.add(newGame);
		jm.add(pause);
		jm.add(quit);
		quit.addActionListener((e)->{
			sm.isRunning = false;
			this.dispose();
		});
		pause.addActionListener((e) -> {
			sm.setPause();
		});
		newGame.addActionListener((e) -> {
			sm.newGame();
		});
		return menu;
	}

	public static void main(String[] args) {
		
		JSnake js = new JSnake();
		js.setVisible(true);
	}

}
