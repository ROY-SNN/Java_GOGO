
public class ArrayStack2{
	private int maxSize; //栈的大小
	private int[] stack; //数组模拟栈，数据放在中
	private int top = -1;//top表示栈顶，初始化为-1
	
	public ArrayStack2(int maxSize){
		this.maxSize = maxSize;
		stack = new int[this.maxSize];
	}
	
	//栈满
	public boolean isFull(){
		return top == maxSize - 1;
	}
	
	//栈空
	public boolean isEmpty(){
		return top == -1;
	}
	
	
	//显示当前栈顶的数值，但不是真正的pop
	public int peek(){
		return stack[top];
	}

	
	//入栈push
	public void push(int value){
		//先判断栈是否满
		if(isFull()){
			System.out.println("栈满");
			return;
		}
		top++;
		stack[top] = value;
	}
	
	//出栈pop，将栈顶数据返回
	public int pop(){
		//先判断栈是否满
		if(isEmpty()){
			//抛出运行异常
			throw new RuntimeException("栈空，没有数据~~~");
		}
		int value = stack[top];
		top--;
		return value;
	}
	
	//显示栈的情况，遍历时需要从栈顶开始显示数据
	public void list(){
		//先判断栈是否空
		if (isEmpty()) {
			System.out.println("栈空，没有数据~~~~");
			return;
		}
		for(int i = top; i >= 0; i--){
			System.out.printf("stack[%d]=%d\n", i, stack[i]);
		}
	}
	
	//返回运算符的优先级，优先级是程序员来确定的，利用数字表示优先级
	//数字越大，优先级越高
	public int priority(int oper){
		if(oper == '*' || oper == '/'){
			return 1;
		}else if(oper == '+' || oper == '-'){
			return 0;
		}else{
			return -1;//假定目前的表达式中，只有+,-,*,/
		}
	}
	
	//判断是不是运算符
	public boolean isOper(char val){
		return val == '+' || val == '-' || val == '*' ||val == '/';
	}
	
	
	//计算方法
	public int cal(int num1, int num2, int oper){
		int res = 0;//res 用于存储计算结果
		switch (oper) {
		case '+':
			res = num1 + num2;
			break;
		case '-':
			res = num2 - num1;  //后弹出来的数字 作为 减数
			break;
		case '*':
			res = num1 * num2;
			break;
		case '/':
			res = num2 / num1; //后弹出来的数字 作为 除数
			break;
		default:
			break;
		}
		return res;
	}	
}