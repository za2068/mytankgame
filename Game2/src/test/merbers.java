package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class Record
{	
	private static  FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static FileReader fr=null;
	private static BufferedReader br=null;
	HeroGroup hg;   //英雄坦克组
	EnemyGroup eg; //敌方坦克组
	ShotGroup sg;   //子弹组
	WallGroup wg;
	String File="Save.txt";  //保存读取的文件名
	public String getFile() {
		return File;
	}
	public void setFile(String file) {
		File = file;
	}
	public Record(HeroGroup hg,EnemyGroup eg,WallGroup wg,ShotGroup sg){
		this.hg=hg;
		this.eg=eg;
		this.wg=wg;
		this.sg=sg;	
	}
	public void PutRecord(){
		try {
			fw=new FileWriter(File);
			bw=new BufferedWriter(fw);			
						
			bw.write("myWalls"+"\r\n");
			bw.write(wg.Container.size()+"\r\n");
			for(int i=0; i<wg.Container.size(); i++)
			{
				Wall wall=wg.Container.get(i);
				if(wall.isAlive())
				{					
					String recode=wall.x+" "+wall.y+" "+wall.direction+" "+wall.type;					
					bw.write(recode+"\r\n");
				}
			}			
			bw.write("myHeros"+"\r\n");
			bw.write(hg.Container.size()+"\r\n");
			for(int i=0; i<hg.Container.size(); i++)
			{
				Hero hero=hg.Container.get(i);
				if(hero.isAlive())
				{					
					String recode=hero.x+" "+hero.y+" "+hero.type+" "+hero.direction;					
					bw.write(recode+"\r\n");
				}			
			}			
			bw.write("myEnemys"+"\r\n");
			bw.write(eg.Container.size()+"\r\n");			
			for(int i=0; i<eg.Container.size(); i++)
			{
				Enemy enemy=eg.Container.get(i);
				if(enemy.isAlive())
				{					
					String recode=enemy.x+" "+enemy.y+" "+enemy.speed
							+" "+enemy.type+" "+enemy.direction+" "+enemy.life;					
					bw.write(recode+"\r\n");
				}
			}			
			bw.write("myBullets"+"\r\n");
			bw.write(sg.Container.size()+"\r\n");
			for(int i=0; i<sg.Container.size(); i++)
			{
				Shot shot=sg.Container.get(i);
				if(shot.isAlive())
				{					
					String recode=shot.x+" "+shot.y+" "+shot.speed+" "+shot.type+" "+shot.direction;					
					bw.write(recode+"\r\n");
				}
			}									
		} catch (Exception e) {
			
		}finally{
			try {
				bw.close();
				fw.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	public void GetRecord(){
		hg.Container.clear();
		wg.Container.clear();
		sg.Container.clear();
		eg.Container.clear();
		//System.out.println("WallCSize"+wg.WallSize);
		try {
			fr=new FileReader(File);
			br=new BufferedReader(fr);
			
			String n="";
						
			n=br.readLine();
			n=br.readLine();
			wg.WallSize=Integer.parseInt(n);
	System.out.println("WallSize you got is "+wg.WallSize);
			for(int i=0;i<wg.WallSize;i++)
			{
				n=br.readLine();
				String []xyz=n.split(" ");   //以空格分隔字符串，得到字符串数组
					Wall wall=new Wall(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]),Integer.parseInt(xyz[2]),Integer.parseInt(xyz[3]));
					wg.Container.addElement(wall);	
			}			
			n=br.readLine();
			n=br.readLine();
			hg.HeroSize=Integer.parseInt(n);
	System.out.println("HeroSize you got is "+hg.HeroSize);
			for(int i=0;i<hg.HeroSize;i++)
			{
				n=br.readLine();
				String []xyz=n.split(" ");   //以空格分隔字符串，得到字符串数组
					Hero hero =new Hero(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1])
							,Integer.parseInt(xyz[2]),Integer.parseInt(xyz[3]));
					hg.Container.addElement(hero);	
			}			
			n=br.readLine();
			n=br.readLine();
			eg.EnemySize=Integer.parseInt(n);
	System.out.println("EnemySize you got is "+eg.EnemySize);
			for(int i=0;i<eg.EnemySize;i++)
			{
				n=br.readLine();
				String []xyz=n.split(" ");   //以空格分隔字符串，得到字符串数组
					Enemy enemy=new Enemy(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1])
							,Integer.parseInt(xyz[2]),Integer.parseInt(xyz[3])
							,Integer.parseInt(xyz[4]),Integer.parseInt(xyz[5]));
					eg.Container.addElement(enemy);	
			}					
			n=br.readLine();
			n=br.readLine();
			sg.BulletCount=Integer.parseInt(n);
	System.out.println("BulletCount you got is "+sg.BulletCount);
			for(int i=0;i<sg.BulletCount;i++)
			{
				n=br.readLine();
				String []xyz=n.split(" ");   //以空格分隔字符串，得到字符串数组
					Shot shot =new Shot(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1])
							,Integer.parseInt(xyz[2]),Integer.parseInt(xyz[3])
							,Integer.parseInt(xyz[4]));
					sg.Container.addElement(shot);	
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
	}
}


