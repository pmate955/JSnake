import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

public class SnakeMap extends JPanel implements KeyListener, Runnable{
     /**
	 * JSnake made by MAte Pinter 
	 * 2017.07.28
	 */
	private static final long serialVersionUID = 2907709694278207308L;
	private int[][] map;
     private Point head;
     private Point tail;
     private byte direction;
     private int size;
     private int pointSize = 10;
     public boolean isRunning = true;
     public Thread gameThread;
     public int score;
     
     public SnakeMap(int size){
    	 this.size = size;
    	 this.score = 0;
    	 this.map = new int[size][size];
    	 this.direction = 1;
    	 this.head = new Point(size/2, size/2);
    	 this.tail = new Point(size/2, (size/2)+1);
    	 for(int x = 0; x < size; x++){
    		 for(int y = 0; y < size; y++){
    			 map[x][y] = 0;;
    		 }
    	 }
    	 map[head.x][head.y] = 8;
    	 map[tail.x][tail.y] = direction;
    	 addKeyListener(this);
    	 addFood();
    	 gameThread = new Thread(this, "Update");
     }
     
     public void paintComponent(Graphics g){
    	 	super.paintComponent(g);
    	 	for(int y = 0; y < size; y++){
    	 		for(int x = 0; x < size; x++){
    	 			int col = map[x][y];
    	 			if(col!=0){
    	 				if(col > 0 && col <= 4) g.setColor(Color.blue);
    	 				else if(col == 8) g.setColor(Color.BLACK);
    	 				else if(col == 10) g.setColor(Color.RED);
    	 				g.fillRect(pointSize + (x*pointSize), pointSize + (y*pointSize), pointSize, pointSize);
    	 			}
    	 		}
    	 	}
    	 	g.setColor(Color.BLACK);
            for (int i=0; i<=size; i++) {
                g.drawLine(((i*pointSize)+pointSize), pointSize, (i*pointSize)+pointSize, pointSize + (pointSize*size));
            }
            for (int i=0; i<size; i++) {
                g.drawLine(pointSize, ((i*pointSize)+pointSize), pointSize*(size+1), ((i*pointSize)+pointSize));
            }
     }
     
     public void printMap(){
    	 for(int y = 0; y < size; y++){
    		 for(int x = 0; x < size; x++){
    			 System.out.print(map[x][y] + " ");
    		 }
    		 System.out.println(" ");
    	 }
     }
          
     public void step(){
    	 boolean eatSth = false;
    	 map[head.x][head.y] = direction;				//Move the head
    	 if(direction == 1){							//Go up
    		 if(head.y > 0){
    			head.y = head.y-1;
    		 } else {
    		    head.y = size-1;
    		 }
    	 } else if(direction == 2){						//Go right
    		 if(head.x<size-1){
    			 head.x++;
    		 } else {
    			 head.x = size-1;
    		 }
    	 } else if(direction == 3){						//Go down
    		 if(head.y<size-1){
    			 head.y++;
    		 } else {
    			 head.y = 0;
    		 }
    	 } else if(direction == 4){						//Go left
    		 if(head.x>0){
    			 head.x--;
    		 } else {
    			 head.x = size-1;
    		 }
    	 }
    	 if(map[head.x][head.y]==10) eatSth = true;
    	 map[head.x][head.y] = 8;						//Put the new head
    	 if(eatSth){
    		 addFood();
    		 repaint();
    		 return;
    	 }
    	 int lastDir = map[tail.x][tail.y];
    	 map[tail.x][tail.y] = 0;						//Delete the last tail
    	 if(lastDir == 1){
    		 if(tail.y > 0) tail.y--;
    		 else tail.y = size-1;
    	 } else if(lastDir == 2){
    		 if(tail.x < size-1) tail.x++;
    		 else tail.x = 0;
    	 } else if(lastDir == 3){
    		 if(tail.y < size-1) tail.y++;
    		 else tail.y = 0;
    	 } else if(lastDir == 4){
    		 if(tail.x>0) tail.x--;
    		 else tail.x = size-1;
    	 }
    	 
    	 repaint();
     }
     
     private void addFood(){
    	 int x = ThreadLocalRandom.current().nextInt(0, size);
    	 int y = ThreadLocalRandom.current().nextInt(0, size);
    	 while(map[x][y]!=0){
    		 x = ThreadLocalRandom.current().nextInt(0, size);
    		 y = ThreadLocalRandom.current().nextInt(0, size);
    	 }
    	 map[x][y] = 10;
     }
     
     public void setDirection(byte direction){
    	 this.direction = direction;
    	 if(direction == 1){
    		 System.out.println("Go Up");
    	 } else if(direction == 2){
    		 System.out.println("Go right");
    	 } else if(direction == 3){
    		 System.out.println("Go down");
    	 } else System.out.println("Go left");
     }

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(keyCode == KeyEvent.VK_UP && direction != 3) this.direction = 1;
		else if(keyCode == KeyEvent.VK_RIGHT && direction != 4) this.direction = 2;
		else if(keyCode == KeyEvent.VK_DOWN && direction != 1) this.direction = 3;
		else if(keyCode == KeyEvent.VK_LEFT && direction != 2) this.direction = 4;
		else if(keyCode == KeyEvent.VK_SPACE) this.step();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while(isRunning){
			this.step();
			try{
				Thread.sleep(250);
			} catch (Exception e){
				
			}
		}
		
	}
     
}
