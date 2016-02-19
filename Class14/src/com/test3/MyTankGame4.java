/**���������⣺���̹�˲��Զ��ߵĻ����������ڲ��ѭ����get(j) д�� get(i) ��
 * 
 * 
 * ���ܣ�̹����Ϸ��3.0�汾
 * 1������̹��
 * 2:ʵ��̹�����������ƶ�
 * 3:ʵ��̹�˷���һ���ӵ�.
 * 4������������ӵ�ʵ���������䣬��ֻ��� ����J ����ʱ��ȥ��һ�������Ϳ�����
 * 5:ʵ���ӵ��򵽵���̹��̹�˻���ʧ(��������ը��Ч��)
 * 6;ʵ�ֵ���̹�˷��ӵ�������,��ֹ����̹���ص��˶�
 *   6.1���������ж��Ƿ���ײ�ĺ���д��EnemyTank
 * 7:�ֹأ�
 *  7.1����һ���յ�Panel ��Ҫ������ʾ
 *  7.2 ������˸
 *  8.����������Ϸ��ʱ����ͣ�˼���*
 *   	8.1�û������ͣ��ʱ���ӵ��ٶ���Ϊ0������̹�˷��򲻸ı�*
 *  9����¼��ҵĳɼ�
 *  	9.1���ļ����ķ�ʽ
 *  	9.2��һдһ����¼��,�����Ҽ�¼
 *  	9.3����ɱ���һ�����ٵ��˶�����̹�˵Ĺ���
 *  	9.5�����˳���Ϸ�����Լ�¼��ʱ�ĵص��ˣ������Իָ�
 */
package com.test3;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class MyTankGame4 extends JFrame implements ActionListener{

	MyPanel mp=null;
	//����һ����ʼ���
	MyStartPanel msp=null;
	//��������Ҫ�Ĳ˵�
	JMenuBar jmb=null;
	//��ʼ��Ϸ
	JMenu jm1=null;
	JMenuItem jmi1=null;
	//�˳�ϵͳ
	JMenuItem jmi2=null;
	//�����˳�
	JMenuItem jmi3=null;
	//ȥ�Ͼ�
	JMenuItem jmi4=null;
	public static void main(String[] args) {
		
		MyTankGame4 mtg=new MyTankGame4();
	}
	//���캯��
	public MyTankGame4()
	{
//		mp=new MyPanel();
//		//����mp�߳�
//		Thread t=new Thread(mp);
//		t.start();
//		this.add(mp);
//		//ע�����
//		this.addKeyListener(mp);
		
		//�����˵��Լ��˵�ѡ��
		jmb=new JMenuBar ();
		jm1=new JMenu("��Ϸ(G))");
		//���ÿ�ݷ�ʽ
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("��ʼ����Ϸ(N)");
		jmi2=new JMenuItem("�˳���Ϸ(E)");
		jmi3=new JMenuItem("�����˳���Ϸ(C)");
		jmi4=new JMenuItem("�����Ͼ���Ϸ(S)");
		
		//ע�����
		jmi4.addActionListener(this);
		jmi4.setActionCommand("conGame");
		//ע����� 
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		
		//��jmi1������Ӧ ��ʼ��Ϸ
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		
		
		//���ÿ�ݷ�ʽ
		jm1.setMnemonic('E');
		
		//��jmi1������Ӧ ��ʼ��Ϸ
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		
		//���뵽�����˵�
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		
		//�����˵����뵽�˵���
		jmb.add(jm1);
		
		
		msp=new MyStartPanel();
		Thread t=new Thread(msp);
		t.start();
		
		//����˵���
		this.setJMenuBar(jmb);
		this.add(msp);
		this.setSize(600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().equals("newgame"))
		{
			//����ս�������
			mp=new MyPanel("newGame");
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//ɾ���ɵ����msp
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾˢ��JFrame
			this.setVisible(true);
		}else if(arg0.getActionCommand().equals("exit"))
		{
			//�û�������˳�ϵͳ�Ĳ˵�
			//������ٵ���̹������
			Recorder.keepRecording();
			System.exit(0);
		}
		else if(arg0.getActionCommand().equals("saveExit"))
		{
			//�����˳�
			//���������˵������͵��˵�����
		//	System.out.println(222);
			Recorder rd=new Recorder();
			rd.setEts(mp.ets);
			rd.keeprRecAndEnemyTank();
			//�˳�
			System.exit(0);
		}else if(arg0.getActionCommand().equals("conGame"))
		{
			//ȥ�Ͼ�
			//����ս�������
			mp=new MyPanel("con");
			
			//����mp�߳�
			Thread t=new Thread(mp);
			t.start();
			//ɾ���ɵ����msp
			this.remove(msp);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾˢ��JFrame
			this.setVisible(true);
		}
	}
}