class Home extends MyObject
{
	public Home(){
		super(385,570);
		this.setType(10);
		this.setWidth(30);
		this.setHeight(30);
	}

	public void DrawHome(Graphics g){
		g.setColor(Color.red);
		g.fill3DRect(x,y,30,30,true);
	}
}


class Wall extends MyObject
{	
	public Wall(int x,int y,int direction,int type)
	{
		super(x,y);
		this.type = type;
		this.direction=direction;
		this.width=30;
		this.height=10;
		this.type =3;
	}
	public void DrawWall(Graphics g){
		switch(direction){
		case 0:case 1:g.fill3DRect(x,y,width,height,true);break;
		case 2:case 3:g.fill3DRect(x,y,height,width,true);break;			
		}
	}
	
}

class PlayWave extends Thread //播放声音类
{
	private String filename;
	public PlayWave(String wavfile) {
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
		//这是缓冲
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

class Bomb extends MyObject //爆炸效果类
{
	int life=30;
	boolean isLive = true;
	Graphics g;
	static Image image1 = null;
	static Image image2 = null;
	static Image image3 = null;
	
	public Bomb(int x,int y)//构造函数
	{
		super(x,y);
		try {
			image1=ImageIO.read(new File("src/test/bomb_1.gif"));//三个爆炸效果图片
			image2=ImageIO.read(new File("src/test/bomb_2.gif"));
			image3=ImageIO.read(new File("src/test/bomb_3.gif"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void lifeDown()//爆炸效果存活时间
	{
		if(life>0) life--;
		else this.isLive = false;
	}
	public void DrawBomb(Graphics g,MyPanel mp) //画出炸弹效果
	{
		if(life >20){
			g.drawImage(image1, x, y, 30,30 ,mp);
		} else if(life >10){
			g.drawImage(image2, x, y, 30,30,mp);
		} else if(life >0){
			g.drawImage(image2, x, y, 30,30,mp);
		}
		lifeDown();
	}
}

class Shot extends MyObject implements Runnable
{
	
	int speed=1;	
	Graphics g;
	Color color;
	boolean ThreadLock = true;
	public boolean isThreadLock() {return ThreadLock;}
	public void setThreadLock(boolean threadLock) {ThreadLock = threadLock;}
	public Color getColor() {return color;}
	public void setColor(Color color) {this.color = color;}
	public int getSpeed() {return speed;}
	public void setSpeed(int speed) {this.speed = speed;}
		
	public Shot(Tank tank)//构造函数
	{
		super(tank.x,tank.y);
		switch(tank.getDirection()){
			case 0:x=tank.getX()+14;y=tank.getY();break;
			case 1:x=tank.getX()+14;y=tank.getY()+30;break;
			case 2:x=tank.getX();y=tank.getY()+14;break;
			case 3:x=tank.getX()+30;y=tank.getY()+14;break;
		}
		this.speed = tank.speed+2;
		this.type=tank.type;
		this.direction=tank.direction;
		this.color=tank.color;
		this.setWidth(4);
		this.setHeight(4);
	}
	public Shot(int x,int y,int speed,int type,int direction){
		super(x,y);
		this.setSpeed(speed);
		this.setType(type);
		this.setDirection(direction);
		this.setWidth(4);
		this.setHeight(4);
	}

	public void DrawShot(Graphics g)  //画子弹
	{
		g.setColor(color);
		g.fill3DRect(x,y,width,height,true);
	}
	
	public void run()//线程
	{
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){
				
			}
			if(this.isThreadLock()){		
				switch(this.direction){
					case 0:y-=speed;break;
					case 1:y+=speed;break;
					case 2:x-=speed;break;
					case 3:x+=speed;break;
				}
				//System.out.println("子弹的坐标x="+x+"子弹的y坐标y="+y);
				
				if(x<0||x>800||y<0||y>600){			
					if(this.type == 0 && this.alive == true){
						Thread p = new PlayWave("fireend.wav");
						p.start();
					}
					this.alive=false;
					break;				
				}
			}			
		}
	}	
}

class Hero extends Tank//英雄坦克类
{
	public Hero(int x,int y,int type,int direction){
		super(x,y);
		//this.setLife(1);
		this.setSpeed(3);
		this.setType(type);
		this.setDirection(direction);
		this.setColor(Color.green);
	}	
}

class Enemy extends Tank implements Runnable //敌方坦克类，做成线程
{

	WallGroup wallGroup;
	HeroGroup heroGroup;
	EnemyGroup enemyGroup;
	ShotGroup shotGroup;
	int time=10;
	int times=13;
	boolean ThreadLock=true;
	public boolean isThreadLock() {return ThreadLock;}
	public void setThreadLock(boolean threadLock) {ThreadLock = threadLock;}
	public Enemy(int x,int y,ShotGroup sg,WallGroup wg,HeroGroup hg,EnemyGroup eg) //构造函数
	{
		super(x,y);
		//this.setLife(1);
		this.setSpeed(1);
		this.setType(1);
		this.setDirection(0);
		this.setColor(Color.pink);
		shotGroup = sg;
		wallGroup = wg;
		heroGroup = hg;
		enemyGroup = eg;
		
	}
	public Enemy(int x,int y,int speed,int type,int direction,int life)
	{
		super(x,y);
		this.setSpeed(speed);
		this.setType(type);
		this.setDirection(direction);
		this.setLife(life);
		this.setColor(Color.pink);
	}
	public void CheckCollision(EnemyGroup eg,HeroGroup hg,WallGroup wg){
		if(eg.Container.size() == 0) return;
		for(int j=0;j<eg.Container.size();j++){
			Enemy enemy = eg.Container.get(j);
			//enemy to hero
			if(hg.Container.size() == 0) return ;
			for(int i =0;i<hg.Container.size();i++){
				Hero hero = hg.Container.get(i);
//				while(enemy.x<hero.x+enemy.width && enemy.x>hero.x-enemy.width
//						&& enemy.y<hero.y+enemy.height && enemy.y>hero.y-enemy.height){
//					if(enemy.direction ==0) enemy.y++;
//					else if(enemy.direction == 1) enemy.y--;
//					else if(enemy.direction == 2) enemy.x++;
//					else if(enemy.direction == 3) enemy.x--;
//				}
				switch(enemy.direction){
				case 0:
					while(hero.x-30 < enemy.x && enemy.x<hero.x+30
							&& hero.y+29<enemy.y && enemy.y<hero.y+31)
						enemy.y++;
				case 1:
					while(hero.x-30 < enemy.x && enemy.x<hero.x+30
							&& hero.y-31<enemy.y && enemy.y<hero.y-29)
						enemy.y--;
				case 2:
					while(hero.x+29 < enemy.x && enemy.x<hero.x-31
							&& hero.y-30<enemy.y && enemy.y<hero.y+30)
						enemy.x++;
				case 3:
					while(hero.x-31 < enemy.x && enemy.x<hero.x-29
							&& hero.y-30<enemy.y && enemy.y<hero.y+30)
						enemy.x--;
				}
			}		
			//enmey to enemy
			for(int l=0;l<eg.Container.size();l++){
				Enemy compare = eg.Container.get(l);
				if(compare != enemy){
//					while(enemy.x<compare.x+enemy.width && enemy.x>compare.x-enemy.width
//						&& enemy.y<compare.y+enemy.height && enemy.y>compare.y-enemy.height){
//					if(enemy.direction ==0) enemy.y++;
//					else if(enemy.direction == 1) enemy.y--;
//					else if(enemy.direction == 2) enemy.x++;
//					else if(enemy.direction == 3) enemy.x--;
					switch(enemy.direction){
					case 0:
						while(compare.x-30 < enemy.x && enemy.x<compare.x+30
								&& compare.y+29<enemy.y && enemy.y<compare.y+31)
							enemy.y++;
					case 1:
						while(compare.x-30 < enemy.x && enemy.x<compare.x+30
								&& compare.y-31<enemy.y && enemy.y<compare.y-29)
							enemy.y--;
					case 2:
						while(compare.x+29 < enemy.x && enemy.x<compare.x+31
								&& compare.y-30<enemy.y && enemy.y<compare.y+30)
							enemy.x++;
					case 3:
						while(compare.x-31 < enemy.x && enemy.x<compare.x-29
								&& compare.y-30<enemy.y && enemy.y<compare.y+30)
							enemy.x--;
					}
				}				
			}	
			
			if(wg.Container.size() == 0) return ;
			for(int k=0;k<wg.Container.size();k++){
				Wall wall= wg.Container.get(k);
				if(wall.direction == 2 || wall.direction == 3){
					while(wall.x-29<enemy.x && enemy.x <wall.x+9
							&& wall.y-29 <enemy.y && enemy.y <wall.y+29){
						if(enemy.direction ==0) enemy.y++;
						else if(enemy.direction == 1) enemy.y--;
						else if(enemy.direction == 2) enemy.x++;
						else if(enemy.direction == 3) enemy.x--;
					}
				}else if(wall.direction == 1 || wall.direction == 0){
					while(wall.x-29<enemy.x && enemy.x <wall.x+29
							&& wall.y-29 <enemy.y && enemy.y <wall.y+9){	
						if(enemy.direction == 0) enemy.y++;
						else if(enemy.direction == 1) enemy.y--;
						else if(enemy.direction == 2) enemy.x++;
						else if(enemy.direction == 3) enemy.x--;
					}
				}
			}
		}	
	}

	@Override
	public void run() //线程
	{
		// TODO Auto-generated method stub
		while(this.alive == true ){
			try{
				Thread.sleep(50);
			}catch(Exception e){			
			}
			if(this.isThreadLock() ==true){				
				switch(this.direction){
					case 0:if(this.getY()>0) y-=speed;break;
					case 1:if(this.getY()<570) y+=speed;break;
					case 2:if(this.getX()>0) x-=speed;break;
					case 3:if(this.getX()<770) x+=speed;break;
				}
				this.CheckCollision(enemyGroup, heroGroup, wallGroup);
				time--;
				times--;
				if(time<0){
					time=10;
					if(  (int)(Math.random()*2) == 1  )
						this.setDirection((int)(Math.random()*4));//两次随机值保证前进占据5/8的概率
				}
				if(times<0){
					times=13;
				}		
				if(times==12)
					this.Fire(shotGroup);
			}			
		}
	}
}

class Tank extends MyObject //坦克父类
{
	int speed;
	int life=1;
	Color color;
	Graphics g;
	int lockdirection;
	
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getLockdirection() {
		return lockdirection;
	}
	public void setLockdirection(int lockdirection) {
		this.lockdirection = lockdirection;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Tank(int x,int y) //构造函数
	{
		super(x,y);
		this.setWidth(30);
		this.setHeight(30);
		this.lockdirection = 5;
	}	

	public void Move(int direc){
		this.direction =direc;
		//if(this.canmove == true){
			switch(this.direction){
				case 0:y-=speed;break;
				case 1:y+=speed;break;
				case 2:x-=speed;break;
				case 3:x+=speed;break;
			}
		//}
		if(this.type == 0){
			Thread p = new PlayWave("heromove.wav");
			p.start();
		}
	}
		
	public void Fire(ShotGroup sg) //开火
	{		
		Shot shot = new Shot(this);
		Thread t = new Thread(shot);
		t.start();
		sg.Container.addElement(shot);
		if(this.type == 0){
			Thread p = new PlayWave("firestart.wav");
			p.start();
		}
	}
	
	public void DrawTank(Graphics g) //画出坦克
	{

		g.setColor(color);
		switch(direction){
		case 0:
			g.fill3DRect(x, y, 7, 30, false);
			g.fill3DRect(x+23, y, 7, 30, false);
			for(int i=1;i<10;i++){
				g.drawLine(x, y+i*3, x+6, y+i*3);
			}
			for(int i=1;i<10;i++){
				g.drawLine(x+23, y+i*3, x+29, y+i*3);
			}
			g.fillOval(x+4, y+8, 21, 20);
			g.fill3DRect(x+14, y+2, 2, 15, true);
			g.fill3DRect(x+11, y, 8, 3, true);
			break;
		case 1:
			g.fill3DRect(x, y, 7, 30, false);
			g.fill3DRect(x+23, y, 7, 30, false);
			for(int i=1;i<10;i++){
				g.drawLine(x, y+i*3, x+6, y+i*3);
			}
			for(int i=1;i<10;i++){
				g.drawLine(x+23, y+i*3, x+29, y+i*3);
			}
			g.fillOval(x+4, y+2, 21, 20);
			g.fill3DRect(x+14, y+14, 2, 15, true);
			g.fill3DRect(x+11, y+27, 8, 3, true);
			break;
		case 2:
			g.fill3DRect(x, y, 30, 7, false);
			g.fill3DRect(x, y+23, 30, 7, false);
			for(int i=1;i<10;i++){
				g.drawLine(x+i*3, y, x+i*3, y+6);
			}
			for(int i=1;i<10;i++){
				g.drawLine(x+i*3, y+23, x+i*3, y+29);
			}
			g.fillOval(x+8, y+4, 20, 21);
			g.fill3DRect(x+2, y+14, 15, 2, true);
			g.fill3DRect(x, y+11, 3, 8, true);
			break;
		case 3:
			g.fill3DRect(x, y, 30, 7, false);
			g.fill3DRect(x, y+23, 30, 7, false);
			for(int i=1;i<10;i++){
				g.drawLine(x+i*3, y, x+i*3, y+6);
			}
			for(int i=1;i<10;i++){
				g.drawLine(x+i*3, y+23, x+i*3, y+29);
			}
			g.fillOval(x+2, y+4, 20, 21);
			g.fill3DRect(x+14, y+14, 15, 2, true);
			g.fill3DRect(x+27, y+11, 3, 8, true);
			break;
		}
	}

}

class MyObject 
{
	int x;int y;int width;int height;
	boolean alive;int type;
	int direction;
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public MyObject(int x,int y){
		this.x=x;
		this.y=y;
		this.alive = true;
	}
}


