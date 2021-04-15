package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MPanel extends JPanel implements KeyListener, ActionListener{
	
	ImageIcon title = new ImageIcon("title.jpg");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon up = new ImageIcon("up.png");
	ImageIcon down = new ImageIcon("down.png");
	ImageIcon left = new ImageIcon("left.png");
	ImageIcon right = new ImageIcon("right.png");
	ImageIcon food = new ImageIcon("food.png");
	
	int len = 3;
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	String fx = "R";
	boolean isStarted =  false;
	Timer timer = new Timer(100, this);
	int foodx;
	int foody;
	Random rand = new Random();
	boolean isFailed = false;
	int score;
	
	
	
	public MPanel(){	
		initSnake();
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 25, 11); 
		g.fillRect(25,75,850,600);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 15));
		g.drawString("Len: " + len, 750,35);
		g.drawString("Score: " + score, 750, 50);
		
	
		
		if(fx == "R"){
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fx == "L"){
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fx == "U"){
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fx == "D"){
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		for(int i = 1; i < len; i++){
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
		
		if(isStarted == false){
			g.setColor(Color.WHITE);                          
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Press Space to summon your Snake!", 100, 300);
		}	
		if(isFailed == true){
			g.setColor(Color.RED);                          
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Your snake is dead!", 100, 300);
		}	
		
	}
	
	public void initSnake(){
		len = 3;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		foodx = 25 + 25 * rand.nextInt(34);
		foody = 75 + 25 * rand.nextInt(24);
		fx = "R";
		score = 0;
	
	}


	@Override
	public void keyTyped(KeyEvent e) {
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE){
			if(isFailed){
				isFailed = false;
				initSnake();
			}else{
				isStarted = !isStarted;
			}
			repaint();                    //重新画：重新调用画方法
		}else if(keyCode == KeyEvent.VK_LEFT){
			fx = "L";
		}else if(keyCode == KeyEvent.VK_RIGHT){
			fx = "R";
		}else if(keyCode == KeyEvent.VK_UP){
			fx = "U";
		}else if(keyCode == KeyEvent.VK_DOWN){
			fx = "D";
		}
	
	}


	@Override
	public void keyReleased(KeyEvent e) {
	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(isStarted && !isFailed){
			for(int i = len -1; i > 0; i--){
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			
			if(fx == "R"){
				snakex[0] = snakex[0] + 25;
				if(snakex[0] > 850 ) snakex[0] = 25;
			}else if(fx == "L"){
				snakex[0] = snakex[0] - 25;
				if(snakex[0] < 25 ) snakex[0] = 850;
			}else if(fx == "D"){
				snakey[0] = snakey[0] + 25;
				if(snakey[0] > 670 ) snakey[0] = 75;
			}else if(fx == "U"){
				snakey[0] = snakey[0] - 25;
				if(snakey[0] < 75 ) snakey[0] = 650;
			}
			if(snakex[0] == foodx && snakey[0] == foody){
				len++;
				score = score + 10;
				foodx = 25 + 25 * rand.nextInt(34);
				foody = 75 + 25 * rand.nextInt(24);
			}
			
			for(int i=1; i < len; i++){
				if(snakex[i] == snakex[0] && snakey[i] == snakey[0]){
					isFailed = true;
				}
			}
			
			repaint();
		}
		
		timer.start();
	}

}
