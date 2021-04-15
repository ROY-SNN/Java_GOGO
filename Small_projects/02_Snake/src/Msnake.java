package snake;

import javax.swing.JFrame;

public class Msnake {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();               //创建窗口
		frame.setBounds(10,10,900,720);           
		frame.setResizable(false);                 //设置窗口为手动不可改变大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MPanel());                   //加了一层布
		
		frame.setVisible(true);
	}

}
			