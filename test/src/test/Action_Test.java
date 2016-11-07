package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Action_Test extends JFrame{
	Action_Test(){
		setTitle("ActionListener");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container cP = getContentPane();
		cP.setLayout(new FlowLayout());
		cP.setBackground(Color.CYAN);
		JButton btn1 = new JButton("��ο�!");
		btn1.addActionListener(new TestAListener());
		cP.add(btn1);
		
		setSize(500,300);
		setVisible(true);
		
	}
	public static void main(String[] args){
		new Action_Test();
	}
}

class TestAListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		
		if(b.getText().equals("��ο�!")){
			b.setText("�ö�!");
		}else{
			b.setText("��ο�!");
		}
	}
	
}