//��ʾ����� 
class MyStartPanel extends JPanel implements Runnable
{
	int times=0;
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 500, 400);
		//��ʾ��Ϣ
		if(times%2==0)
		{
			 // ����������ɫ
			g.setColor(Color.yellow);
			//��������
			Font myFont=new Font("������κ",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("stage: 1", 150, 150);
		}
		
	}

	public void run() {
		
		while(true)
		{
			//����
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			times++;
			//�ػ�
			this.repaint();
			 
		}
	}
}
//�ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable
{
	//����һ���ҵ�̹��
	Hero hero=null;
	
	//�ж���ȥ�Ͼ֣���������Ϸ
	
	//������˵�̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	//�ָ�����
	Vector<Node> nodes=new Vector<Node>();
	
	//����ը���ļ��� 
	Vector<Bomb> bombs=new Vector<Bomb>();
	int enSize=7;
	
	//�������ű�ը��Ҫ��ͼƬ
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//���캯��
	public MyPanel(String flag)
	{	
		//�ָ���¼
		Recorder.getRecoring();
		
		
		//�ҵ�̹��
		hero=new Hero(150,150);
		
		if(flag.equals("newGame"))
		{
		
			//��ʼ�����˵�̹��
			for(int i=0; i<enSize; i++)
			{	
				
				
				// ����һ������̹��
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
		}else{
			
		//	System.out.println("������");
			nodes=new Recorder().getNodesAndEnNums();
			//��ʼ�����˵�̹��
			for(int i=0; i<nodes.size(); i++)
			{	
				Node node=nodes.get(i);
				// ����һ������̹��
				EnemyTank  et=new EnemyTank(node.x,node.y);
				et.setColor(0);
				et.setDirect(node.direct);
				
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
		}
		//��ʼ��ͼƬ. ע��ͼƬ��λ�õ�д��ͼƬ������src ��  Class11����
		try {
			image1=ImageIO.read(new File("bomb_1.gif"));
			image2=ImageIO.read(new File("bomb_2.gif"));
			image3=ImageIO.read(new File("bomb_3.gif"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//���ſ�ս����
		AePlayWave apw=new AePlayWave("111.wav");
		apw.start();
		
		 //��ʼ��ͼƬ,���������һ���ӵ���ըЧ�������� ͼƬ������ src ����
//		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
//		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
//		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
	}
	
	//������ʾ��Ϣ
	public void showInfo(Graphics g)
	{
		//������ʾ��Ϣ���˵�̹��
		this.drawTank(80,330, g, 0, 0);
		//�����������ɫ
		g.setColor(Color.red);
		g.drawString(Recorder.getEnNum()+"",110, 350);
		
		//������ʾ��Ϣ�ҵ�̹�˼�¼��Ϣ
		this.drawTank(130,330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"",160, 350);
		
		//������ҵ��ܳɼ���ʾ��Ϣ
		g.setColor(Color.black);
		//��������Ĵ�С
		Font f=new Font("����",Font.BOLD,20);
		g.setFont(f);
		g.drawString("�����ܳɼ�",420 , 30);
		//������ҵ��ܹ��ݻٵ��˶���̹�˵ļ�¼
		this.drawTank(420,60, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getAllEnEum()+"",460,80);
	}
	
	
	//��дpaint
	public void paint(Graphics g)
	{
		super.paint(g);
		//����ͼ��ı���ɫ
		g.fillRect(0, 0, 400, 300);
		
			
		//������ʾ��Ϣ
		this.showInfo(g);
		
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
			//System.out.println("bombs.size()="+bombs.size());
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
				//����ҵ�̹�˻�������
				if(hero.isLive)
				{
					//�жϵ��˵��ӵ��Ƿ�����ҵ�̹��,����֮���ҵ���̹�˵������Զ�����Ϊfalse
					if(this.hitTank(enemyShot, hero))
					{
						//�ж��ҵ�̹�������Ƿ��������ڵ���2 ��  �ҵ�̹������������ �ֱ���  myLife ��ֵ�� true 3�� 2 ture,  true 1 ,false�� 
						//���Թ���̹�˵�����������������   
						if(Recorder.getMyLife()>=2)
						{
							//����������Ϊ��
							hero.isLive=true;
						}
						else
						{
							hero.isLive=false;
						}
						//��������ҵ�̹�ˣ��ҵ�̹�������ͼ�Сһ��
						Recorder.reduceMyNum();
					}
				}
				
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
						if(this.hitTank(myShot, et))
						{
							Recorder.reduceEnNum();
							Recorder.addEnNumRec();
						}
					}
				}
			}
		}
	}
	
	
	//дһ������ר���ж��ӵ��Է����̹��
	public boolean hitTank(Shot s, Tank t)
	{
		//�жϸ�̹�˵ķ���
		boolean isShotTank=false;
		switch(t.direct)
		{
		//������˵�̹�˵ķ��������ϻ�������
		case 0:
		case 2:
			if(s.x>t.x&&s.x<t.x+20&&s.y>t.y&&s.y<t.y+30)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				
				//̹������
				t.isLive=false;
				
				//������������һ��
			//	Recorder.reduceEnNum();
				//����һ��ը�������뵽Vcetor
				Bomb b=new Bomb(t.x, t.y);
				//���뵽Vector
				bombs.add(b);
				isShotTank=true;
			};
			break;
		case 1:
		case 3:
			if(s.x>t.x&&s.x<t.x+30&&s.y>t.y&&s.y<t.y+20)
			{
				//����
				//�ӵ�����
				s.isLive=false;
				//������������һ��
			//	Recorder.reduceEnNum();
				//����̹������
				t.isLive=false;
				//����һ��ը�������뵽Vcetor
				Bomb b=new Bomb(t.x, t.y);
				//���뵽Vector
				bombs.add(b);
				isShotTank=true;
			};
			break;
		}
		return isShotTank;
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
			//����ҵ�̹�˻�����
			if(hero.isLive)
			{
				if(this.hero.ss.size()<=20)
				{
					this.hero.shotEnemy();
					AePlayWave bb=new AePlayWave("2.wav");
					bb.start();
				}
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
	

