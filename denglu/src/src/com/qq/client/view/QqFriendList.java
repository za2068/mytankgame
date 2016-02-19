/**
 * �ҵĺ����б�����İ���ˣ���������
 */
package com.qq.client.view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class QqFriendList extends JFrame implements ActionListener, MouseListener{

	//�����һ�ſ�Ƭ
	JPanel jphy1, jphy2, jphy3;
	JButton jphy_jb1, jphy_jb2,jphy_jb3;
	JScrollPane jsp1;
	
	//����ڶ��ſ�Ƭ��İ���ˣ�
	
	JPanel jpmsr1, jpmsr2, jpmsr3;
	JButton jpmsr_jb1, jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	
	//������JFrame ���ó�CardLayout
	CardLayout cl;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QqFriendList qqFriendList=new QqFriendList();
	}
	
	public QqFriendList()
	{
		//�����һ�ſ�Ƭ(��ʾ�����б�
		jphy_jb1=new JButton("�ҵĺ���");
		jphy_jb2=new JButton ("İ����");
		jphy_jb2.addActionListener(this);
		jphy_jb3=new JButton ("������");
		jphy1=new JPanel(new BorderLayout());
		//�ٶ���50������
		jphy2=new JPanel(new GridLayout(50,1,4,4));  //4��ʾ�м����м��
		
		//��jphy2,��ʼ��50������
		JLabel []jbls=new JLabel[50];
		
		for(int i=0; i<jbls.length; i++)
		{
			//��ʾ����
			jbls[i]=new JLabel (i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			jbls[i].addMouseListener(this);
			jphy2.add(jbls[i]);
		}
			
		
		jphy3=new JPanel(new GridLayout(2,1));
		//�������������뵽jphy3��
		jphy3.add(jphy_jb2);
		jphy3.add(jphy_jb3);
		
		
		jsp1=new JScrollPane(jphy2);
		
		
		//��jphy1�ĳ�ʼ��
		jphy1.add(jphy_jb1,"North");
		jphy1.add(jsp1,"Center");
		jphy1.add(jphy3,"South");
		
		
		
		
		
		//����ڶ��ſ�Ƭ
		
		jpmsr_jb1=new JButton("�ҵĺ���");
		jpmsr_jb1.addActionListener(this);
		jpmsr_jb2=new JButton ("İ����");
		jpmsr_jb3=new JButton ("������");
		jpmsr1=new JPanel(new BorderLayout());
		//�ٶ���20������
		jpmsr2=new JPanel(new GridLayout(20,1,4,4));  //4��ʾ�м����м��
		
		//��jphy2,��ʼ��20��İ����
		JLabel []jbls2=new JLabel[20];
		
		for(int i=0; i<jbls2.length; i++)
		{
			//��ʾ����
			jbls2[i]=new JLabel (i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			jpmsr2.add(jbls2[i]);
		}
			
		
		jpmsr3=new JPanel(new GridLayout(2,1));
		//�������������뵽jphy3��
		jpmsr3.add(jpmsr_jb1);
		jpmsr3.add(jpmsr_jb2);
		
		
		jsp2=new JScrollPane(jpmsr2);
		
		
		//��jphy1�ĳ�ʼ��
		jpmsr1.add(jpmsr3,"North");
		jpmsr1.add(jsp2,"Center");
		jpmsr1.add(jpmsr_jb3,"South");
		
		
		
		cl=new CardLayout();
		this.setLayout(cl);
		this.add(jphy1, "1");
		this.add(jpmsr1, "2");
		
		
		
		this.setSize(140,400);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jphy_jb2)
		{
			cl.show(this.getContentPane(),"2");
		}else if(arg0.getSource()==jpmsr_jb1)
		{
			cl.show(this.getContentPane(), "1");
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//��Ӧ�û�˫�����¼����õ����ѵı��
		if(arg0.getClickCount()==2)  //��ʾ˫��
		{
			//�õ��ú��ѵı��
			String friendNo=((JLabel)arg0.getSource()).getText();
			new QqChart(friendNo);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel jl=(JLabel)arg0.getSource();
		jl.setForeground(Color.red);
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel jl=(JLabel)arg0.getSource();
		jl.setForeground(Color.black);
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
