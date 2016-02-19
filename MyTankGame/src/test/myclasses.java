package test;

import java.util.Vector;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Shot implements Runnable
{
	int x;
	int y;
	int direction;
	int speed = 5;
	boolean isLive = true;
	
	public Shot(int x,int y,int direction){
		this.x=x;
		this.y=y;
		this.direction=direction;
		//this.addActionListener(mp);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Thread.sleep(50);
			} catch (Exception e){
				
			}
			switch(direction){
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
