package saolei;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SaoLei implements ActionListener {
	JFrame frame = new JFrame();
	ImageIcon bannerIcon = new ImageIcon("banner.png");
	ImageIcon guessIcon = new ImageIcon("guess.png");
	ImageIcon bombIcon = new ImageIcon("bomb.png");
	ImageIcon failIcon = new ImageIcon("fail.png");
	ImageIcon winIcon = new ImageIcon("win.png");
	ImageIcon winFlagIcon = new ImageIcon("win_flag.png");
	
	//数据结构
	int ROW = 20; //行数
	int COL = 20; //列数
	int[][] data = new int[ROW][COL];//存放数据
	JButton[][] btns = new JButton[ROW][COL];
	int LEICOUNT = 30; //雷的总数
	int LEICODE = -1; //表示是雷
	int unopened = ROW * COL; //未开格子数
	int opened = 0; //已开格子数
	int seconds = 0; //时钟计数
	JButton bannerBtn = new JButton(bannerIcon);
	JLabel label1 = new JLabel("待开：" + unopened); 
	JLabel label2 = new JLabel("已开：" + opened);
	JLabel label3 = new JLabel("用时：" + seconds + "s");
	Timer timer = new Timer(1000, this);

	public SaoLei() {
		frame.setSize(600, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		setHeader();
		addLei();
		setButtons();
		timer.start();
	
		frame.setVisible(true);
	}
	
	/**
	 * 埋雷并计算周边雷的数量
	 */
	private void addLei() {
		Random rand = new Random();
		for (int i = 0; i < LEICOUNT; ) {
			int r = rand.nextInt(ROW);
			int c = rand.nextInt(COL);
			if(data[r][c] != LEICODE) {
				data[r][c] = LEICODE;
				i++;
			}
		}
		
		//计算周边的雷的数量
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if(data[i][j] == LEICODE) continue;
				int tempCount = 0;
				if (i>0 && j>0 && data[i-1][j-1] == LEICODE) tempCount++;
				if (i>0 && data[i-1][j] == LEICODE) tempCount++;
				if (i>0 && j<19 && data[i-1][j+1] == LEICODE) tempCount++;
				if (j>0 && data[i][j-1] ==  LEICODE) tempCount++;
				if (j<19 && data[i][j+1] == LEICODE) tempCount++;
				if (i<19 && j>0 && data[i+1][j-1] == LEICODE) tempCount++;
				if (i<19 && data[i+1][j] == LEICODE) tempCount++;
				if (i<19 && j<19 && data[i+1][j+1] == LEICODE) tempCount++;
				data[i][j] = tempCount;
			}
			
		}
	}
	
	/**
	 * 启动时创建并添加按钮
	 */
	private void setButtons() {
		Container con = new Container();
		con.setLayout(new GridLayout(ROW, COL));
		
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				JButton btn = new JButton(guessIcon);
				btn.setOpaque(true);
				btn.setMargin(new Insets(0,0,0,0));
				btn.setBackground(new Color(244,183,113));
				btn.addActionListener(this);
				//JButton btn = new JButton(data[i][j] + "");
				con.add(btn);
				btns[i][j] = btn;
			}
		}
		
		frame.add(con, BorderLayout.CENTER);
	}
	
	/**
	 * 设置头部的Banner以及状态栏的布局和控件
	 */
	private void setHeader() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c1 = new GridBagConstraints(0,0,3,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(bannerBtn, c1);
		bannerBtn.addActionListener(this);
		
		label1.setOpaque(true);
		label1.setBackground(Color.white);
		label1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		label2.setOpaque(true);
		label2.setBackground(Color.white);
		label2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		label3.setOpaque(true);
		label3.setBackground(Color.white);
		label3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		bannerBtn.setOpaque(true);
		bannerBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		bannerBtn.setBackground(Color.white);
		
		GridBagConstraints c2 = new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(label1, c2);
		GridBagConstraints c3 = new GridBagConstraints(1,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(label2, c3);
		GridBagConstraints c4 = new GridBagConstraints(2,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
		panel.add(label3, c4);	
		
		frame.add(panel, BorderLayout.NORTH);
		
	}

	
	/**
	 * 事件响应，点击按钮和时钟都会触发这个方法
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof Timer) {
			seconds++;
			label3.setText("用时：" + seconds + "s");
			timer.start();
			return;
		}
		
		JButton btn = (JButton)e.getSource();
		if(btn.equals(bannerBtn)) {
			restart();
			return;
		}
		
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if(btn.equals(btns[i][j])) {
					if(data[i][j] == LEICODE) {
						lose();
					} else {
						openCell(i, j);
						checkWin();
					}	
					return;
				}
			}
		}
	}
	
	/**
	 * 赢了游戏的处理
	 */
	private void checkWin() {
		int count = 0;
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if(btns[i][j].isEnabled()) count++;
			}
		}
		if(count == LEICOUNT) {
			timer.stop();
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					if(btns[i][j].isEnabled()) {
						btns[i][j].setIcon(winFlagIcon);
					}
				}
			}			
			bannerBtn.setIcon(winIcon);
			JOptionPane.showMessageDialog(frame, "你赢了, Yeah！\n点击Banner重新开始", "赢了", JOptionPane.PLAIN_MESSAGE );
		}		
	}
	
	/**
	 * 输掉游戏的处理
	 */
	private void lose() {
		timer.stop();
		bannerBtn.setIcon(failIcon);
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if(btns[i][j].isEnabled()) {
					JButton btn = btns[i][j];
					if(data[i][j] == LEICODE) {
						btn.setEnabled(false);
						btn.setIcon(bombIcon);
						btn.setDisabledIcon(bombIcon);
					} else {
						btn.setIcon(null);
						btn.setEnabled(false);
						btn.setOpaque(true);
						btn.setText(data[i][j]+"");
					}
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "可惜你暴雷了！\n你可以点击上面的Banner重新开始。", "暴雷啦", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * 打开格式的过程
	 * @param i 所在行
	 * @param j 所在列
	 */
	private void openCell(int i, int j) {
		JButton btn = btns[i][j];
		if(!btn.isEnabled()) return;
		
		btn.setIcon(null);
		btn.setEnabled(false);
		btn.setOpaque(true);
		btn.setBackground(Color.GREEN);
		btn.setText(data[i][j]+"");
		addOpenCount();
		
		if(data[i][j] == 0) {
			if (i>0 && j>0 && data[i-1][j-1] == 0) openCell(i-1, j-1);
			if (i>0 && data[i-1][j] == 0) openCell(i-1, j);
			if (i>0 && j<19 && data[i-1][j+1] == 0) openCell(i-1, j+1);
			if (j>0 && data[i][j-1] == 0) openCell(i, j-1);
			if (j<19 && data[i][j+1] == 0) openCell(i, j+1);
			if (i<19 && j>0 && data[i+1][j-1] == 0) openCell(i+1, j-1);
			if (i<19 && data[i+1][j] == 0) openCell(i+1, j);
			if (i<19 && j<19 && data[i+1][j+1] == 0) openCell(i+1, j+1);			
		}
	}
	
	/**
	 * 打开计数加1
	 */
	private void addOpenCount() {
		opened++;
		unopened--;
		label1.setText("待开：" + unopened);
		label2.setText("已开：" + opened);
	}
	
	/**
	 * 1. 给数据清零；2. 给按钮恢复状态。3.重新启动时钟。
	 */
	private void restart() {
		//恢复了数据和按钮
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				data[i][j] = 0;
				btns[i][j].setBackground(new Color(244,183,113));
				btns[i][j].setEnabled(true);
				btns[i][j].setText("");
				btns[i][j].setIcon(guessIcon);				
			}
		}
		
		//状态栏恢复
		unopened = ROW * COL;
		opened = 0;
		seconds = 0;
		label1.setText("待开：" + unopened);
		label2.setText("已开：" + opened);
		label3.setText("用时：" + seconds + "s");
		
		//重新启动！
		addLei();
		timer.start();
	}
	
	public static void main(String[] args) {
		new SaoLei();
	}

}
