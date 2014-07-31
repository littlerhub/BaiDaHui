package com.littletools.tool.calculator;

import java.util.HashMap;
import java.util.Stack;

import com.littletools.*;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListenerOnItemClick implements OnItemClickListener {
	
	private Activity mThis;
	
	//标识是否点击"="
	boolean flag = false;
	
	public ListenerOnItemClick(Activity mThis){
		this.mThis = mThis;
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>)arg0.getItemAtPosition(arg2);
		
		String label = map.get("label").toString();
		String exception = ActivityCalculator.mEditText.getText().toString();

		
		if(label.equals("=")){
			
			String result = mThis.getResources().getString(R.string.calculator_error);

			try {
				result = calException(exception);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}finally{
				
				ActivityCalculator.mEditText.setText(result);
				
			}
			
			
			
		}else if(label.equals("C")){
			
			ActivityCalculator.mEditText.setText("");
		
			
		}else if(label.equals("Del")){
			if(exception.length() <= 1){
				
				ActivityCalculator.mEditText.setText("");
				
			}else{
				
				ActivityCalculator.mEditText.setText(exception.substring(0, exception.length() - 1));
				
			}
			
		}else{
	
			if(exception.equals(mThis.getResources().getString(R.string.calculator_error))){
				ActivityCalculator.mEditText.setText("");
			}
			ActivityCalculator.mEditText.append(label);
			
		}

	}

	/**
	 * 计算表达式的�?��结果
	 * @param 表达�?
	 * @return 结果
	 */
	private static String calException(String s) throws Exception{  //�?
		Stack<String> num=new Stack<String>();//操作数栈
		Stack<String> sy=new Stack<String>();   //操作符栈
		        s=s.replace("sin","s");                             //将里面的sin之类的做为一个单目运算符
		        s=s.replace("asin","a");
		        s=s.replace("ln","l");
		s=s.concat("=");                          //表达式末尾接上等�?
		sy.push(";");                                //操作符栈先push�?��;
		String number=new String();       //保存读到的数�?
		int i=0;
		while(i<s.length()){
			String c=new String();
			String cc=new String(" ");
			c=s.substring(i, i+1);
			if(i>0){
				
				cc=s.substring(i-1, i);				
			}
			if(isNumber(c)){
				
				number=number.concat(c);
			}
			
			else if(c.equals("(")){
				//读到符号时，若上�?��字符时数字就把number push进去
				if(isNumber(cc)) {
					num.push(number); 
					number="";
					}
					//读到左括号直接push进去
					sy.push(c);                                                                  
					
			}
			else if(c.equals(")")){
				if(isNumber(cc)) {
					num.push(number); 
					number="";
					}
				while(!sy.peek().toString().equals("(")){   
					//�?��计算到sy里是左括�?
					String a=sy.pop().toString();
					double num1=Double.valueOf(num.pop().toString());
					double num2=Double.valueOf(num.pop().toString());
					double result=cal(num2,num1,a);
					num.push(String.valueOf(result));
					
					}
				 	//左括号弹�?
					sy.pop();                                                            
			}
			else {
				if(isNumber(cc)) {
					num.push(number); 
					number="";
					}
				//是负号不是减�?
				if(c.equals("-")&&(cc.equals(" ")||cc.equals("("))) 
					number=number.concat(c); 
				else{
					//直到c的优先级大于操作符栈顶的字符
					while(pri(c)<=pri(sy.peek().toString())){                          
						
						String a=sy.pop().toString();
						if(a.equals("s")||a.equals("a")||a.equals("l")){  
							//单目运算�?
							double n=Double.valueOf(num.pop().toString());
							num.push(String.valueOf(cal(n,a)));
						}
						else {                                                                              //双目运算�?
							double num1=Double.valueOf(num.pop().toString());
							double num2=Double.valueOf(num.pop().toString());
							double result=cal(num2,num1,a);
							num.push(String.valueOf(result));
						}
					}
					
					sy.push(c);
					
				}
			}
			i++;
		
		}
			String d= num.pop().toString();
			if(num.isEmpty()) 
				return d;
			else //num栈还不为空，说明错误，比�?  1�?-1�?		
				return "请输入正确表达式" ; 
 
		}
	
		//判断符号优先级的函数�?
		private static int pri(String a){                   //在这里定义运算符的优先级
			int r=0;
			if(a.equals(";"))
				r=-1;
			else if(a.equals("=")) 
				r=0;
			else if(a.equals("+")||a.equals("-")) 
				r=1;
			else if(a.equals("��")||a.equals("/")) 
				r=2;
			else if(a.equals("s")||a.equals("a")||a.equals("l")||a.equals("%"))
				r=3;
			return r;
			
		}
		//计算函数‍：
		private static double cal(double num,String a){           //单目运算符运算方法加到下�?
			double r=0;
			if(a.equals("s")) 
				r=java.lang.Math.sin(num);
			else if(a.equals("a")) 
				r=java.lang.Math.asin(num);
			else if(a.equals("l")) 
				r=java.lang.Math.log(num);
			return r;
		}
		private static double cal(double num1,double num2,String a){//双目运算符加到下�?
			double r=0;
			if(a.equals("+"))
				r=num1+num2;
			else if(a.equals("-")) 
				r=num1-num2;
			else if(a.equals("��")) 
				r=num1*num2;
			else if(a.equals("/")) 
				r=num1/num2;
			else if(a.equals("%"))
				r=num1%num2;
			return r;
		}
		//判断是否为数字：
		private static boolean isNumber(String s){
			if(s.equals("0")||s.equals("1")||s.equals("2")||s.equals("3")||s.equals("4")||s.equals("5")||s.equals("6")||s.equals("7")||s.equals("8")||s.equals("9")||s.equals("."))			        
				return true;
			else 				
				return false;
		
		}
	
}
