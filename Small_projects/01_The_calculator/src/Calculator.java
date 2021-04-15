import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class Calculator implements ActionListener, ItemListener{
	//因为要在main静态方法中被引用，所以必须设为static类型
	//第一个窗体window_first相关元素
	static JFrame window_first= null;  
	ButtonGroup bg;                    //按钮组：可组合若干单选按钮
	JRadioButton btn_add, btn_sub;     //单选按钮：+ 、 -
	int option = 0;                    //单选中间变量
	JTextField text1, text2, text3, text4, text5, text6;//6个数据输入
	JLabel L1;                         //显示计算结果的标签
	JButton btn_jisuan1, btn_history;  //计算按钮、历史记录窗体按钮
	String expression = "";            //表达式
	//第二个窗体window_history相关元素
	static Frame window_history = null;
	JTextArea showArea_his;            //显示面板
	ArrayList<String> history_list = new ArrayList<String>();//存储历史记录：表达式和结果
	boolean loop = true;
	//第三个窗体window_history相关元素
	static JFrame window_zidingyi = null; 
    JButton btn_jisuan2, btn_ret;      //计算按钮、返回窗体window_first按钮

    
    
    //----------------------------------------【构造方法】-----------------------------------------------------
    public Calculator() {
    	//-------------------------------------------------【窗体window_first 设计开始】-------------------------------------------------------------
        window_first = new JFrame("2018131212 李延松");
        window_first.setSize(400, 300); 
        Container contentPane = window_first.getContentPane();
        window_first.setLocation(750, 320);
        //图标：替换
        Toolkit kit =Toolkit.getDefaultToolkit();
        Image image=kit.createImage("images/软件图标.gif");
        window_first.setIconImage(image);
        window_first.setVisible(true);
        window_first.setResizable(false);
        //---------------------------【窗体window_first：下拉式菜单】----------------------------------------
        //创建菜单栏组件JMenuBar
     	JMenuBar menuBar = new JMenuBar(); 
     	//创建3个JMenu菜单组件，并加入JMenuBar中
     	JMenu menu1 = new JMenu("文件(F)");
     	JMenu menu3 = new JMenu("帮助(H)");
     	JMenu menu2 = new JMenu("编辑(E)");
     	menuBar.add(menu1);
     	menuBar.add(menu2);
     	menuBar.add(menu3);
     	//创建4个JMenuItem菜单项组件，并加入JMenu中
     	JMenuItem item1 = new JMenuItem("自定义(U)");
     	JMenuItem item2 = new JMenuItem("退出(X)");
     	JMenuItem item3 = new JMenuItem("清空后台历史数据(C)");
     	JMenuItem item4 = new JMenuItem("关于作者(A)");
     	menu1.add(item1);
     	menu1.addSeparator();// 设置分隔符
     	menu1.add(item2);
     	menu2.add(item3);
     	menu3.add(item4);
     	
     	//-----------------------------------·················【窗口window_zidingyi 设计开始】·············································································
     	JFrame ZDY_window = new JFrame("自定义输入表达式");
 		ZDY_window.setLayout(new BorderLayout());
 		ZDY_window.setSize(500, 300);
 		ZDY_window.setLocation(750, 320);
        ZDY_window.setIconImage(image);
 		ZDY_window.setVisible(false);
 		ZDY_window.setResizable(false);
 		ZDY_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//创建一个JTextArea文本域，用来显示多行聊天信息
		JTextArea showArea2 = new JTextArea(12, 34);
		//创建一个JScrollPane滚动面板组件，将JTextArea文本域作为其显示组件
		JScrollPane scrollPane2 = new JScrollPane(showArea2);
		showArea2.setEditable(false); //设置文本域不可编辑
		//创建一个JTextField文本框，用来输入单行聊天信息
		JTextField inputField2 = new JTextField(20);
		btn_jisuan2 = new JButton("计算"); 
		showArea2.append("【提示】输入表达式格式举例：2+4-10+2/2+4*5"  + "\n"
											+ "【提示】输入“clear”命令，即可清空数据~~~" + "\n");
		btn_jisuan2.addActionListener(e -> {//按钮btn_jisuan2监听器
			String content = inputField2.getText();	
			// 判断输入的信息是否为空
			if (content != null && !content.trim().equals("")) {//如果不为空，将输入的文本追加到到聊天窗口
				if(content.trim().equals("clear")){
					showArea2.setText("");
					showArea2.append("【提示】输入表达式格式举例：2+4-10+2/2+4*5"  + "\n"
							+ "【提示】输入“clear”命令，即可清空数据~~~" + "\n");
				}else{
					try {
						String out = StackCalculator(content);
						showArea2.append("输入信息:" + out + "\n");
					} catch (Exception e2) {
						showArea2.append("【提示】您输入的表达式有误，请重新输入~~" + "\n");
					}
				}	
			} else {// 如果为空，提示聊天信息不能为空
				showArea2.append("表达式不能为空！！！" + "\n");
			}
			inputField2.setText(""); // 将输入的文本域内容置为空
		});
		//创建两个JPanel面板组件
		JPanel panel2 = new JPanel();
		JLabel label2 = new JLabel("输入表达式");// 创建一个标签
		btn_ret = new JButton("返回");
		btn_ret.addActionListener(e -> {
			window_first.setVisible(true);
			ZDY_window.setVisible(false);
		});
		panel2.add(btn_ret);
		panel2.add(label2);                 
		panel2.add(inputField2);            
		panel2.add(btn_jisuan2);                  
		//向JFrame聊天窗口的顶部和尾部分别加入面板组件JScrollPane和JPanel
		ZDY_window.add(scrollPane2, BorderLayout.PAGE_START);
		ZDY_window.add(panel2, BorderLayout.PAGE_END);
		//-----------------------------------··················【窗体window_zidingyi 设计结束】·············································································
		//分别创建4个JMenuItem菜单项监听器
     	item1.addActionListener(e -> {//【自定义】 ---> 【打开窗体window_zidingyi】
     		window_first.setVisible(false);
     		ZDY_window.setVisible(true);
     	});
     	
     	item2.addActionListener(e -> System.exit(0));//【退出】--> 【直接关闭所有窗体】
     	
     	item3.addActionListener(e -> {//【清空后台历史数据】---> 【集合history_list中的历史数据被清空】
     		history_list.clear();
     		showArea_his.setText("");//同时清空窗体window_history中，已经显示的文本信息
     	});
     	
     	item4.addActionListener(e -> {//【关于作者】 --> 【打开窗体writer_window】
     		JFrame writer_window = new JFrame("小程序作者");
     		writer_window.setLayout(new BorderLayout());
     		writer_window.setSize(305, 330);
     		writer_window.setLocation(750, 320);
     		writer_window.setVisible(true);
     		writer_window.setResizable(false);
    		JLabel label_writer = new JLabel();
    		ImageIcon icon_writer = new  ImageIcon("images/专业团队.gif");
    		Image writer = icon_writer.getImage();
    		writer = writer.getScaledInstance(300, 300, Image.SCALE_DEFAULT);  
    		icon_writer.setImage(writer);  
    		label_writer.setIcon(icon_writer);
    		JPanel panel_writer = new JPanel();
    		writer_window.add(label_writer, BorderLayout.PAGE_START);
    		writer_window.add(panel_writer, BorderLayout.PAGE_END);
    		writer_window.setIconImage(image);
     	});
     	//向JFrame窗体容器中加入JMenuBar菜单组件
     	window_first.setJMenuBar(menuBar);    
     	
      //----------------------【窗体window_first 最上面的Panel：单选】-------------------------------
        JPanel p1 = new JPanel();            
        p1.setLayout(new GridLayout(1, 2));
        //设置边框
        p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                Color.blue, 1), "选择运算种类", TitledBorder.CENTER, TitledBorder.TOP));
        btn_add = new JRadioButton("＋");
        btn_sub = new JRadioButton("－");
        p1.add(btn_add);
        p1.add(btn_sub);
        bg = new ButtonGroup();         //按钮组：组合2个单选按钮，使一次只能选择一个
        bg.add(btn_add);
        bg.add(btn_sub);
        btn_add.addItemListener(this);  //增加ItemListener事件监听器(后面有专门的函数)
        btn_sub.addItemListener(this);
       
      //-----------------------------------------【窗体window_first 中间的Panel：输入数据、历史记录按钮】----------------------------------------------
        JPanel p2 = new JPanel(); 
        p2.setLayout(null);//取消面板内默认布局
        p2.setLayout(new GridLayout(3, 4));
        p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                Color.blue, 1), "输入参与运算的两个带分数", TitledBorder.CENTER, TitledBorder.TOP));
        btn_history = new JButton(new ImageIcon("images/历史记录.gif"));
        btn_history.setToolTipText("历史记录");//鼠标悬浮提示信息
        btn_history.setBorderPainted(false);
        btn_history.setBackground(new Color(238,238,238));//颜色更改
        JLabel L1_2 = new JLabel("整数部分");
        JLabel L1_3 = new JLabel("分子");
        JLabel L1_4 = new JLabel("分母");
        JLabel L2_1 = new JLabel("第一个真分数：");
        JLabel L3_1 = new JLabel("第二个真分数：");
        text1 = new JTextField(); text2 = new JTextField(); text3 = new JTextField();
        text4 = new JTextField(); text5 = new JTextField(); text6 = new JTextField();
        p2.add(btn_history);p2.add(L1_2); p2.add(L1_3); p2.add(L1_4);
        p2.add(L2_1);p2.add(text1); p2.add(text2); p2.add(text3);
        p2.add(L3_1);p2.add(text4); p2.add(text5); p2.add(text6);  
        btn_history.addActionListener(this);
        
      //-------------------------------------------【窗体window_first 最下面的Panel：计算按钮、表达式显示】--------------------------------------------------
        JPanel p4 = new JPanel(); 
        p4.setLayout(new GridLayout(1, 2));
        btn_jisuan1 = new JButton("计   算");
        L1 = new JLabel("", SwingConstants.CENTER); 
        p4.add(btn_jisuan1);
        p4.add(L1);
        btn_jisuan1.addActionListener(this);
        
       //--------------------------------------------------------【窗体window_first 整体布局】------------------------------------------------------------------
        contentPane.add(p1, BorderLayout.NORTH);
        contentPane.add(p2, BorderLayout.CENTER);
        contentPane.add(p4, BorderLayout.SOUTH);
        window_first.getRootPane().setDefaultButton(btn_jisuan1); // 设置窗体回车对应按钮
        window_first.setVisible(true);
        window_first.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        //--------------------------------------------------------【窗体window_first 设计结束】---------------------------------------------------------------------------------------
        //-------------------------------------------------------【history窗口 设计开始】------------------------------------------------------------------
        window_history = new JFrame("历史记录");
		window_history.setLayout(new BorderLayout());
		window_history.setSize(400, 305);
		window_history.setLocation(1150, 320);
		window_history.setIconImage(image);
		window_history.add(showArea_his); 
		window_history.setVisible(false);//先不让显示，当btn_history按钮触发再显示
		//创建一个JTextArea文本域，用来显示多行聊天信息
		showArea_his = new JTextArea(12, 34);
		//创建一个JScrollPane滚动面板组件，将JTextArea文本域作为其显示组件
		JScrollPane scrollPane = new JScrollPane(showArea_his);
		showArea_his.setEditable(false); // 设置文本域不可编辑  
		//-------------------------------------------------------【history窗口 设计结束】-------------------------------------------------------------------
    }
     
    //--------------------------------------------------【itemStateChanged：单选钮被点击时触发】----------------------------------------------
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == btn_add) {//+
            option = 1;
        }
        if (e.getSource() == btn_sub) {//-
            option = 2;
        }
    }   
    
    //---------------------------------------【actionPerformed：多个按钮放在同一监视函数：btn_history、btn_jisuan1】----------------------------------------------
    public void actionPerformed(ActionEvent e)
    {	
    	if(e.getSource() == btn_history){
    		window_history.setVisible(true);
    		loop = true;
            while(loop){
            	showArea_his.setText("");
            	if(history_list.size() == 0){
            		showArea_his.append("~~~~~~~~~~~~~暂无数据，请先输入数据~~~~~~~~~~~" + "\n");
            		break;
            	}
            	for(String obj : history_list ){
            		showArea_his.append("输入的算式为:" + obj + "\n");
            	}
            	showArea_his.append("【提示】刷新数据请重新点击“历史记录”的图标" + "\n");
            	showArea_his.append("【提示】清空数据请点击“编辑”-“清空”" + "\n");
            	loop = false;
            }
    	}
    	if(e.getSource() == btn_jisuan1){
	    	int[] arr = new int[6];
	    	try {//捕获异常，处理输入的操作数不符合要求的情况
	    		if(Integer.parseInt(text3.getText()) == 0 || Integer.parseInt(text6.getText()) == 0){
	    			JOptionPane.showMessageDialog(null, "分母不能为0！！！");
	    			return;
	    		}
	    		arr[0] = Integer.parseInt(text1.getText());arr[1] = Integer.parseInt(text2.getText());arr[2] = Integer.parseInt(text3.getText());
	    		arr[3] = Integer.parseInt(text4.getText());arr[4] = Integer.parseInt(text5.getText());arr[5] = Integer.parseInt(text6.getText());	
	        }catch(Exception exc) {
	            JOptionPane.showMessageDialog(null, "请输入正确的操作数");
	            return;
	        }
	    	MixedFraction num1 = new MixedFraction(arr[0],arr[1],arr[2]);
	    	MixedFraction num2 = new MixedFraction(arr[3],arr[4],arr[5]);
	        String  result, expression1, expression2;
	        expression1 = text1.getText() + "[" + text2.getText() + "/" + text3.getText() + "]";
	        expression2 = text4.getText() + "[" + text5.getText() + "/" + text6.getText() + "]";
	        switch (option) {
	            case 1:
	            	num1.jia(num2);
	            	result = num1 + "";
	            	expression = expression1 + "+" + expression2;
	                L1.setText(expression + "=" + result);
	                history_list.add(expression + "=" + result);
	                break;
	            case 2:
	            	num1.jian(num2);
	            	result = num1 + "";
	            	expression = expression1 + "-" + expression2;
	                L1.setText(expression + "=" + result);
	                history_list.add(expression + "=" + result);
	                break;
	            default:
	                L1.setText("请选择运算符");
	        }
    	}	
    }
    
    public static void main(String args[]) {
        new Calculator();
    }
    
    //-----------------------------------------------------------【扩展功能：解析字符串输入的表达式】--------------------------------------------------------------------------------------
    //处理数组堆栈中的数字和符号
    public static String StackCalculator(String str){
		//根据思路，完成表达式的运算
		String expression = str;
		//创建两个栈：数字栈、符号栈
		ArrayStack2 numStack = new ArrayStack2(10);
		ArrayStack2 operStack = new ArrayStack2(10);
		
		//定义相关变量
		int index = 0; //用于扫描
		int num1 = 0;
		int num2 = 0;
		int oper = 0;
		int res = 0;
		char ch = ' '; //将每次扫描得到的char保存到ch
		String keepNum = "";//用于拼接多位数
		//开始while循环扫描expression
		while(true){
			//依次得到expression的每一个字符
			ch = expression.substring(index, index+1).charAt(0);//expression.substring(index, index+1)拿到的是一个字符串，再加上.charAt(0)得到的是一个字符
			//判断ch是什么，然后做相应的处理
			if(operStack.isOper(ch)){//【如果是符号】
				if(!operStack.isEmpty()){//【如果符号栈不为空】就进行比较
					if(operStack.priority(ch) <= operStack.priority(operStack.peek())){//ch的优先级小于或等于栈中的符号
						//1.数字栈pop出两个数字、符号栈pop出一个数字
						num1 = numStack.pop();
						num2 = numStack.pop();
						oper = operStack.pop();
						//2.计算结果，并将结果res放回数字栈
						res = numStack.cal(num1, num2, oper);
						numStack.push(res);
						//3.然后再把当前的操作符ch放入符号栈
						operStack.push(ch);
					}else{//ch的优先级大于栈中的符号，就直接入符号栈
						operStack.push(ch);
					}
				}else{//【如果符号栈为空】直接入栈
					operStack.push(ch);
				}
			}else{//【如果是数字】
				//【补充】：数字可能为多位数的思路
				//1.当处理多位数时，不能发现是一个数就立刻入栈，因为可能是多位数
				//2.在处理数的时候，需要向expression的表达式的index 后再看一位，如果是数就进行扫描，如果是符号，才入栈
				//3.因此，我们需要定义一个字符串变量，进行拼接
				keepNum += ch;
				if(index == expression.length() - 1){//最后一个数据
					numStack.push(Integer.parseInt(keepNum));
				}else{
					if(operStack.isOper(expression.substring(index+1, index+2).charAt(0))){
					numStack.push(Integer.parseInt(keepNum));
					//重要！！！！keepNum清空
					keepNum = "";
					}
				}	
			}
			//让index+1，判断是否扫描到expression最后
			index++;
			if(index >= expression.length()){
				break;
			}
		}
		//当前表达式扫描完毕
		while(true){
			//如果符号栈为空，则计算结束，数字栈仅存一个数字res
			if(operStack.isEmpty()){
				break;
			}
			//如果符号栈不空，则计算未结束，还存在一次运算
			num1 = numStack.pop();
			num2 = numStack.pop();
			oper = operStack.pop();
			res = numStack.cal(num1, num2, oper);
			numStack.push(res);
		}
		return "表达式" + expression + "=" + numStack.pop() ;
	}   
}
