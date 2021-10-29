import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class SPanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 450; 
	static final int SCREEN_HEIGHT = 450; 
	static final int PIXEL_SIZE = 15; 
	static final int SCREEN_SIZE = (SCREEN_WIDTH*SCREEN_HEIGHT)/PIXEL_SIZE;
	static final int DELAY = 100; 
	final int x[] = new int[SCREEN_SIZE];
	final int y[] = new int[SCREEN_SIZE];
	int snakeSize = 5; 
	int score = 0; 
	int scorex; 
	int scorey; 
	char direction = 'R';
	boolean gameRunning = false; 
	Timer timer; 
	Random random; 
	
	SPanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new KAdapter());
		startGame();
	}
	
	public void startGame() {
		newS();
		gameRunning = true; 
		timer = new Timer(DELAY,this); 
		timer.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(gameRunning) {
			g.setColor(Color.white); 
			g.fillOval(scorex, scorey, PIXEL_SIZE, PIXEL_SIZE); 
			
			for(int i = 0; i < snakeSize;i++) {
				if(i == 0) {
					g.setColor(Color.gray);
					g.fillOval(x[i], y[i], PIXEL_SIZE, PIXEL_SIZE);
				}
				else {
					g.setColor(Color.gray);
					g.fillOval(x[i], y[i], PIXEL_SIZE, PIXEL_SIZE);
				}	
			}
		}
		else {
			end(g);
		}
	}
	
	public void newS() {
		scorex = random.nextInt((int)(SCREEN_WIDTH/PIXEL_SIZE))*PIXEL_SIZE; 
		scorey = random.nextInt((int)(SCREEN_HEIGHT/PIXEL_SIZE))*PIXEL_SIZE; 
	}
	
	public void move() {
		for(int i = snakeSize; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U': 
			y[0] = y[0] - PIXEL_SIZE;
			break; 
		case 'D': 
			y[0] = y[0] + PIXEL_SIZE;
			break; 
		case 'L': 
			x[0] = x[0] - PIXEL_SIZE;
			break; 
		case 'R': 
			x[0] = x[0] + PIXEL_SIZE;
			break; 
		}
	}
	
	public void checkScore() {
		if(scorex == x[0] && scorey == y[0]) {
			snakeSize++; 
			score++;
			newS();
		}
	}
	public void checkCollision() {
		//check head with body 
		for(int i = snakeSize; i>0;i--) {
			if((x[0]==x[i])&&y[0]==y[i]) {
				gameRunning = false; 
			}
			
		}
		//check head with wall 
		if(x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
			gameRunning = false; 
		}
		if(gameRunning == false) {
			timer.stop();
		}
	}
	public void end(Graphics g) {
		Font f = new Font("Calibri", Font.BOLD, 30);
		String str1 = "Game Over";
		String str2 = "Score: " + score;
		FontMetrics m = getFontMetrics(f);
		g.setColor(Color.white);
		g.setFont(f);
		g.drawString(str1, (SCREEN_WIDTH - m.stringWidth(str1)) / 2, SCREEN_HEIGHT/2-20);
		g.drawString(str2, (SCREEN_WIDTH - m.stringWidth(str2)) / 2, SCREEN_HEIGHT/2+20);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameRunning) {
			move();
			checkScore();
			checkCollision();
		}
		repaint();
		
	}
	
	private class KAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
	
}
