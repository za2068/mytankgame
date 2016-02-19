package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

class StartPanel extends JPanel implements MouseListener,KeyListener,MouseMotionListener,Runnable
{
	boolean ThreadLock = true;
	boolean case1=false;
	boolean ClickedStart=false;
	boolean ClickedExit=false;
	boolean Pressed1=false;
	boolean Pressed2=false;
	boolean case2=false;
	int time=1;
	int count=time;
	MyTankGameX mtg;
	boolean start = false;
	boolean exit = false;
	public boolean isThreadLock() {return ThreadLock;}
	public void setThreadLock(boolean threadLock) {ThreadLock = threadLock;}
	public boolean isClickedStart() {return ClickedStart;}
	public void setClickedStart(boolean ClickedStart) {this.ClickedStart = ClickedStart;}
	public boolean isClickedExit() {return ClickedExit;}
	public void setClickedExit(boolean ClickedExit) {this.ClickedExit = ClickedExit;}	
	public boolean isCase1() {return case1;}
	public void setCase1(boolean case1) {this.case1 = case1;}
	public boolean isCase2() {return case2;}
	public void setCase2(boolean case2) {this.case2 = case2;}
	public boolean isPressed1() {return Pressed1;}
	public void setPressed1(boolean Pressed1) {this.Pressed1 = Pressed1;}
	public boolean isPressed2() {return Pressed2;}
	public void setPressed2(boolean Pressed2) {this.Pressed2 = Pressed2;}

	
	public StartPanel(MyTankGameX MG){
		mtg=MG;
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	//	this.addKeyListener(this);
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
		
		if(Pressed1 ==false){
			g.setColor(Color.yellow);
			g.drawString("Start Game", 330, 250);
		}else{
			g.setColor(Color.yellow);
			g.fill3DRect(325, 215, 200, 40,false);
			g.setColor(Color.pink);
			g.drawString("Start Game", 330, 250);
//			count--;
//			if(count < 0){
//					this.setClickedStart(true);
//					count = time;
//					start = true;					
//				}
		}
		if(Pressed2 == false){
			g.setColor(Color.yellow);
			g.drawString("Exit", 330, 300);
		}else{
			g.setColor(Color.yellow);
			g.fill3DRect(325, 265, 100, 40,false);
			g.setColor(Color.pink);
			g.drawString("Exit", 330, 300);
//			count--;
//			if(count < 0){
//					this.setClickedExit(true);
//					count = time;
//					exit = true;				
//			}
		}
		if(case1 == true) g.drawRect(325, 215, 200, 40);
		if(case2 == true) g.drawRect(325, 265, 100, 40);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while(true){
			try {
				Thread.sleep(100);
			} catch (Exception e) {
					e.printStackTrace();
			}
			if(this.isThreadLock()){			
				if(this.start ==true){
					this.start = false;
					this.setClickedStart(false);
					this.setClickedExit(false);
					this.setPressed1(false);
					this.setPressed2(false);
					mtg.StartGame();
				}
				if(this.exit == true){
					System.exit(0);
				}			
				this.repaint();				
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
		if(x>325&&x<535&&y>215&&y<255) this.setCase1(true);
		else {
			this.setCase1(false);
			this.setPressed1(false);
		}
		if(x>325&&x<425&&y>265&&y<305) this.setCase2(true);
		else {
			this.setCase2(false);		
			this.setPressed2(false);
		}
	}
	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
//		int x= m.getX();
//		int y=m.getY();
//		if(x>325&&x<535&&y>215&&y<255) this.setClickedStart(true);
//		else this.setClickedStart(false);
//		if(x>325&&x<425&&y>265&&y<305) this.setClickedExit(true);
//		else this.setClickedExit(false);
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
		int x= m.getX();
		int y=m.getY();
		if(x>325&&x<535&&y>215&&y<255) this.setPressed1(true);
		else this.setPressed1(false);
		if(x>325&&x<425&&y>265&&y<305) this.setPressed2(true);
		else this.setPressed2(false);
	}
	@Override
	public void mouseReleased(MouseEvent m) {
		int x= m.getX();
		int y=m.getY();
		if(x>325&&x<535&&y>215&&y<255) this.start = true;
		else this.start = false;
		if(x>325&&x<425&&y>265&&y<305) this.exit =true;
		else this.exit = false;
	}
	@Override
	public void keyPressed(KeyEvent arg0) {	
		System.out.println("you get ");
		if(arg0.getKeyCode()==KeyEvent.VK_UP){
			System.out.println("you get up");
			this.setCase1(true);
			this.setCase2(false);
		}
		if(arg0.getKeyCode()==KeyEvent.VK_DOWN){
			System.out.println("you get down");
			this.setCase1(false);
			this.setCase2(true);
		}
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
			if(this.isCase1()) {
				this.setPressed1(true);
				this.start =true;
			}
			if(this.isCase2()) {
				this.setPressed2(true);
				this.exit = true;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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

