package com.test3;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class MyTankGame4 extends JFrame{

	MyPanel mp=null;
	public static void main(String[] args) {
		
		MyTankGame4 mtg=new MyTankGame4();
	}
	//���캯��
	public MyTankGame4()
	{
		mp=new MyPanel();
		//����mp�߳�
		Thread t=new Thread(mp);
		t.start();
		this.add(mp);
		//ע�����
		this.addKeyListener(mp);
		this.setSize(400,300);
		this.setVisible(true);
	}
}

//��ʾ����� 
class MyStartPanel extends JPanel
{
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
	}
}
//�ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable
{
	//����һ���ҵ�̹��
	Hero hero=null;
	//������˵�̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//����ը���ļ��� 
	Vector<Bomb> bombs=new Vector<Bomb>();
	int enSize=10;
	
	//�������ű�ը��Ҫ��ͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//���캯��
	public MyPanel()
	{	//�ҵ�̹��
		hero=new Hero(150,150);
		
		
		//��ʼ�����˵�̹��
		for(int i=0; i<enSize; i++)
		{	// ����һ������̹��
			EnemyTank  et=new EnemyTank((i+1)*50,0);
			et.setColor(0);
			et.setDirect(2);
			
			//��MyPanel�ĵ��˵�̹�����������õ��˵�̹��
			//�������˵�̹��
			et.setEts(ets);
			
			Thread t=new Thread(et);
			t.start();
			//������̹�����һ���ӵ�
			Shot s=new Shot(et.x+10, et.y+30, 2);
			//���뵽�����ӵ�����������
			et.ss.add(s);
			//�����߳�
			Thread t2=new Thread(s);
			t2.start();
			//���뵽�����
			ets.add(et);
		}
		
		//��ʼ��ͼƬ. ע��ͼƬ��λ�õ�д��ͼƬ������src ��  Class11����
		try {
			image1=ImageIO.read(new File("bomb_1.gif"));
			image2=ImageIO.read(new File("bomb_2.gif"));
			image3=ImageIO.read(new File("bomb_3.gif"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		 //��ʼ��ͼƬ,���������һ���ӵ���ըЧ�������� ͼƬ������ src ����
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	//��дpaint
	public void paint(Graphics g)
	{
		super.paint(g);
		//����ͼ��ı���ɫ
		g.fillRect(0, 0, 600, 400);
		//�����Լ���̹��
		if(hero.isLive==true)
		{
			this.drawTank(hero.getX(),hero.getY(), g, hero.direct, 1);
		}
		//ss��ȡ��ÿһ���ӵ�������
		for(int i=0; i<hero.ss.size(); i++)
		{
			Shot myShot=hero.ss.get(i);
			//���ӵ�
			if(myShot!=null&&myShot.isLive==true)
			{
				g.draw3DRect(myShot.x, myShot.y, 1, 1, false);
			}
			//ɾ�������������ӵ�
			if(myShot.isLive==false)
			{
				//��ss��ɾ�����ӵ�
				hero.ss.remove(myShot);
			}
		}
		
		
		//����ը��
		for (int i=0; i<bombs.size(); i++)
		{
			System.out.println("bombs.size()="+bombs.size());
			//ȡ��ը��
			Bomb b=bombs.get(i);
			
			if(b.life>6)
			{
				g.drawImage(image1, b.x, b.y, 30, 30, this);  // this��ʾ���ڵ�ǰ����ϻ���
			}
			else if(b.life>4)
			{
				g.drawImage(image2, b.x, b.y, 30, 30, this);  // this��ʾ���ڵ�ǰ����ϻ���
			}
			else
			{
				g.drawImage(image3, b.x, b.y, 30, 30, this);  // this��ʾ���ڵ�ǰ����ϻ���
			}
			//��Сb������ֵ
			b.lifeDown();
			//���ը��������ֵΪ0���Ͱ�ը��bombsȥ��
			 if(b.life==0)
			 {
				 bombs.remove(b);
			 }
		}
		
		//�������˵�̹��
		for(int i=0; i<ets.size(); i++)
		{
			EnemyTank et=ets.get(i);
			if(et.isLive)
			{
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				//�������˵��ӵ�
				for(int j=0; j<et.ss.size(); j++)
				{
					//ȡ���ӵ�
					Shot enemyShot=et.ss.get(j);
					if(enemyShot.isLive)
					{
						g.draw3DRect(enemyShot.x, enemyShot.y, 1, 1, false);
					}else
					{
						//������˵�̹�������ʹ�Vector���Ƴ��ӵ�
						et.ss.remove(enemyShot);
					}
				}
			}
		}
		
	}
	
	
	//���˵�̹���Ƿ������
	public void hitMe()
	{
		//ȡ��ÿһ�����˵�̹��
		for(int i=0; i<this.ets.size(); i++)
		{
			//ȡ��̹��
			EnemyTank et=ets.get(i);
			
			//ȡ��ÿһ���ӵ�
			for(int j=0; j<et.ss.size(); j++)
			{
				//ȡ���ӵ�
				Shot enemyShot=et.ss.get(j);
				this.hitTank(enemyShot, hero);
			}
		}
		
	}
	
	//�ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank()
	{
		//�ж��Ƿ���е��˵�̹��
		for(int i=0; i<hero.ss.size(); i++)
		{
			//ȡ���ӵ�
			Shot myShot=hero.ss.get(i);
			//�ж��ӵ��Ƿ���Ч
			if(myShot.isLive)
			{
				//ȡ��̹�ˣ������ж�
				for(int j=0; j<ets.size(); j++)
				{
					//ȡ��̹��
					EnemyTank et=ets.get(j);	
					if(et.isLive)
					{
						this.hitTank(myShot, et);
					}
				}
			}
		}
	}
	
	
	//дһ������ר���ж��ӵ��Է���е��˵�̹��
	public void hitTank(Shot s, Tank et)
	{
		//�жϸ�̹�˵ķ���
		switch(et.direct)
		{
		//������˵�̹�˵ķ��������ϻ�������
		case 0:
		case 2:
			if(s.x>et.x&&s.x<et.x+20&&s.y>et.y&&s.y<et.y+30)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				//����̹������
				et.isLive=false;
				//����һ��ը�������뵽Vcetor
				Bomb b=new Bomb(et.x, et.y);
				//���뵽Vector
				bombs.add(b);
			}
		case 1:
		case 3:
			if(s.x>et.x&&s.x<et.x+30&&s.y>et.y&&s.y<et.y+20)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				//����̹������
				et.isLive=false;
				//����һ��ը�������뵽Vcetor
				Bomb b=new Bomb(et.x, et.y);
				//���뵽Vector
				bombs.add(b);
			}
		}
	}
	//����̹�˵ĺ���
	public void drawTank(int x, int y, Graphics g, int direct, int type)
	{
		switch(type)
		{
		case 0:
			g.setColor(Color.cyan);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}
		// �жϷ���
		switch(direct)
		{
		case 0:   // ����
			
			//�����ҵ�̹�ˣ���ʱ����װ��һ��������
			//1����������ľ���
			//���û��ʵ���ɫ�����ƾ���
			//1��������ߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//2�������ұߵľ���
			g.fill3DRect(x+15,y, 5, 30,false);
			//3:�����м����
			g.fill3DRect(x+5, y+5,10, 20,false);
			//4:����Բ��
			g.fillOval(x+5, y+10, 10, 10);
			//5:������Ͳ
			g.drawLine(x+10, y+15, x+10, y);	
			break;
		case 1:    //  ����
			g.fill3DRect(x, y, 30, 5, false);
			//
			g.fill3DRect(x, y+15, 30, 5, false);
			//
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//
			g.fillOval(x+10, y+5, 10, 10);
			//
			g.drawLine(x+15, y+10, x+30, y+10);
			break;
		case 2: // ����
			//�����ҵ�̹�ˣ���ʱ����װ��һ��������
			//1����������ľ���
			//���û��ʵ���ɫ�����ƾ���
			//1��������ߵľ���
			g.fill3DRect(x, y, 5, 30,false);
			//2�������ұߵľ���
			g.fill3DRect(x+15,y, 5, 30,false);
			//3:�����м����
			g.fill3DRect(x+5, y+5,10, 20,false);
			//4:����Բ��
			g.fillOval(x+5, y+10, 10, 10);
			//5:������Ͳ
			g.drawLine(x+10, y+15, x+10, y+30);	
			break;
		case 3:  //����
			g.fill3DRect(x, y, 30, 5, false);
			//
			g.fill3DRect(x, y+15, 30, 5, false);
			//
			g.fill3DRect(x+5, y+5, 20, 10, false);
			//
			g.fillOval(x+10, y+5, 10, 10);
			//
			g.drawLine(x+15, y+10, x-3, y+10);
			break;
			
		}
		
		
	}
	//�����´��� a s w d
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			//���� ǰ��
			this.hero.setDirect(0);
			this.hero.moveUp();
		}
		else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			//����ǰ��
			this.hero.setDirect(1);
			this.hero.moveRight();
		}
		else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			//����ǰ��
			this.hero.setDirect(2);
			this.hero.moveDown();
		}
		else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			//����ǰ��
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
		//�ж�����Ƿ���J��
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			if(this.hero.ss.size()<=20)
			{
				this.hero.shotEnemy();
			}
		}
		//���»��ƴ���
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void run() {
		// TODO Auto-generated method stub
		//ÿ��100�������»����ӵ�
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//�ж��ҵ��ӵ��Ƿ���е���
			this.hitEnemyTank();
			//�������жϵ��˵��ӵ��Ƿ������
			this.hitMe();
		
			//���»���panel
			this.repaint();
		}
		
	}
	
}
	


