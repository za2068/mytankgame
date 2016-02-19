package test;



class Hero extends Tank
{
	boolean islive=true;
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
	boolean islive=true;
	public Enemy(int x,int y){
		super(x,y);
		this.setLife(1);
		//this.setSpeed(20);
		this.setType(1);
		this.setDirection(0);
	}
}

class Shot implements Runnable
{
	int x;
	int direction;
	int speed=1;
	boolean isLive=true;
	int type; //0 for myTank 1 for EnemyTank
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
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	int y;
	
	public Shot(int x,int y,int direction){
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
	public void run(){
		while(true){
			try{
				Thread.sleep(50);
			}catch(Exception e){
				
			}
			switch(this.direction){
			case 0:y-=speed;break;
			case 1:y+=speed;break;
			case 2:x-=speed;break;
			case 3:x+=speed;break;
			}
			System.out.println("子弹的坐标x="+x+"子弹的y坐标y="+y);
			
			if(x<0||x>400||y<0||y>300){
				this.isLive=false;
				break;
			}
		}
	}	
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


