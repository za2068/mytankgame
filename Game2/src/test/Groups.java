package test;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
class WallGroup extends ObjectGroup
{
	Vector<Wall> Container;
	int WallSize=0;
	String File="myWall.txt";
	public WallGroup(){
		this.Container = new Vector<Wall>();
		for(int i=0;i<14;i+=1){
			Wall wall = new Wall(i*60,220,1,0);			
			Container.addElement(wall);	
			Wall wallx = new Wall(i*60+30,260,1,0);			
			Container.addElement(wallx);
		}	
		Wall wall1 = new Wall(365,570,3,0);
		Wall wall2 = new Wall(375,570,3,0);
		Wall wall3 = new Wall(415,570,3,0);
		Wall wall4 = new Wall(425,570,3,0);
		Wall wall5 = new Wall(385,560,1,0);
		Container.addElement(wall1);
		Container.addElement(wall2);
		Container.addElement(wall3);
		Container.addElement(wall4);
		Container.addElement(wall5);
	}
	public void CheckAlive(){
		for(int i=0;i<Container.size();i++){
			Wall wall = Container.get(i);
			if(wall.alive == false) 
				Container.remove(wall);
		}
	}
	public void DrawWalls(Graphics g){
		g.setColor(Color.yellow);
		for(int i=0;i<Container.size();i++){
			Container.get(i).DrawWall(g);
		}
	}	
}


class HeroGroup extends ObjectGroup//英雄坦克组类
{
	int HeroSize=1;
	Vector<Hero> Container;
	String File="myHero.txt";
	public HeroGroup()  //构造函数
	{
		super();
		Container = new Vector<Hero>();
		for(int i=0;i<HeroSize;i++){
			Hero myHero = new Hero(200,570,0,0);
			Container.addElement(myHero);
		}
	}
	public void CheckAlive() //核查所有英雄生存状态
	{
		for(int i=0;i<Container.size();i++){
			Hero hero=Container.get(i);
			if(hero.isAlive() == false){
				Container.remove(hero);
			}
		}
	}
	public void DrawHeros(Graphics g) //画出所有英雄坦克
	{
		for(int i=0;i<Container.size();i++){
			Container.get(i).DrawTank(g);
		}
	}
	
	public void CheckCollision(EnemyGroup eg,WallGroup wg){
		for(int i=0;i<this.Container.size();i++){
			Hero hero= this.Container.get(i);
			for(int j=0;j<eg.Container.size();j++){
				Enemy enemy = eg.Container.get(j);
				while(hero.x<enemy.x+hero.width && hero.x>enemy.x-hero.width
						&& hero.y<enemy.y+hero.height && hero.y>enemy.y-hero.height){
					if(hero.direction ==0) hero.y++;
					else if(hero.direction == 1) hero.y--;
					else if(hero.direction == 2) hero.x++;
					else if(hero.direction == 3) hero.x--;
				}
			}
			for(int k=0;k<wg.Container.size();k++){
				Wall wall= wg.Container.get(k);
				if(wall.direction == 2 || wall.direction == 3){
					while(wall.x-29<hero.x && hero.x <wall.x+9
							&& wall.y-29 <hero.y && hero.y <wall.y+29){
						if(hero.direction ==0) hero.y++;
						else if(hero.direction == 1) hero.y--;
						else if(hero.direction == 2) hero.x++;
						else if(hero.direction == 3) hero.x--;
					}
				}else if(wall.direction == 1 || wall.direction == 0){
					while(wall.x-29<hero.x && hero.x <wall.x+29
							&& wall.y-29 <hero.y && hero.y <wall.y+9){	
						if(hero.direction == 0) hero.y++;
						else if(hero.direction == 1) hero.y--;
						else if(hero.direction == 2) hero.x++;
						else if(hero.direction == 3) hero.x--;
					}
				}
			}
		}
	}	
}

class EnemyGroup extends ObjectGroup//敌方坦克
{
	int EnemySize = 3;
	Vector<Enemy> Container;
	String File="myEnemy.txt";
	
