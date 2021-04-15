import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class Calculator implements ActionListener, ItemListener{
	//��ΪҪ��main��̬�����б����ã����Ա�����Ϊstatic����
	//��һ������window_first���Ԫ��
	static JFrame window_first= null;  
	ButtonGroup bg;                    //��ť�飺��������ɵ�ѡ��ť
	JRadioButton btn_add, btn_sub;     //��ѡ��ť��+ �� -
	int option = 0;                    //��ѡ�м����
	JTextField text1, text2, text3, text4, text5, text6;//6����������
	JLabel L1;                         //��ʾ�������ı�ǩ
	JButton btn_jisuan1, btn_history;  //���㰴ť����ʷ��¼���尴ť
	String expression = "";            //���ʽ
	//�ڶ�������window_history���Ԫ��
	static Frame window_history = null;
	JTextArea showArea_his;            //��ʾ���
	ArrayList<String> history_list = new ArrayList<String>();//�洢��ʷ��¼�����ʽ�ͽ��
	boolean loop = true;
	//����������window_history���Ԫ��
	static JFrame window_zidingyi = null; 
    JButton btn_jisuan2, btn_ret;      //���㰴ť�����ش���window_first��ť

    
    
    //----------------------------------------�����췽����-----------------------------------------------------
    public Calculator() {
    	//-------------------------------------------------������window_first ��ƿ�ʼ��-------------------------------------------------------------
        window_first = new JFrame("2018131212 ������");
        window_first.setSize(400, 300); 
        Container contentPane = window_first.getContentPane();
        window_first.setLocation(750, 320);
        //ͼ�꣺�滻
        Toolkit kit =Toolkit.getDefaultToolkit();
        Image image=kit.createImage("images/���ͼ��.gif");
        window_first.setIconImage(image);
        window_first.setVisible(true);
        window_first.setResizable(false);
        //---------------------------������window_first������ʽ�˵���----------------------------------------
        //�����˵������JMenuBar
     	JMenuBar menuBar = new JMenuBar(); 
     	//����3��JMenu�˵������������JMenuBar��
     	JMenu menu1 = new JMenu("�ļ�(F)");
     	JMenu menu3 = new JMenu("����(H)");
     	JMenu menu2 = new JMenu("�༭(E)");
     	menuBar.add(menu1);
     	menuBar.add(menu2);
     	menuBar.add(menu3);
     	//����4��JMenuItem�˵��������������JMenu��
     	JMenuItem item1 = new JMenuItem("�Զ���(U)");
     	JMenuItem item2 = new JMenuItem("�˳�(X)");
     	JMenuItem item3 = new JMenuItem("��պ�̨��ʷ����(C)");
     	JMenuItem item4 = new JMenuItem("��������(A)");
     	menu1.add(item1);
     	menu1.addSeparator();// ���÷ָ���
     	menu1.add(item2);
     	menu2.add(item3);
     	menu3.add(item4);
     	
     	//-----------------------------------����������������������������������������window_zidingyi ��ƿ�ʼ������������������������������������������������������������������������������������������������������������������������������������������������������������
     	JFrame ZDY_window = new JFrame("�Զ���������ʽ");
 		ZDY_window.setLayout(new BorderLayout());
 		ZDY_window.setSize(500, 300);
 		ZDY_window.setLocation(750, 320);
        ZDY_window.setIconImage(image);
 		ZDY_window.setVisible(false);
 		ZDY_window.setResizable(false);
 		ZDY_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//����һ��JTextArea�ı���������ʾ����������Ϣ
		JTextArea showArea2 = new JTextArea(12, 34);
		//����һ��JScrollPane��������������JTextArea�ı�����Ϊ����ʾ���
		JScrollPane scrollPane2 = new JScrollPane(showArea2);
		showArea2.setEditable(false); //�����ı��򲻿ɱ༭
		//����һ��JTextField�ı����������뵥��������Ϣ
		JTextField inputField2 = new JTextField(20);
		btn_jisuan2 = new JButton("����"); 
		showArea2.append("����ʾ��������ʽ��ʽ������2+4-10+2/2+4*5"  + "\n"
											+ "����ʾ�����롰clear����������������~~~" + "\n");
		btn_jisuan2.addActionListener(e -> {//��ťbtn_jisuan2������
			String content = inputField2.getText();	
			// �ж��������Ϣ�Ƿ�Ϊ��
			if (content != null && !content.trim().equals("")) {//�����Ϊ�գ���������ı�׷�ӵ������촰��
				if(content.trim().equals("clear")){
					showArea2.setText("");
					showArea2.append("����ʾ��������ʽ��ʽ������2+4-10+2/2+4*5"  + "\n"
							+ "����ʾ�����롰clear����������������~~~" + "\n");
				}else{
					try {
						String out = StackCalculator(content);
						showArea2.append("������Ϣ:" + out + "\n");
					} catch (Exception e2) {
						showArea2.append("����ʾ��������ı��ʽ��������������~~" + "\n");
					}
				}	
			} else {// ���Ϊ�գ���ʾ������Ϣ����Ϊ��
				showArea2.append("���ʽ����Ϊ�գ�����" + "\n");
			}
			inputField2.setText(""); // ��������ı���������Ϊ��
		});
		//��������JPanel������
		JPanel panel2 = new JPanel();
		JLabel label2 = new JLabel("������ʽ");// ����һ����ǩ
		btn_ret = new JButton("����");
		btn_ret.addActionListener(e -> {
			window_first.setVisible(true);
			ZDY_window.setVisible(false);
		});
		panel2.add(btn_ret);
		panel2.add(label2);                 
		panel2.add(inputField2);            
		panel2.add(btn_jisuan2);                  
		//��JFrame���촰�ڵĶ�����β���ֱ����������JScrollPane��JPanel
		ZDY_window.add(scrollPane2, BorderLayout.PAGE_START);
		ZDY_window.add(panel2, BorderLayout.PAGE_END);
		//-----------------------------------������������������������������������������window_zidingyi ��ƽ���������������������������������������������������������������������������������������������������������������������������������������������������������������
		//�ֱ𴴽�4��JMenuItem�˵��������
     	item1.addActionListener(e -> {//���Զ��塿 ---> ���򿪴���window_zidingyi��
     		window_first.setVisible(false);
     		ZDY_window.setVisible(true);
     	});
     	
     	item2.addActionListener(e -> System.exit(0));//���˳���--> ��ֱ�ӹر����д��塿
     	
     	item3.addActionListener(e -> {//����պ�̨��ʷ���ݡ�---> ������history_list�е���ʷ���ݱ���ա�
     		history_list.clear();
     		showArea_his.setText("");//ͬʱ��մ���window_history�У��Ѿ���ʾ���ı���Ϣ
     	});
     	
     	item4.addActionListener(e -> {//���������ߡ� --> ���򿪴���writer_window��
     		JFrame writer_window = new JFrame("С��������");
     		writer_window.setLayout(new BorderLayout());
     		writer_window.setSize(305, 330);
     		writer_window.setLocation(750, 320);
     		writer_window.setVisible(true);
     		writer_window.setResizable(false);
    		JLabel label_writer = new JLabel();
    		ImageIcon icon_writer = new  ImageIcon("images/רҵ�Ŷ�.gif");
    		Image writer = icon_writer.getImage();
    		writer = writer.getScaledInstance(300, 300, Image.SCALE_DEFAULT);  
    		icon_writer.setImage(writer);  
    		label_writer.setIcon(icon_writer);
    		JPanel panel_writer = new JPanel();
    		writer_window.add(label_writer, BorderLayout.PAGE_START);
    		writer_window.add(panel_writer, BorderLayout.PAGE_END);
    		writer_window.setIconImage(image);
     	});
     	//��JFrame���������м���JMenuBar�˵����
     	window_first.setJMenuBar(menuBar);    
     	
      //----------------------������window_first �������Panel����ѡ��-------------------------------
        JPanel p1 = new JPanel();            
        p1.setLayout(new GridLayout(1, 2));
        //���ñ߿�
        p1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                Color.blue, 1), "ѡ����������", TitledBorder.CENTER, TitledBorder.TOP));
        btn_add = new JRadioButton("��");
        btn_sub = new JRadioButton("��");
        p1.add(btn_add);
        p1.add(btn_sub);
        bg = new ButtonGroup();         //��ť�飺���2����ѡ��ť��ʹһ��ֻ��ѡ��һ��
        bg.add(btn_add);
        bg.add(btn_sub);
        btn_add.addItemListener(this);  //����ItemListener�¼�������(������ר�ŵĺ���)
        btn_sub.addItemListener(this);
       
      //-----------------------------------------������window_first �м��Panel���������ݡ���ʷ��¼��ť��----------------------------------------------
        JPanel p2 = new JPanel(); 
        p2.setLayout(null);//ȡ�������Ĭ�ϲ���
        p2.setLayout(new GridLayout(3, 4));
        p2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                Color.blue, 1), "����������������������", TitledBorder.CENTER, TitledBorder.TOP));
        btn_history = new JButton(new ImageIcon("images/��ʷ��¼.gif"));
        btn_history.setToolTipText("��ʷ��¼");//���������ʾ��Ϣ
        btn_history.setBorderPainted(false);
        btn_history.setBackground(new Color(238,238,238));//��ɫ����
        JLabel L1_2 = new JLabel("��������");
        JLabel L1_3 = new JLabel("����");
        JLabel L1_4 = new JLabel("��ĸ");
        JLabel L2_1 = new JLabel("��һ���������");
        JLabel L3_1 = new JLabel("�ڶ����������");
        text1 = new JTextField(); text2 = new JTextField(); text3 = new JTextField();
        text4 = new JTextField(); text5 = new JTextField(); text6 = new JTextField();
        p2.add(btn_history);p2.add(L1_2); p2.add(L1_3); p2.add(L1_4);
        p2.add(L2_1);p2.add(text1); p2.add(text2); p2.add(text3);
        p2.add(L3_1);p2.add(text4); p2.add(text5); p2.add(text6);  
        btn_history.addActionListener(this);
        
      //-------------------------------------------������window_first �������Panel�����㰴ť�����ʽ��ʾ��--------------------------------------------------
        JPanel p4 = new JPanel(); 
        p4.setLayout(new GridLayout(1, 2));
        btn_jisuan1 = new JButton("��   ��");
        L1 = new JLabel("", SwingConstants.CENTER); 
        p4.add(btn_jisuan1);
        p4.add(L1);
        btn_jisuan1.addActionListener(this);
        
       //--------------------------------------------------------������window_first ���岼�֡�------------------------------------------------------------------
        contentPane.add(p1, BorderLayout.NORTH);
        contentPane.add(p2, BorderLayout.CENTER);
        contentPane.add(p4, BorderLayout.SOUTH);
        window_first.getRootPane().setDefaultButton(btn_jisuan1); // ���ô���س���Ӧ��ť
        window_first.setVisible(true);
        window_first.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        //--------------------------------------------------------������window_first ��ƽ�����---------------------------------------------------------------------------------------
        //-------------------------------------------------------��history���� ��ƿ�ʼ��------------------------------------------------------------------
        window_history = new JFrame("��ʷ��¼");
		window_history.setLayout(new BorderLayout());
		window_history.setSize(400, 305);
		window_history.setLocation(1150, 320);
		window_history.setIconImage(image);
		window_history.add(showArea_his); 
		window_history.setVisible(false);//�Ȳ�����ʾ����btn_history��ť��������ʾ
		//����һ��JTextArea�ı���������ʾ����������Ϣ
		showArea_his = new JTextArea(12, 34);
		//����һ��JScrollPane��������������JTextArea�ı�����Ϊ����ʾ���
		JScrollPane scrollPane = new JScrollPane(showArea_his);
		showArea_his.setEditable(false); // �����ı��򲻿ɱ༭  
		//-------------------------------------------------------��history���� ��ƽ�����-------------------------------------------------------------------
    }
     
    //--------------------------------------------------��itemStateChanged����ѡť�����ʱ������----------------------------------------------
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == btn_add) {//+
            option = 1;
        }
        if (e.getSource() == btn_sub) {//-
            option = 2;
        }
    }   
    
    //---------------------------------------��actionPerformed�������ť����ͬһ���Ӻ�����btn_history��btn_jisuan1��----------------------------------------------
    public void actionPerformed(ActionEvent e)
    {	
    	if(e.getSource() == btn_history){
    		window_history.setVisible(true);
    		loop = true;
            while(loop){
            	showArea_his.setText("");
            	if(history_list.size() == 0){
            		showArea_his.append("~~~~~~~~~~~~~�������ݣ�������������~~~~~~~~~~~" + "\n");
            		break;
            	}
            	for(String obj : history_list ){
            		showArea_his.append("�������ʽΪ:" + obj + "\n");
            	}
            	showArea_his.append("����ʾ��ˢ�����������µ������ʷ��¼����ͼ��" + "\n");
            	showArea_his.append("����ʾ����������������༭��-����ա�" + "\n");
            	loop = false;
            }
    	}
    	if(e.getSource() == btn_jisuan1){
	    	int[] arr = new int[6];
	    	try {//�����쳣����������Ĳ�����������Ҫ������
	    		if(Integer.parseInt(text3.getText()) == 0 || Integer.parseInt(text6.getText()) == 0){
	    			JOptionPane.showMessageDialog(null, "��ĸ����Ϊ0������");
	    			return;
	    		}
	    		arr[0] = Integer.parseInt(text1.getText());arr[1] = Integer.parseInt(text2.getText());arr[2] = Integer.parseInt(text3.getText());
	    		arr[3] = Integer.parseInt(text4.getText());arr[4] = Integer.parseInt(text5.getText());arr[5] = Integer.parseInt(text6.getText());	
	        }catch(Exception exc) {
	            JOptionPane.showMessageDialog(null, "��������ȷ�Ĳ�����");
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
	                L1.setText("��ѡ�������");
	        }
    	}	
    }
    
    public static void main(String args[]) {
        new Calculator();
    }
    
    //-----------------------------------------------------------����չ���ܣ������ַ�������ı��ʽ��--------------------------------------------------------------------------------------
    //���������ջ�е����ֺͷ���
    public static String StackCalculator(String str){
		//����˼·����ɱ��ʽ������
		String expression = str;
		//��������ջ������ջ������ջ
		ArrayStack2 numStack = new ArrayStack2(10);
		ArrayStack2 operStack = new ArrayStack2(10);
		
		//������ر���
		int index = 0; //����ɨ��
		int num1 = 0;
		int num2 = 0;
		int oper = 0;
		int res = 0;
		char ch = ' '; //��ÿ��ɨ��õ���char���浽ch
		String keepNum = "";//����ƴ�Ӷ�λ��
		//��ʼwhileѭ��ɨ��expression
		while(true){
			//���εõ�expression��ÿһ���ַ�
			ch = expression.substring(index, index+1).charAt(0);//expression.substring(index, index+1)�õ�����һ���ַ������ټ���.charAt(0)�õ�����һ���ַ�
			//�ж�ch��ʲô��Ȼ������Ӧ�Ĵ���
			if(operStack.isOper(ch)){//������Ƿ��š�
				if(!operStack.isEmpty()){//���������ջ��Ϊ�ա��ͽ��бȽ�
					if(operStack.priority(ch) <= operStack.priority(operStack.peek())){//ch�����ȼ�С�ڻ����ջ�еķ���
						//1.����ջpop���������֡�����ջpop��һ������
						num1 = numStack.pop();
						num2 = numStack.pop();
						oper = operStack.pop();
						//2.���������������res�Ż�����ջ
						res = numStack.cal(num1, num2, oper);
						numStack.push(res);
						//3.Ȼ���ٰѵ�ǰ�Ĳ�����ch�������ջ
						operStack.push(ch);
					}else{//ch�����ȼ�����ջ�еķ��ţ���ֱ�������ջ
						operStack.push(ch);
					}
				}else{//���������ջΪ�ա�ֱ����ջ
					operStack.push(ch);
				}
			}else{//����������֡�
				//�����䡿�����ֿ���Ϊ��λ����˼·
				//1.�������λ��ʱ�����ܷ�����һ������������ջ����Ϊ�����Ƕ�λ��
				//2.�ڴ�������ʱ����Ҫ��expression�ı��ʽ��index ���ٿ�һλ����������ͽ���ɨ�裬����Ƿ��ţ�����ջ
				//3.��ˣ�������Ҫ����һ���ַ�������������ƴ��
				keepNum += ch;
				if(index == expression.length() - 1){//���һ������
					numStack.push(Integer.parseInt(keepNum));
				}else{
					if(operStack.isOper(expression.substring(index+1, index+2).charAt(0))){
					numStack.push(Integer.parseInt(keepNum));
					//��Ҫ��������keepNum���
					keepNum = "";
					}
				}	
			}
			//��index+1���ж��Ƿ�ɨ�赽expression���
			index++;
			if(index >= expression.length()){
				break;
			}
		}
		//��ǰ���ʽɨ�����
		while(true){
			//�������ջΪ�գ���������������ջ����һ������res
			if(operStack.isEmpty()){
				break;
			}
			//�������ջ���գ������δ������������һ������
			num1 = numStack.pop();
			num2 = numStack.pop();
			oper = operStack.pop();
			res = numStack.cal(num1, num2, oper);
			numStack.push(res);
		}
		return "���ʽ" + expression + "=" + numStack.pop() ;
	}   
}
