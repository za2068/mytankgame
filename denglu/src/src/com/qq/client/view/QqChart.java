/**
 * �������������Ľ���
 */
package com.qq.client.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class QqChart extends JFrame{

	JTextArea jta;
	JTextField jtf;
	JButton jb;
	JPanel jp;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QqChart QQ=new QqChart("1");
	}

	public 	QqChart(String friend)
	{
		jta=new JTextArea();
		jtf=new JTextField(15);
		jb=new JButton("����");
		jp=new JPanel();
		jp.add(jtf);
		jp.add(jb);
		
		this.add(jta,"Center");
		this.add(jp,"South");
		//���ô��ڵ�ͼ��
		this.setTitle("�����ں�"+friend+"����");
		this.setIconImage((new ImageIcon("image/qq.gif").getImage()));
		this.setSize(300,200);
		this.setVisible(true);
		
		
	}
}
