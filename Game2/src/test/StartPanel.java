package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

class StartPanel extends JPanel implements Runnable,MouseListener,MouseMotionListener
{
	boolean ThreadLock = true;
	boolean lock1=true;
	boolean lock3=true;
	boolean lock4=true;
	boolean lock2=true;
	int count1=20;
	int count2=20;
	MyTankGameX mtg;
	boolean start = false;
	boolean exit = false;
	public boolean isThreadLock() {return ThreadLock;}
	public void setThreadLock(boolean threadLock) {ThreadLock = threadLock;}
	public boolean isLock3() {return lock3;}
	public void setLock3(boolean lock3) {this.lock3 = lock3;}
	public boolean isLock4() {return lock4;}
	public void setLock4(boolean lock4) {this.lock4 = lock4;}	
	public boolean isLock1() {return lock1;}
	public void setLock1(boolean lock1) {this.lock1 = lock1;}
	public boolean isLock2() {return lock2;}
	public void setLock2(boolean lock2) {this.lock2 = lock2;}

	
	public StartPanel(MyTankGameX MG){
		InitStartPanel(MG);					
	}
	public void InitStartPanel(MyTankGameX MG){
		mtg=MG;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setSize(800,600);
		this.setBackground(Color.BLACK);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		
		g.setColor(Color.yellow);
		Font myFont = new Font("华文新魏",Font.BOLD,30);
		g.setFont(myFont);
		
		if(lock3 ==true){
			g.setColor(Color.yellow);
			g.drawString("Start Game", 330, 250);
		}else{
			g.setColor(Color.yellow);
			g.fill3DRect(325, 215, 200, 40,false);
			g.setColor(Color.pink);
			g.drawString("Start Game", 330, 250);
			count1--;
			if(count1 < 0){
					this.setLock3(true);
					count1 = 20;
					start = true;
				}
		}
		if(lock4 == true){
			g.setColor(Color.yellow);
			g.drawString("Exit", 330, 300);
		}else{
			g.setColor(Color.yellow);
			g.fill3DRect(325, 265, 100, 40,false);
			g.setColor(Color.pink);
			g.drawString("Exit", 330, 300);
			count2--;
			if(count2 < 0){
					this.setLock4(true);
					count2 = 20;
					exit = true;
				}
		}
		if(lock1 == false) g.drawRect(325, 215, 200, 40);
		if(lock2 == false) g.drawRect(325, 265, 100, 40);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(true){
			try {
				Thread.sleep(10);
			} catch (Exception e) {
					e.printStackTrace();
			}
			if(this.isThreadLock()){			
					if(this.isThreadLock()){
						if(this.start ==true){
							this.start = false;
						mtg.StartGame();
						}
						if(this.exit == true){
							System.exit(0);
						}			
						this.repaint();
					}
			}		
		}		
	}
	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent m) {
		// TODO Auto-generated method stub
		int x= m.getX();
		int y=m.getY();
		if(x>325&&x<535&&y>215&&y<255) this.setLock1(false);
		else this.setLock1(true);
		if(x>325&&x<425&&y>265&&y<305) this.setLock2(false);
		else this.setLock2(true);
		
	}
	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
		int x= m.getX();
		int y=m.getY();
		if(x>325&&x<535&&y>215&&y<255) this.setLock3(false);
		else this.setLock3(true);
		if(x>325&&x<425&&y>265&&y<305) this.setLock4(false);
		else this.setLock4(true);
	}
	@Override
	public void mouseEntered(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent m) {

	}
	@Override
	public void mouseReleased(MouseEvent m) {
	
	}
}

class ExitPanel extends JPanel implements Runnable
{
	boolean ThreadLock = true;
	boolean win=false;
	public boolean isWin() {return win;}
	public void setWin(boolean win) {this.win = win;}
	public boolean isThreadLock() {return ThreadLock;}
	public void setThreadLock(boolean threadLock) {ThreadLock = threadLock;}
	MyTankGameX mtg;
	int time=20;
	public ExitPanel(MyTankGameX MTG){
		InitExitPanel(MTG);
	}
	public void InitExitPanel(MyTankGameX MTG){
		mtg=MTG;
		this.setBackground(Color.black);
	}
	public void paint(Graphics g){
		
		g.setColor(Color.black);
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.yellow);
		Font myFont = new Font("华文新魏",Font.BOLD,30);
		g.setFont(myFont);
		if(isWin()){
			g.drawString("YOU  WIN !", 330, 250);
		}else{
			g.drawString("GAME OVER !", 330, 250);
		}
		
		
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(this.isThreadLock()){				
					time--;
					if(time<0){
						time =20;
						mtg.ToStartPanel();
					}
			this.repaint();
			}			
		}
	}	
}

