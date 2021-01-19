import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

/**
 * JSnake made by Mate Pinter
 * 2017.07.28
 */

public class SnakeMap extends JPanel implements KeyListener, Runnable, MapConfig {

    private Snake snake;
    private Cell[][] map;
    private int size;
    private int pointSize;
    private boolean isRunning;
    private Thread gameThread;
    private int score;
    private boolean isPaused;

    public SnakeMap(int size) {
        this.pointSize = 10;
        this.isRunning = true;
        this.isPaused = false;
        this.size = size;
        this.score = 0;
        this.map = new Cell[size][size];
        init();
        addKeyListener(this);
        this.gameThread = new Thread(this, "Update");
    }

    public void init() {
        Point head = new Point(size / 2, size / 2);
        Point tail = new Point(size / 2, (size / 2) + 1);
        this.snake = new Snake(head, tail, Cell.UP);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map[x][y] = Cell.EMPTY;
            }
        }
        map[snake.getHead().x][snake.getHead().y] = Cell.HEAD;
        map[snake.getTail().x][snake.getTail().y] = snake.getDirection();
        addFood();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSnake(g);
        drawMap(g);
    }

    private void drawMap(Graphics g) {
        g.setColor(Color.GRAY);
        for (int i = 0; i <= size; i++) {
            g.drawLine(((i * pointSize) + pointSize), pointSize, (i * pointSize) + pointSize, pointSize + (pointSize * size));
        }
        for (int i = 0; i < size; i++) {
            g.drawLine(pointSize, ((i * pointSize) + pointSize), pointSize * (size + 1), ((i * pointSize) + pointSize));
        }
    }

    private void drawSnake(Graphics g) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Cell cell = map[x][y];
                if (cell != Cell.EMPTY) {
                    if (cell.isDirection()) g.setColor(Color.BLUE);
                    else if (cell == Cell.HEAD) g.setColor(Color.BLACK);
                    else if (cell == Cell.FOOD) g.setColor(Color.RED);
                    int rectX = pointSize + (x * pointSize);
                    int rectY = pointSize + (y * pointSize);
                    g.fillRect(rectX, rectY, pointSize, pointSize);
                }
            }
        }
    }

    public void step() {
        moveHead();
        boolean foundFood = false;
        Point head = snake.getHead();
        if (map[head.x][head.y] == Cell.FOOD) {
            foundFood = true;
            score += 10;
        }
        if (foundFood) {
            addFood();
        } else {
            moveTail();
            if (map[head.x][head.y] != Cell.EMPTY) {
                showNewGameDialog();
            }
        }
        map[head.x][head.y] = Cell.HEAD;
        repaint();
    }

    private void showNewGameDialog() {
        int option = JOptionPane.showConfirmDialog(null, " Score: " + score + " New game?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            this.init();
        } else {
            System.exit(0);
        }
    }

    private void moveTail() {
        Point tail = snake.getTail();
        Cell cell = map[tail.x][tail.y];
        map[tail.x][tail.y] = Cell.EMPTY;
        if (cell == Cell.UP) {
            tail.y = (tail.y > 0 ? tail.y - 1 : size - 1);
        } else if (cell == Cell.RIGHT) {
            tail.x = (tail.x < size - 1 ? tail.x + 1 : 0);
        } else if (cell == Cell.DOWN) {
            tail.y = (tail.y < size - 1 ? tail.y + 1 : 0);
        } else if (cell == Cell.LEFT) {
            tail.x = (tail.x > 0 ? tail.x - 1 : size - 1);
        }
    }

    private void moveHead() {
        Point head = snake.getHead();
        Cell direction = snake.getDirection();
        map[head.x][head.y] = snake.getDirection();
        if (direction == Cell.UP) {
            head.y = (head.y > 0 ? head.y - 1 : size - 1);
        } else if (direction == Cell.RIGHT) {
            head.x = (head.x < size - 1 ? head.x + 1 : 0);
        } else if (direction == Cell.DOWN) {
            head.y = (head.y < size - 1 ? head.y + 1 : 0);
        } else if (direction == Cell.LEFT) {
            head.x = (head.x > 0 ? head.x - 1 : size - 1);
        }
    }

    private void addFood() {
        int x;
        int y;
        do {
            x = ThreadLocalRandom.current().nextInt(0, size);
            y = ThreadLocalRandom.current().nextInt(0, size);
        } while (map[x][y] != Cell.EMPTY);
        map[x][y] = Cell.FOOD;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Cell direction = snake.getDirection();
        if (keyCode == KeyEvent.VK_UP && direction != Cell.DOWN) snake.setDirection(Cell.UP);
        else if (keyCode == KeyEvent.VK_RIGHT && direction != Cell.LEFT) snake.setDirection(Cell.RIGHT);
        else if (keyCode == KeyEvent.VK_DOWN && direction != Cell.UP) snake.setDirection(Cell.DOWN);
        else if (keyCode == KeyEvent.VK_LEFT && direction != Cell.RIGHT) snake.setDirection(Cell.LEFT);
        else if (keyCode == KeyEvent.VK_SPACE) this.step();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    public void changePause() {
        isPaused = !isPaused;
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!isPaused) {
                this.step();
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void startThread() {
        this.gameThread.start();
    }
}
