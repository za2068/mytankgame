package com.test3;
import java.io.*;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//������������
class AePlayWave extends Thread {

	private String filename;
	public AePlayWave(String wavfile) {
		filename = wavfile;

	}

	public void run() {

		File soundFile = new File(filename);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		auline.start();
		int nBytesRead = 0;
		//���ǻ���
		byte[] abData = new byte[512];

		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}

	}

	
}


class Node
{
	int x;
	int y;
	int direct;
	public Node(int x, int y, int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	
}
//��¼�࣬ͬ�¿��Ա�����ҵ�����
class Recorder
{
	//��¼ÿ���ж��ٵ���
	private static int enNum=7;
	//�������ж��ٿ�������
	private static int myLife=3;
	//��¼�ܹ������˶��ٵ��˵�̹��
	private static int allEnEum=0;
	//���ļ��ָ���¼��
	static Vector<Node> nodes=new Vector<Node>();
	
	//�����ļ�������¼�˳�����Ϣ���뵽�ļ�
	private static  FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	
	private static Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	
	//��ɶ�ȡ
	public Vector<Node> getNodesAndEnNums()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			
			String n="";
			//��ȡ��һ��
			n=br.readLine();
			allEnEum=Integer.parseInt(n);
			while ((n=br.readLine())!=null)
			{
				String []xyz=n.split(" ");   //�Կո�ָ��ַ������õ��ַ�������
					Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]));
					nodes.add(node);	
			}
				
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return nodes;
	}
	
	
	//���������˵������͵���̹������,����
	public  void keeprRecAndEnemyTank()
	{
		try {
			//����
			fw=new FileWriter("myRecording.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnEum+"\r\n");
			//���浱ǰ�����ŵĵ��˵�̹�˵�����ͷ���
			for(int i=0; i<ets.size(); i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				if(et.isLive)
				{
					//��ľͱ���
					String recode=et.x+" "+et.y+" "+et.direct;
					//д�뵽�ļ���
					bw.write(recode+"\r\n");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				//�ر���,�ȿ��ĺ�ر�
				bw.close();
				fw.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	//���ļ��ж�ȡ����¼
	public static void getRecoring()
	{
		try {
			fr=new FileReader("d:\\myRecording.txt");
			br=new BufferedReader(fr);
			String n=br.readLine();
			allEnEum=Integer.parseInt(n);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				br.close();
				fr.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	//������ٵ���̹���������浽�ļ���
	public static void keepRecording()
	{
		try {
			//����
			fw=new FileWriter("d:\\myRecording.txt");
			bw=new BufferedWriter(fw);
			bw.write(allEnEum+"\r\n");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				//�ر���,�ȿ��ĺ�ر�
				bw.close();
				fw.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	//���ٵ��������ĵ�����
	public static void reduceEnNum()
	{
		enNum--;
	}
	//�����ҵ���������
	public static void reduceMyNum()
	{
		myLife--;
	}
	//��������˵�ʱ��
	public static void addEnNumRec()
	{
		allEnEum++;
	}
	public static int getAllEnEum() {
		return allEnEum;
	}
	public static void setAllEnEum(int allEnEum) {
		Recorder.allEnEum = allEnEum;
	}

	public   Vector<EnemyTank> getEts() {
		return ets;
	}

	public  void setEts(Vector<EnemyTank> ets) {
		this.ets = ets;
	}
}

//ը����
class Bomb
{
	//�����ҵ�������
	int x, y;
	//ը��������
	int life=9;
	boolean isLive=true;
	
	public Bomb(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	//��������ֵ
	public void lifeDown()
	{
		if(life>0)
		{
			life--;
		}
		else
		{
			this.isLive=false;
		}
	}
	
}

//�ӵ���
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed=1;
	//�Ƿ񻹻���
	boolean isLive=true;
	public Shot(int x, int y, int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				// TODO: handle exception
			}
			switch(direct)
			{
			case 0:
				//�ӵ�����
				y-=speed;
				break;
			case 1:
				x+=speed;
				break;
			case 2:
				y+=speed;
				break;
			case 3:
				x-=speed;
				break;
			}
		//	System.out.println("�ӵ�������x="+x+"�ӵ���y����y="+y);
			//�ӵ���ʱ����
			
			//�жϸ��ӵ��Ƿ�������Ե
			if(x<0||x>400||y<0||y>300)
			{
				this.isLive=false;
				break;
			}
			
		}
		
	}
}

//����һ��̹����
class Tank
{
	//b��ʾ̹�˵ĺ�����x ��������y
	int x=0;
	int y=0;
	// ���巽��  0��ʾ���� 1��ʾ�ң� 2��ʾ�� 3��ʾ��
	int direct=0;
	//̹����ɫ
	int color=0;
	//̹���ٶ�
	
	boolean isLive=true;
	int speed=5;
	public Tank(int x, int y)
	{
		this.x=x;
		this.y=y;
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
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
}
//���˵�̹��,�����߳���
class EnemyTank extends Tank implements Runnable
{
	
	int times=0;
	
	//����һ�����������Է��ʵ�MyPanel �����е�̹��
	
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	
	
	//����һ�����������Դ�ŵ��˵��ӵ�
	Vector<Shot> ss=new Vector<Shot>();
	//��������ӵ���Ӧ���ڸոմ���̹�˺͵��˵��ӵ������Ժ�ȥ���ӵ�
	public EnemyTank(int x, int y)
	{
		super(x, y);
	}
	
	//�õ�MyPanel �ĵ��˵�̹������
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets=vv;
	}
	
	//�ж��Ƿ������˱�ĵ��˵�̹��
	public boolean isTouchOtherEnemy()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//�ҵ�̹������
			//ȡ�����еĵ��˵�̹��
			for(int i=0; i<ets.size(); i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y>=et.y&&this.y<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y>=et.y&&this.y<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 1:
			//�ҵ�̹������
			//ȡ�����еĵ��˵�̹��
			for(int i=0; i<ets.size(); i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
					if(et.direct==0||et.direct==2)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y>=y&&this.y<=et.y+30)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x+30>=et.x&&this.x+30<=et.x+20&&this.y>=y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x+30>=et.x&&this.x+30<=et.x+30&&this.y+20>=et.y&&this.y+20<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 2:
			//�ҵ�̹������
			//ȡ�����еĵ��˵�̹��
			for(int i=0; i<ets.size(); i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
					if(et.direct==0||et.direct==2)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+30>=y&&this.y+30<=et.y+30)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x<=et.x+20&&this.y+30>=et.y&&this.y+30<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+30>=y&&this.y+30<=et.y+20)
						{
							return true;
						}
						if(this.x+20>=et.x&&this.x+20<=et.x+30&&this.y+30>=et.y&&this.y+30<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		case 3:
			//�ҵ�̹������
			//ȡ�����еĵ��˵�̹��
			for(int i=0; i<ets.size(); i++)
			{
				//ȡ����һ��̹��
				EnemyTank et=ets.get(i);
				//��������Լ�
				if(et!=this)
				{
					//������˵ķ��������ϻ�������
					if(et.direct==0||et.direct==2)
					{
						//�ҵ���һ��
						if(this.x>=et.x&&this.x<=et.x+20&&this.y>=y&&this.y<=et.y+30)
						{
							return true;
						}
						//�ҵ���һ��
						if(this.x>=et.x&&this.x<=et.x+20&&this.y+20>=et.y&&this.y+20<=et.y+30)
						{
							return true;
						}
					}
					if(et.direct==3||et.direct==1)
					{
						if(this.x+20>=et.x&&this.x+20<=et.x+20&&this.y>=y&&this.y<=et.y+20)
						{
							return true;
						}
						if(this.x>=et.x&&this.x<=et.x+30&&this.y+20>=et.y&&this.y+20<=et.y+20)
						{
							return true;
						}
					}
				}
			}
			break;
		}
		return b;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while (true)
		{
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}
			switch(this.direct)
			{
			case 0:
				//˵��̹�����������ƶ�
				for(int i=0; i<30; i++)
				{
					if(y>0&&!this.isTouchOtherEnemy())
					{
						y-=speed;
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case 1:
				for(int i=0; i<30; i++)
				{
					if(x<400&&!this.isTouchOtherEnemy())
					{
						x+=speed;
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case 2:
				for(int i=0; i<30; i++)
				{
					if(y<300&&!this.isTouchOtherEnemy())
					{
						y+=speed;
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case 3:
				for(int i=0; i<30; i++)
				{
					if(x>0&&!this.isTouchOtherEnemy())
					{
						x-=speed;
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
			this.times++;
			//�ж��Ƿ���Ҫ��̹�˼����µ��ӵ�
			if(times%2==0)
			{
					if(isLive)
					{
						if(ss.size()<5)
						{
					//		System.out.println("et.ss.size()"+et.ss.size());
							//û���ӵ�
							 Shot s=null;
							 //����ӵ�
							switch(direct)
							{
								case 0:
									s=new Shot(x+10,y ,0);
									ss.add(s);
									break;
								case 1:
									s=new Shot(x+30,y+10, 1);
									ss.add(s);
									break;
								case 2:
									s=new Shot(x+10,y+30, 2);
									ss.add(s);
									break;
								case 3:
									s=new Shot(x, y+10, 3);
									ss.add(s);
									break;
							}
							// �����ӵ�
							Thread t=new Thread(s);
							t.start();
						}	
						
					}
			}	
			
			
			//��̹���������һ���µķ���
			this.direct=(int)(Math.random()*4);
			
			//�жϵ��˵�̹���Ƿ�����
			
			if(this.isLive==false)
			{
				//��̹���������Ƴ��߳�
				break;
			}
			
		}
	}
	
}
//�ҵ�̹��
class Hero extends Tank
{
	 Shot s=null;
	//�����ӵ�����
	 Vector<Shot> ss=new Vector<Shot>();
	public Hero(int x, int y)
	{
		super(x,y);
	}
	public void shotEnemy()
	{
		
		switch(this.direct)
		{
		case 0:
			s=new Shot(x+10,y ,0);
			ss.add(s);
			break;
		case 1:
			s=new Shot(x+30,y+10, 1);
			ss.add(s);
			break;
		case 2:
			s=new Shot(x+10,y+30, 2);
			ss.add(s);
			break;
		case 3:
			s=new Shot(x,y+10, 3);
			ss.add(s);
			break;
		}
		//�����̲߳�����
		Thread t=new Thread(s);
		t.start();
	}
	//̹�������ƶ�
	public void moveUp()
	{
		y-=speed;
	}
	//̹�������ƶ�
	public void moveRight()
	{
		x+=speed;
	}
	//�����ƶ�
	public void moveDown()
	{
		y+=speed;
	}
	//������ƶ�
	public void moveLeft()
	{
		x-=speed;
	}
}
