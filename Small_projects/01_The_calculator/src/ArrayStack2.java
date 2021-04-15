
public class ArrayStack2{
	private int maxSize; //ջ�Ĵ�С
	private int[] stack; //����ģ��ջ�����ݷ�����
	private int top = -1;//top��ʾջ������ʼ��Ϊ-1
	
	public ArrayStack2(int maxSize){
		this.maxSize = maxSize;
		stack = new int[this.maxSize];
	}
	
	//ջ��
	public boolean isFull(){
		return top == maxSize - 1;
	}
	
	//ջ��
	public boolean isEmpty(){
		return top == -1;
	}
	
	
	//��ʾ��ǰջ������ֵ��������������pop
	public int peek(){
		return stack[top];
	}

	
	//��ջpush
	public void push(int value){
		//���ж�ջ�Ƿ���
		if(isFull()){
			System.out.println("ջ��");
			return;
		}
		top++;
		stack[top] = value;
	}
	
	//��ջpop����ջ�����ݷ���
	public int pop(){
		//���ж�ջ�Ƿ���
		if(isEmpty()){
			//�׳������쳣
			throw new RuntimeException("ջ�գ�û������~~~");
		}
		int value = stack[top];
		top--;
		return value;
	}
	
	//��ʾջ�����������ʱ��Ҫ��ջ����ʼ��ʾ����
	public void list(){
		//���ж�ջ�Ƿ��
		if (isEmpty()) {
			System.out.println("ջ�գ�û������~~~~");
			return;
		}
		for(int i = top; i >= 0; i--){
			System.out.printf("stack[%d]=%d\n", i, stack[i]);
		}
	}
	
	//��������������ȼ������ȼ��ǳ���Ա��ȷ���ģ��������ֱ�ʾ���ȼ�
	//����Խ�����ȼ�Խ��
	public int priority(int oper){
		if(oper == '*' || oper == '/'){
			return 1;
		}else if(oper == '+' || oper == '-'){
			return 0;
		}else{
			return -1;//�ٶ�Ŀǰ�ı��ʽ�У�ֻ��+,-,*,/
		}
	}
	
	//�ж��ǲ��������
	public boolean isOper(char val){
		return val == '+' || val == '-' || val == '*' ||val == '/';
	}
	
	
	//���㷽��
	public int cal(int num1, int num2, int oper){
		int res = 0;//res ���ڴ洢������
		switch (oper) {
		case '+':
			res = num1 + num2;
			break;
		case '-':
			res = num2 - num1;  //�󵯳��������� ��Ϊ ����
			break;
		case '*':
			res = num1 * num2;
			break;
		case '/':
			res = num2 / num1; //�󵯳��������� ��Ϊ ����
			break;
		default:
			break;
		}
		return res;
	}	
}