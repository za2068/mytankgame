package test;

import java.awt.*;

import javax.swing.*;

import java.util.Vector;
import java.awt.event.*;
import java.util.Vector;
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

class MyPanel extends JPanel implements KeyListener,Runnable
{
	int x=5;
	int y=5;
	Tank myHero;
	Tank myEnemy;
	Vector<Shot> ShotContainer = new Vector<Shot>();
	Vector<Enemy> EnemyContainer = new Vector<Enemy>();
	public MyPanel()
	{
		this.setBackground(Color.black);
		myHero = new Hero(200,200);	
	}
	public void paint(Graphics g){
		super.paint(g);
		this.setSize(400,300);
		
		int EnemySize = 3;
		for(int i=0;i<EnemySize;i++){
			Enemy myEnemy = new Enemy(50*i,0);
			myEnemy.setDirection(1);
			EnemyContainer.addElement(myEnemy);
		}
		
		this.DrawTank(g, myHero);
		
		for(int i=0;i<EnemyContainer.size();i++){
			Enemy enemy = EnemyContainer.get(i);
			if(enemy.islive == false){
				EnemyContainer.remove(enemy);
			}
		}
		for(int i=0;i<EnemyContainer.size();i++){
			this.DrawTank(g, EnemyContainer.get(i));
		}
		
		//以下删除死亡的子弹，在绘制存在的子弹
		for(int i=0;i<ShotContainer.size();i++){
			Shot shot = ShotContainer.get(i);
			if(shot.isLive == false){
				ShotContainer.remove(shot);
			}
		}		
		for(int i=0;i<ShotContainer.size();i++){
			Shot shot=ShotContainer.get(i);
			DrawShot(g,shot.getX(),shot.getY());
		}
	}
	
	public void DrawShot(Graphics g,int x,int y)
	{
		g.fill3DRect(x,y,5,5,true);
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
		}else if(arg0.getKeyCode() == KeyEvent.VK_J){
			this.Fire(myHero);
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
		while(true){
		this.repaint();
		}
	}

	//public void Fire(int x,int y,int direction){
	public void Fire(Tank tank){
		int speed=5;
		if(tank.type==0){    //设置子弹速度
			speed = 5;
		}
		switch(tank.getDirection()){
		case 0:x=tank.getX()+8;y=tank.getY();break;
		case 1:x=tank.getX()+8;y=tank.getY()+30;break;
		case 2:x=tank.getX();y=tank.getY()+8;break;
		case 3:x=tank.getX()+30;y=tank.getY()+8;break;
		}
		Shot shot = new Shot(x,y,tank.getDirection());
		shot.setSpeed(speed);
		Thread t = new Thread(shot);
		t.start();
		this.ShotContainer.add(shot);		
	}
	
}