	public EnemyGroup(ShotGroup sg,WallGroup wg,HeroGroup hg) //构造函数
	{
		super();
		Container = new Vector<Enemy>();
		for(int i=0;i<EnemySize;i++){
			Enemy enemy = new Enemy(385*i,1,sg,wg,hg,this);
			enemy.setDirection(0);		
			Container.addElement(enemy);
		}
		for(int i=0;i<EnemySize;i++){
			Enemy e = Container.get(i);
			Thread t = new Thread(e);
			t.start();
		}
	}
	public void CheckAlive()//核查所有敌方坦克生存状态
	{
		for(int i=0;i<Container.size();i++){
			Enemy enemy=Container.get(i);
			if(enemy.isAlive() == false){
				Container.remove(enemy);
			}
		}
	}
	public void DrawEnemys(Graphics g)//画出所有敌方坦克
	{
		for(int i=0;i<Container.size();i++){
			Container.get(i).DrawTank(g);
		}
	}
	
}

class ShotGroup extends ObjectGroup//子弹数组类
{
	public static int cx;
	public static int cy;
	public static int ct;
	Vector<Shot> Container;
	String File="myBullets.txt";
	int BulletCount;
	public ShotGroup() //构造函数
	{
		super();
		Container = new Vector<Shot>();
	}
	public void DrawShots(Graphics g)
	{
		for(int i=0;i<Container.size();i++){
			Container.get(i).DrawShot(g);
		}
	}
	public void CheckAlive() //核查所有子弹生存状态
	{
		for(int i=0;i<Container.size();i++){
			Shot shot = Container.get(i);
			if(shot.isAlive() == false)
				Container.remove(shot);
		}
	}
	public void HitHeros(HeroGroup hg,BombGroup bombg) //核查是否击中英雄坦克
	{
		for(int i=0;i<Container.size();i++)
		{
			Shot shot = Container.get(i);	
			for(int j=0;j<hg.Container.size();j++){					
				HitObject(shot,hg.Container.get(j),bombg);
			}
		}
	}
	public void HitEnemys(EnemyGroup eg,BombGroup bombg)//核查是否击中敌方坦克
	{
		for(int i=0;i<Container.size();i++)
		{
			Shot shot = Container.get(i);	
			for(int j=0;j<eg.Container.size();j++){					
				HitObject(shot,eg.Container.get(j),bombg);
			}
		}
	}
	
	public void HitWalls(WallGroup wg,BombGroup bombg){
		for(int i=0;i<Container.size();i++){
			Shot shot = Container.get(i);
			for(int j=0;j<wg.Container.size();j++){
				HitObject(shot,wg.Container.get(j),bombg);
			}
		}
	}
	public void HitHome(Home myHome,BombGroup bombg){
		for(int i=0;i<Container.size();i++){
			Shot shot = Container.get(i);
			HitObject(shot,myHome,bombg);
		}
	}
		
	public void HitObject(Shot shot,MyObject ob,BombGroup bombg)//核查击中坦克实现函数
	{
		cx=ob.width;
		cy=ob.height;
		switch(ob.direction){
			case 0:
			case 1:
				break;
			case 2:
			case 3:
				if(cx != cy) { ct = cx;cx = cy;cy = ct;}
				break;
		}
		if(shot.getX() >= ob.getX()
				&& shot.getY() >= ob.getY()
				&& shot.getX() <= ob.getX()+cx
				&& shot.getY() <= ob.getY()+cy
				&& shot.getType() != ob.getType())
			{
				shot.setAlive(false);
				ob.setAlive(false);
				if(cx == cy) { 
					Bomb bomb = new Bomb(ob.getX(), ob.getY());
					bombg.Container.addElement(bomb);
					Thread p = new PlayWave("bomb.wav");
					p.start();
				} else {
					Thread p = new PlayWave("fireend.wav");
					p.start();
				}			
		}			
	}		
}

class BombGroup extends ObjectGroup//爆炸效果组类
{
	Vector<Bomb> Container;
	public BombGroup()//构造函数
	{
		Container = new Vector<Bomb>();
	}
	public void DrawBombs(Graphics g,MyPanel mp)//画出所有爆炸效果
	{
		for(int i=0;i<Container.size();i++){
			Container.get(i).DrawBomb(g,mp);
		}
	}
}

class ObjectGroup
{
	public ObjectGroup()
	{	
	}
}