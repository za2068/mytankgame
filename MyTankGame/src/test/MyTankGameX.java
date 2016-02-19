package test;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
public class MyTankGameX extends JFrame{
	MyPanel mp=null;
	public static void  main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGameX  myta= new MyTankGameX ();
	}
	public MyTankGameX ()
	{
		mp = new MyPanel();	
		Thread t = new Thread(mp);
		t.start();
		
		
		this.add(mp);
		this.setSize(400,300);
		
		this.addKeyListener(mp);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

class MyPanel extends JPanel implements ActionListener,KeyListener,Runnable
{
	int x=5;
	int y=5;
	Tank myHero;
	Tank myEnemy;
	Shot myShot;
	public MyPanel()
	{
		this.setBackground(Color.black);
		myHero = new Hero(200,200);
		myEnemy = new Enemy(100,100);
		
		
		myShot = new Shot(50,50,1);
		Thread t = new Thread(myShot);
		t.start();
		
		//myShot.addActionListener();
		
	}
	public void paint(Graphics g){
		super.paint(g);
		this.setSize(400,300);
		
		this.DrawTank(g, myHero);
		this.DrawTank(g, myEnemy);
		this.DrawShot(myShot,g,10,200);
	}
	
	public void DrawShot(Shot shot,Graphics g,int x,int y)
	{
		if(shot.isLive == true){
			g.fillOval(shot.x-1, shot.y-1, 5, 5);
		}
		
	}
	
	public void DrawTank(Graphics g,Tank tank)
	{
		int x=tank.getX();
		int y=tank.getY();
		switch(tank.getType()){
			case 0:g.setColor(Color.green);break;
			case 1:g.setColor(Color.pink);break;
		}
		switch(tank.getDirection()){
		case 0:
			g.fill3DRect(x,y,5,30,false);
			for(int i=1;i<10;i++){
				g.drawLine(x, y+i*3, x+3, y+i*3);
			}
			g.fill3DRect(x+15, y, 5, 30, false);
			for(int i=1;i<10;i++){
				g.drawLine(x+15, y+i*3, x+18, y+i*3);
			}
			g.fillOval(x-1,y+4,20,20);
			g.fill3DRect(x+8,y,4,15,true);
			break;
		case 1:
			g.fill3DRect(x,y,5,30,false);
			for(int i=1;i<10;i++){
				g.drawLine(x, y+i*3, x+3, y+i*3);
			}
			g.fill3DRect(x+15, y, 5, 30, false);
			for(int i=1;i<10;i++){
				g.drawLine(x+15, y+i*3, x+18, y+i*3);
			}
			g.fillOval(x-1,y+4,20,20);
			g.fill3DRect(x+8,y+15,4,15,true);
			break;
		case 2:
			g.fill3DRect(x,y,30,5,false);
			for(int i=1;i<10;i++){
				g.drawLine(x-1+i*3, y, x-1+i*3, y+5);
			}
			g.fill3DRect(x, y+15, 30, 5, false);
			for(int i=1;i<10;i++){
				g.drawLine(x-1+i*3, y+14, x-1+i*3, y+19);
			}
			g.fillOval(x+4,y-1,20,20);
			g.fill3DRect(x,y+8,15,4,true);
			break;
		case 3:
			g.fill3DRect(x,y,30,5,false);
			for(int i=1;i<10;i++){
				g.drawLine(x-1+i*3, y, x-1+i*3, y+5);
			}
			g.fill3DRect(x, y+15, 30, 5, false);
			for(int i=1;i<10;i++){
				g.drawLine(x-1+i*3, y+14, x-1+i*3, y+19);
			}
			g.fillOval(x+4,y-1,20,20);
			g.fill3DRect(x+15,y+8,15,4,true);
			break;
		}
		
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_W){
			this.myHero.setDirection(0);
			this.myHero.moveUp();
		}else if(arg0.getKeyCode() == KeyEvent.VK_S){
			this.myHero.setDirection(1);
			this.myHero.moveDown();
		}else if(arg0.getKeyCode() == KeyEvent.VK_A){
			this.myHero.setDirection(2);
			this.myHero.moveLeft();
		}else if(arg0.getKeyCode() == KeyEvent.VK_D){
			this.myHero.setDirection(3);
			this.myHero.moveRight();
		}
		
		this.repaint();
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//public void 
	
}

class Tank
{
	int x;
	int y;
	int direction=0;
	int type=0;
	int speed=3;
	int life=0;
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Tank(int x,int y){
		this.x=x;
		this.y=y;
	}	
	public void moveUp() {y-=speed;}
	public void moveDown() {y+=speed;}
	public void moveLeft() {x-=speed;}
	public void moveRight() {x+=speed;}	
}

class Hero extends Tank
{
	public Hero(int x,int y){
		super(x,y);
		this.setLife(1);
		//this.setSpeed(20);
		this.setType(0);
		this.setDirection(0);
	}
}

class Enemy extends Tank
{
	public Enemy(int x,int y){
		super(x,y);
		this.setLife(1);
		//this.setSpeed(20);
		this.setType(1);
		this.setDirection(0);
	}
}


