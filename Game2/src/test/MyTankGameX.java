package test;

import java.awt.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;

public class MyTankGameX extends JFrame implements ActionListener{
	MyPanel mp=null;
	StartPanel startmp=null;
	ExitPanel exitmp=null;
	
	JMenuBar jmb=null;
	JMenu jm1=null;
	JMenuItem jmStart=null;
	JMenuItem jmOpen=null;
	JMenuItem jmSave=null;
	JMenuItem jmSaveAs=null;
	JMenuItem jmExit=null;
	JMenuItem jmPause=null;
	boolean FirstStart = true;  //是否为第一次启动，第一次启动会写入第一关数据到map1.txt
	boolean PauseToStart=false; //暂停，开始的控制开关
	String GameFile="map1.txt"; //默认开始游戏读取文件
	
	public static void  main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGameX  myta= new MyTankGameX ();
	}
	public MyTankGameX (){
		
		jmb = new JMenuBar();
		
		jm1 = new JMenu("游戏（G）");
		jm1.setMnemonic('G');
		jmStart = new JMenuItem("开始新游戏（N）");
		jmExit = new JMenuItem("退出游戏(E)");
		jmOpen = new JMenuItem("打开游戏（O）");
		jmSaveAs = new JMenuItem("另存为（A）");
		jmSave = new JMenuItem("保存（S）");
		jmPause = new JMenuItem("暂停（P）");
		
		jmStart.addActionListener(this);
		jmStart.setActionCommand("StartGame");
		jmOpen.addActionListener(this);
		jmOpen.setActionCommand("Open");
		jmPause.addActionListener(this);
		jmPause.setActionCommand("Pause");
		jmSave.addActionListener(this);
		jmSave.setActionCommand("Save");
		jmSave.addActionListener(this);
		jmSaveAs.addActionListener(this);
		jmSaveAs.setActionCommand("SaveAs");
		jmExit.addActionListener(this);
		jmExit.setActionCommand("Exit");
				
		jm1.add(jmStart);		
		jm1.add(jmOpen);
		jm1.add(jmPause);
		jm1.add(jmSave);
		jm1.add(jmSaveAs);
		jm1.addSeparator();
		jm1.add(jmExit);
		
		jmb.add(jm1);
		
		this.setJMenuBar(jmb);
		jm1.addActionListener(this);
		
		mp = new MyPanel(this);	    //游戏主界面面板	
		exitmp = new ExitPanel(this); //游戏结束界面
		startmp = new StartPanel(this); //游戏开始界面
		GamePause();
		startmp.setThreadLock(false);	//先锁上游戏结束和开始界面线程，只启动主界面线程
		exitmp.setThreadLock(false);	//
		
		Thread g = new Thread(mp);	
		g.start();		
		Thread e = new Thread(exitmp);		
		e.start();	
		Thread s = new Thread(startmp);
		s.start();
				
		this.setSize(816,663);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		ToStartPanel();		
	}
	// 启动顺序
	//
	public void ToStartPanel(){	
		System.out.println("ToStartPanel");
		exitmp.setThreadLock(false);
		try {
			this.remove(exitmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		startmp.setThreadLock(true);
		
		this.add(startmp);	
		this.setVisible(true);
	}
	public void StartGame(){
		System.out.println("StartGame");
		//GamePause();		
		startmp.setThreadLock(false);
		exitmp.setThreadLock(false);
		if(FirstStart){
			mp.myRecord.setFile("map1.txt");
			mp.myRecord.PutRecord();
			mp.myRecord.setFile("Save.txt");
		} else {
			ReStartGame(GameFile);	
		}
		FirstStart=false;
		try {
			this.remove(startmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.remove(exitmp);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		this.add(mp);
		this.addKeyListener(mp);	//加入按键监听(WSAD:为上下左右;J为开火)
		GameStart();
		this.setVisible(true);
		Thread p = new PlayWave("start.wav");
		p.start();
	}
	public void GameOver(){
		System.out.println("GameOver");
		if(mp.enemyGroup.Container.size()==0) {
			exitmp.setWin(true);
		}
		else exitmp.setWin(false);
		GamePause();
		this.remove(mp);
		this.removeKeyListener(mp);
		this.add(exitmp);
		exitmp.setThreadLock(true);
		this.setVisible(true);
	}
	public void GamePause(){
		System.out.println("GemePause");
		for(int i=0;i<mp.enemyGroup.Container.size();i++){
			mp.enemyGroup.Container.get(i).setThreadLock(false);
		}
		for(int i=0;i<mp.shotGroup.Container.size();i++){
			mp.shotGroup.Container.get(i).setThreadLock(false);
		}
		mp.setThreadLock(false);
	}
	public void GameStart(){
		System.out.println("GemeStart");
		for(int i=0;i<mp.enemyGroup.Container.size();i++){
			mp.enemyGroup.Container.get(i).setThreadLock(true);
		}
		for(int i=0;i<mp.shotGroup.Container.size();i++){
			mp.shotGroup.Container.get(i).setThreadLock(true);
		}
		mp.setThreadLock(true);
	}
	public void ReStartGame(String GameFile){
		GamePause();
		mp.myHome.setX(385);mp.myHome.setY(570);
		mp.myRecord.setFile(GameFile);
		mp.myRecord.GetRecord();
		mp.myRecord.setFile("Save.txt");
		for(int i=0;i<mp.enemyGroup.Container.size();i++){
			Enemy enemy = mp.enemyGroup.Container.get(i);
			enemy.enemyGroup = mp.enemyGroup;
			enemy.wallGroup = mp.wallGroup;
			enemy.shotGroup = mp.shotGroup;
			enemy.heroGroup	= mp.heroGroup;
			Thread t = new Thread(enemy);
			t.start();
		}
		for(int i=0;i<mp.shotGroup.Container.size();i++){
			Thread t = new Thread(mp.shotGroup.Container.get(i));
			t.start();
		}
		GameStart();
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		if(a.getActionCommand() == "StartGame"){
			this.removeKeyListener(mp);
			StartGame();
		}
		if(a.getActionCommand() == "Save"){
			mp.myRecord.PutRecord();
		}
		if(a.getActionCommand() == "Exit"){
			System.exit(0);
		}
		if(a.getActionCommand() == "Pause"){
			if(PauseToStart == false){
				GamePause();
				this.jmPause.setText("开始(P)");
				PauseToStart = true;
			}else{
				GameStart();
				this.jmPause.setText("暂停（P）");
				PauseToStart = false;
			}			
		}
		if(a.getActionCommand() == "Open"){
			JFileChooser file = new JFileChooser(".");
			file.setAcceptAllFileFilterUsed(false);
			//file.addChoosableFileFilter(new );
			int result = file.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				this.removeKeyListener(mp);
				System.out.println(file.getSelectedFile().getName());
				if(!startmp.isThreadLock() || !exitmp.isThreadLock()){
					this.GameFile = file.getSelectedFile().getName();
					StartGame();
					this.GameFile = "map1.txt";
				}else{				
					ReStartGame(file.getSelectedFile().getName());
				}			
			}else{
				System.out.println("您选择了取消");
			}
		}
		if(a.getActionCommand() == "SaveAs"){
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt","txt");
			JFileChooser file = new JFileChooser();
			file.setFileFilter(filter);
			int result = file.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				mp.myRecord.setFile(file.getSelectedFile().getParentFile()+"\\"
						+ file.getSelectedFile().getName()+".txt");
				mp.myRecord.PutRecord();
				mp.myRecord.setFile("save.txt");
			}else{
				System.out.println("您选择了取消");
			}
		}
	}
}

class MyPanel extends JPanel implements KeyListener,Runnable
{
	boolean ThreadLock = true;
	public boolean isThreadLock() {return ThreadLock;}
	public void setThreadLock(boolean threadLock) {ThreadLock = threadLock;}

	int MyPanelWidth=800;
	int MyPanelHeight=600;
	int time =20;
	
	MyTankGameX mtg;
	HeroGroup heroGroup;   //英雄坦克组
	EnemyGroup enemyGroup; //敌方坦克组
	ShotGroup shotGroup;   //子弹组
	BombGroup bombGroup;   //坦克爆炸效果组
	WallGroup wallGroup;
	Home myHome;
	Record myRecord;
		
		
	public MyPanel(MyTankGameX MTG){	
		InitMyPanel(MTG);
	}
	
	public void InitMyPanel(MyTankGameX MTG){
		mtg=MTG;
		this.setBackground(Color.black);
		heroGroup = new HeroGroup();		
		shotGroup = new ShotGroup();
		bombGroup = new BombGroup();
		wallGroup = new WallGroup();
		enemyGroup = new EnemyGroup(shotGroup,wallGroup,heroGroup);
		myHome = new Home();
		myRecord = new Record(heroGroup,enemyGroup,wallGroup,shotGroup);		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.setSize(MyPanelWidth,MyPanelHeight);       //设置大小
		
		heroGroup.CheckAlive();      //核查英雄坦克是否存活
		heroGroup.DrawHeros(g);      //画出英雄坦克
		enemyGroup.CheckAlive();     //核查敌方坦克是否存活
		enemyGroup.DrawEnemys(g);    //画出敌方坦克
		bombGroup.DrawBombs(g,this); //画出坦克爆炸效果
		shotGroup.CheckAlive();      //核查子弹是否存活
		shotGroup.DrawShots(g);      //画出子弹
		wallGroup.CheckAlive();
		wallGroup.DrawWalls(g);
		myHome.DrawHome(g);
				
	}
	

			
	@Override//按键操作控制s
	public void keyPressed(KeyEvent arg0) {
		
		if(heroGroup.Container.size() == 0) return;
		
		// TODO Auto-generated method stub
			if(arg0.getKeyCode() == KeyEvent.VK_W){	
				System.out.println("speed = "+heroGroup.Container.get(0).speed);
				heroGroup.Container.get(0).Move(0);				
			}else if(arg0.getKeyCode() == KeyEvent.VK_S){
				heroGroup.Container.get(0).Move(1);
			}else if(arg0.getKeyCode() == KeyEvent.VK_A){
				heroGroup.Container.get(0).Move(2);
			}else if(arg0.getKeyCode() == KeyEvent.VK_D){
				heroGroup.Container.get(0).Move(3);
			}else if(arg0.getKeyCode() == KeyEvent.VK_J){
				heroGroup.Container.get(0).Fire(shotGroup);
			}
			heroGroup.CheckCollision(enemyGroup, wallGroup);
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
	public void run(){	
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(this.isThreadLock()){		
				shotGroup.HitHeros(heroGroup, bombGroup);   //核查是否击中英雄坦克
				shotGroup.HitEnemys(enemyGroup,bombGroup);  //核查是否击中敌方坦克		
				shotGroup.HitWalls(wallGroup,bombGroup);
				shotGroup.HitHome(myHome, bombGroup);
				
				if(this.enemyGroup.Container.size() == 0){
					
					time--;
					if(time == 0){
						time =20;
						mtg.GameOver();
					}		
				}			
				if(myHome.isAlive() == false || this.heroGroup.Container.size() == 0){
					
					time--;
					this.myHome.setX(-1000);this.myHome.setY(-1000);
					if(time == 0){
						time =20;
						myHome.setAlive(true);
						Thread p = new PlayWave("gameover.wav");
						p.start();
						mtg.GameOver();
					}				
				}
			this.repaint();//重画
			}			
		}
	}
}	




