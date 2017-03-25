
public class Converter {
	
	private String errMessage = "SYNTAX ERROR";
	private String mathErr = "MATH ERROR";
	private String result = "";
	private String inputString;
	private int right = 0;
	private int left = 0; 
	private Stack stack;
  
	public Converter() { stack = new Stack(); }
  
	public Converter(String inputString) {
		
		this.inputString = inputString;
		stack = new Stack();
		
	}
	
	public double evaluate(String postFix) {
		
		int addedElements = 0;
		double answer = 0.0;
		double temp = 0.0;
		
		for(int x = 0; x < postFix.length(); x++) {
			String current = String.valueOf(postFix.charAt(x));
			if(isOperand(current)) {
				stack.push(current);
				addedElements++;
				
			}else if(current.equals(")")) {
				int exponent = 0, limit = 0;
				while(addedElements > limit++) {
					temp += Math.pow(10,exponent)*(Double.parseDouble(stack.pop()));
					exponent++;
				}
				stack.push(String.valueOf(temp));
				addedElements = 0;
				temp = 0.0;
				
			}else if(isOperator(current)) {
				double num1 = Double.parseDouble(stack.pop());
				double num2 = Double.parseDouble(stack.pop());
				
				if(current.equals("+")) 
					answer = num1 + num2;
				else if(current.equals("-")) 
					answer = num2 - num1;
				else if(current.equals("x")) 
					answer = num1 * num2;
				else if(current.equals("/")) {
					if(num1 == 0) {
						//result = "MATH ERROR";
						break;
					}else answer = num2 / num1;
				}
				stack.push(String.valueOf(answer));
			}
		}
		
		return answer;
	}
  

	public String toPostFix() {
	//	int check=0, checkPop=0;
		String temp = "(", infix = "", previousChar = "", previousOperator = "";
		String space = "           ";
		
		for(int x = 0; x < inputString.length(); x++) {
			String current = String.valueOf(inputString.charAt(x));

			if(isOperator(current)) {
		//		check++;
				if(x == 0 || x == inputString.length()-1 || isOperator(previousChar) || previousChar.equals("(")) {
					result = errMessage;
					break;
				}else if(current.equals("+") || current.equals("-")) {
					  if(previousOperator != "" && stack.elements != 0)
						result += stack.pop();
			//		  	checkPop++;
					stack.push(current);
				}else if(current.equals("/") || current.equals("x")) {
					if(previousOperator.equals("/") || previousOperator.equals("x"))
						result += stack.pop();
						//checkPop++;
					stack.push(current);
				}
//				if(check>2&&checkPop!=0){
	//				result+=stack.pop();
		//		}
				previousOperator = current;
				
			}else if(current.equals("(")) {
				if(x == inputString.length()-1) {
					result = errMessage;
					break;
				}else if(previousChar.equals("") || isOperand(previousChar) || previousChar.equals(")"))
					stack.push("x");
				
				left++;
				previousOperator = "";
				stack.push(current);
				
			}else if(current.equals(")")) {
				right++;
				if(x == 0 || left < right || previousChar.equals("(") || isOperator(previousChar)) {
					result = errMessage;
					break;
				}else{
					while(true) {
						String popped = stack.pop();
						if(!popped.equals("("))
							result += popped;
						else break;
					}
				}
				
			}else{
				if(current.equals("0") && previousChar.equals("/")) {
					result = mathErr;
					break;
				}else if(previousChar.equals(")")) {
					result = errMessage;
					break;
				}
				if(x != inputString.length()-1) {
					String next = String.valueOf(inputString.charAt(x+1));
					temp += current;
					if(!isOperand(next)) {
						result += temp + ")";
						temp = "(";
					}
				}else{
				   temp += current + ")";
				   result += temp;
				}
			}	
     
			if(x == inputString.length()-1) {
				for(int k = 0; k <= stack.elements; k++)
					result += stack.pop();
				stack.elements = 0;
			}
			
			infix += current;
			
			try {				
				StringBuilder sb = new StringBuilder(space);
				sb.deleteCharAt(space.length()-1);
				space = sb.toString();
			}catch(StringIndexOutOfBoundsException s) {}
			
			Calculator.snapshotScreen.append("  " + current + " | " + infix + space + " | " + result + "     " + " | ");
			stack.display();
	
			previousChar = current;
        }
		       
		if(left == right)
			return result;
		return errMessage;
		
    }	
	
	
	public boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("x") || s.equals("/");
	}
	
	public boolean isOperand(String s) {
		return !s.equals("(") && !s.equals(")") && !isOperator(s);
	}
		
}
