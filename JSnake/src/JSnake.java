import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class JSnake extends JFrame {

    private SnakeMap snakeMap;

    public JSnake() {
        snakeMap = new SnakeMap(50);
        this.add(snakeMap);
        this.addKeyListener(snakeMap);
        this.setTitle("JSnake v1.0");
        this.setSize(550, 550);
        this.setJMenuBar(this.createMenuBar());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        snakeMap.startThread();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        addMenuListeners(menu);
        menuBar.add(menu);
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem pause = new JMenuItem("Pause");
        JMenuItem quit = new JMenuItem("Quit");
        menu.add(newGame);
        menu.add(pause);
        menu.add(quit);
        newGame.addActionListener((e) -> {
            snakeMap.init();
        });
        pause.addActionListener((e) -> {
            snakeMap.changePause();
        });
        quit.addActionListener((e) -> {
            snakeMap.setRunning(false);
            this.dispose();
        });
        return menuBar;
    }


    private void addMenuListeners(JMenu menu) {
        menu.addMenuListener(new MenuListener() {

            @Override
            public void menuCanceled(MenuEvent arg0) {
            }

            @Override
            public void menuDeselected(MenuEvent arg0) {
                snakeMap.changePause();
            }

            @Override
            public void menuSelected(MenuEvent arg0) {
                snakeMap.changePause();
            }

        });
    }

    public static void main(String[] args) {
        JSnake snake = new JSnake();
        snake.setVisible(true);
    }

}
